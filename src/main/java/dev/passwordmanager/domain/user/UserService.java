package dev.passwordmanager.domain.user;

import dev.passwordmanager.shared.exceptions.ConflictException;
import dev.passwordmanager.shared.exceptions.NotFoundException;
import dev.passwordmanager.shared.utils.ExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        log.info("Finding all users");
        try {
            return userRepository.findAll();
        } catch (Exception exception) {
            log.error("Failed to find all users", exception);
            throw ExceptionHandler.handle(exception, "Failed to find all users");
        }
    }

    public Page<User> findAll(Pageable pageable) {
        log.info("Finding all users paginated");
        try {
            return userRepository.findAll(pageable);
        } catch (Exception exception) {
            log.error("Failed to find all users", exception);
            throw ExceptionHandler.handle(exception, "Failed to find all users");
        }
    }

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
        return this.find(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public User create(String name) {
        log.info("Creating user with name: {}", name);
        try {
            User user = new User(name);
            return userRepository.save(user);
        } catch (Exception exception) {
            log.error("Failed to create user with name: {}", name, exception);
            throw ExceptionHandler.handle(exception, "Failed to create user");
        }
    }

    @Transactional
    public User update(Long id, String name) {
        log.info("Updating user with name: {}", name);
        try {
            User user = this.findOrThrow(id);
            user.setName(name);
            return userRepository.save(user);
        } catch (Exception exception) {
            log.error("Failed to update user with name: {}", name, exception);
            throw ExceptionHandler.handle(exception, "Failed to update user");
        }
    }

    public void existsByName(String name) {
        log.info("Checking if user with name exists: {}", name);
        try {
            userRepository.findByName(name).ifPresent(user -> {
                throw new ConflictException("User with name already exists");
            });
        } catch (Exception exception) {
            log.error("Failed to check if user with name exists: {}", name, exception);
            throw ExceptionHandler.handle(exception, "Failed to check if user exists");
        }
    }
}
