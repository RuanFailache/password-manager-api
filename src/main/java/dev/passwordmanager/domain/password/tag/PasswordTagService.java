package dev.passwordmanager.domain.password.tag;

import dev.passwordmanager.domain.password.Password;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new RuntimeException("Failed to create password tag");
        }
    }

    @Transactional
    public void onUpdatePassword(Password password, List<String> tags) {
        log.info("Deleting password tags for password: {}", password.getId());
        try {
            password.getTags().forEach(tag -> {
                var passwordNotContainsTag = tags.stream().noneMatch(t -> t.equals(tag.getTag()));
                if (passwordNotContainsTag) passwordTagRepository.delete(tag);
            });
            tags.forEach(tag -> {
                var passwordContainsTag = password.getTags().stream().anyMatch(t -> t.getTag().equals(tag));
                if (!passwordContainsTag) this.onCreatePassword(password, tag);
            });
        } catch (Exception exception) {
            log.error("Failed to delete password tags for password: {}", password.getId(), exception);
            throw new RuntimeException("Failed to delete password tags");
        }
    }
}
