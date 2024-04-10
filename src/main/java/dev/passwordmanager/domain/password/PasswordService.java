package dev.passwordmanager.domain.password;

import dev.passwordmanager.domain.user.User;
import dev.passwordmanager.shared.utils.ExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordRepository passwordRepository;

    @Transactional
    public Password create(User user, String description, String password) {
        log.info("Creating password for user: {}", user.getName());
        try {
            var passwordEntity = new Password(user, description, password);
            return passwordRepository.save(passwordEntity);
        } catch (Exception exception) {
            log.error("Failed to create password for user: {}", user.getName(), exception);
            throw ExceptionHandler.handle(exception, "Failed to create password");
        }
    }
}
