package dev.passwordmanager.domain.password;

import dev.passwordmanager.domain.user.User;
import dev.passwordmanager.shared.exceptions.NotFoundException;
import dev.passwordmanager.shared.utils.ExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Password findOrThrow(Long id) {
        log.info("Finding password by id: {}", id);
        try {
            return passwordRepository.findById(id).orElseThrow(() -> new NotFoundException("Password not found"));
        } catch (Exception exception) {
            log.error("Failed to find password by id: {}", id, exception);
            throw ExceptionHandler.handle(exception, "Failed to find password");
        }
    }

    public void update(Password password, String description, String encryptedPassword) {
        log.info("Updating password: {}", password.getId());
        try {
            password.setDescription(description);
            password.setPassword(encryptedPassword);
            passwordRepository.save(password);
        } catch (Exception exception) {
            log.error("Failed to update password: {}", password.getId(), exception);
            throw ExceptionHandler.handle(exception, "Failed to update password");
        }
    }

    public Page<Password> findByUser(User user, Pageable pageable) {
        log.info("Finding passwords by user: {}", user.getName());
        try {
            return passwordRepository.findByUser(user, pageable);
        } catch (Exception exception) {
            log.error("Failed to find passwords by user: {}", user.getName(), exception);
            throw ExceptionHandler.handle(exception, "Failed to find passwords");
        }
    }

    public void delete(Long id) {
        log.info("Deleting password by id: {}", id);
        try {
            passwordRepository.deleteById(id);
        } catch (Exception exception) {
            log.error("Failed to delete password by id: {}", id, exception);
            throw ExceptionHandler.handle(exception, "Failed to delete password");
        }
    }
}
