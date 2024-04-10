package dev.passwordmanager.domain.user;

import dev.passwordmanager.shared.exceptions.ConflictException;
import dev.passwordmanager.shared.exceptions.NotFoundException;
import dev.passwordmanager.shared.utils.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public Optional<User> find(Long id) {
        log.info("Finding user with id: {}", id);
        try {
            return userRepository.findById(id);
        } catch (Exception exception) {
            log.error("Failed to find user with id: {}", id, exception);
            throw ExceptionHandler.handle(exception, "Failed to find user");
        }
    }

    public User findOrThrow(Long id) {
        return find(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User create(String name) {
        log.info("Creating user with name: {}", name);
        try {
            userRepository.findByName(name).ifPresent(user -> {
                throw new ConflictException("User with name already exists");
            });
            User user = userFactory.create(name);
            return userRepository.save(user);
        } catch (Exception exception) {
            log.error("Failed to create user with name: {}", name, exception);
            throw ExceptionHandler.handle(exception, "Failed to create user");
        }
    }
}
