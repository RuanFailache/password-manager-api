package dev.passwordmanager.api.v1.password;

import dev.passwordmanager.api.v1.password.dto.response.PasswordDto;
import dev.passwordmanager.api.v1.user.dto.response.UserDto;
import dev.passwordmanager.domain.password.Password;
import dev.passwordmanager.domain.passwordtag.PasswordTag;
import dev.passwordmanager.domain.user.User;
import dev.passwordmanager.shared.utils.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordMapper {
    private final EncryptionUtil encryptionUtil;

    public PasswordDto toDto(Password password) {
        var decryptedPassword = encryptionUtil.decrypt(password.getPassword());
        var tags = password.getTags().stream().map(PasswordTag::getTag).toList();
        return new PasswordDto(password.getId(), password.getDescription(), decryptedPassword, tags);
    }
}
