package dev.passwordmanager.api.v1.user;

import dev.passwordmanager.domain.user.User;
import dev.passwordmanager.domain.user.UserService;
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

    @GetMapping
    public ResponseEntity<Page<User>> get(Pageable pageable) {
        var responseBody = userService.findAll(pageable);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        var responseBody = userService.findOrThrow(id);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> post(String name) {
        userService.existsByName(name);
        userService.create(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> put(@PathVariable Long id, String name) {
        userService.existsByName(name);
        userService.update(id, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
