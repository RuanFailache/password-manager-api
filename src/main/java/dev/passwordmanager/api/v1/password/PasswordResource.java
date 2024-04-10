package dev.passwordmanager.api.v1.password;

import dev.passwordmanager.api.v1.password.dto.request.CreatePasswordDto;
import dev.passwordmanager.domain.password.PasswordService;
import dev.passwordmanager.domain.password.tag.PasswordTagService;
import dev.passwordmanager.domain.user.UserService;
import dev.passwordmanager.shared.exceptions.ForbiddenException;
import dev.passwordmanager.shared.utils.EncryptionUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/passwords")
@RequiredArgsConstructor
public class PasswordResource implements PasswordOpenApi {
    private final UserService userService;

    private final PasswordService passwordService;

    private final PasswordTagService passwordTagService;

    private final EncryptionUtil encryptionUtil;

    @PostMapping
    public ResponseEntity<Void> post(@Valid @RequestBody CreatePasswordDto dto) {
        var user = userService.findOrThrow(dto.userId());

        if(user.isPasswordLimitReached()) {
            throw new ForbiddenException("User's password limit reached");
        }

        var encrypted = encryptionUtil.encrypt(dto.password());

        var password = passwordService.create(user, dto.description(), encrypted);

        dto.tags().forEach(tag -> passwordTagService.create(password, tag));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
