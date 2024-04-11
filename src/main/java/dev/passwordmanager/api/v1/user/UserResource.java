package dev.passwordmanager.api.v1.user;

import dev.passwordmanager.api.v1.user.dto.request.SaveUserDto;
import dev.passwordmanager.api.v1.user.dto.response.UserDto;
import dev.passwordmanager.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserResource implements UserOpenApi {
    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<Page<UserDto>> get(Pageable pageable) {
        var users = userService.findAll(pageable);
        var responseBody = users.map(userMapper::toDto);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        var user = userService.findOrThrow(id);
        var responseBody = userMapper.toDto(user);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> post(@Valid @RequestBody SaveUserDto requestBody) {
        userService.existsByName(requestBody.name());
        userService.create(requestBody.name());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> put(@PathVariable Long id, @Valid @RequestBody SaveUserDto requestBody) {
        userService.existsByName(requestBody.name());
        userService.update(id, requestBody.name());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
