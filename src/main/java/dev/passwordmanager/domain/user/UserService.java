package dev.passwordmanager.domain.user;

import dev.passwordmanager.shared.exceptions.ConflictException;
import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import dev.passwordmanager.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public User create(String name) {
        log.info("Creating user with name: {}", name);
        try {
            userRepository.findByName(name).ifPresent(user -> {
                throw new ConflictException("User with name already exists");
            });
            User user = userFactory.create(name);
            return userRepository.save(user);
        } catch (ConflictException exception) {
            log.warn("User with name already exists: {}", name);
            throw exception;
        } catch (Exception exception) {
            log.error("Failed to create user with name: {}", name, exception);
            throw new InternalServerErrorException("Failed to create user");
        }
    }
}
