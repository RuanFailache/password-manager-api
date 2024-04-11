package dev.passwordmanager.api.v1.password;

import dev.passwordmanager.api.v1.password.dto.request.CreatePasswordDto;
import dev.passwordmanager.api.v1.password.dto.request.UpdatePasswordDto;
import dev.passwordmanager.api.v1.password.dto.response.PasswordDto;
import dev.passwordmanager.domain.password.PasswordService;
import dev.passwordmanager.domain.passwordtag.PasswordTagService;
import dev.passwordmanager.domain.user.UserService;
import dev.passwordmanager.shared.exceptions.ForbiddenException;
import dev.passwordmanager.shared.utils.EncryptionUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/passwords")
@RequiredArgsConstructor
public class PasswordResource implements PasswordOpenApi {
    private final UserService userService;

    private final PasswordService passwordService;

    private final PasswordTagService passwordTagService;

    private final PasswordMapper passwordMapper;

    private final EncryptionUtil encryptionUtil;

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<PasswordDto>> getByUser(@PathVariable Long id, Pageable pageable) {
        var user = userService.findOrThrow(id);
        var passwords = passwordService.findByUser(user, pageable);
        var responseBody = passwords.map(passwordMapper::toDto);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> post(@Valid @RequestBody CreatePasswordDto dto) {
        var user = userService.findOrThrow(dto.userId());

        if(user.isPasswordLimitReached()) {
            throw new ForbiddenException("User's password limit reached");
        }

        var encrypted = encryptionUtil.encrypt(dto.password());
        var password = passwordService.create(user, dto.description(), encrypted);

        dto.tags().forEach(tag -> passwordTagService.onCreatePassword(password, tag));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> put(@PathVariable Long id, @Valid @RequestBody UpdatePasswordDto dto) {
        var password = passwordService.findOrThrow(id);
        var encrypted = encryptionUtil.encrypt(dto.password());

        passwordService.update(password, dto.description(), encrypted);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
