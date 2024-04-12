package dev.passwordmanager.domain.passwordtag;

import dev.passwordmanager.domain.password.Password;
import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordTagService {
    private final PasswordTagRepository passwordTagRepository;

    @Transactional
    public void onCreatePassword(Password password, String tag) {
        log.info("Creating password tag: {}", tag);
        try {
            var newTag = new PasswordTag(tag, password);
            passwordTagRepository.save(newTag);
        } catch (Exception exception) {
            log.error("Failed to create password tag: {}", tag, exception);
            throw new InternalServerErrorException("Failed to create password tag");
        }
    }
}
