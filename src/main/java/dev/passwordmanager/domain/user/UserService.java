package dev.passwordmanager.domain.user;

import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
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
            User user = userFactory.create(name);
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Failed to create user with name: {}", name, e);
            throw new InternalServerErrorException("Failed to create user");
        }
    }
}
