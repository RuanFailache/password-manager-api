package dev.passwordmanager.domain.user;

import dev.passwordmanager.shared.exceptions.ConflictException;
import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import dev.passwordmanager.shared.exceptions.NotFoundException;
import dev.passwordmanager.shared.utils.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public User findOrThrow(Long id) {
        log.info("Finding user with id: {}", id);
        try {
            return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        }  catch (Exception exception) {
            log.error("Failed to find user with id: {}", id, exception);
            throw ExceptionHandler.handle(exception, "Failed to find user");
        }
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
