package dev.passwordmanager.api.v1.user;

import dev.passwordmanager.api.v1.password.PasswordMapper;
import dev.passwordmanager.api.v1.user.dto.response.UserDto;
import dev.passwordmanager.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordMapper passwordMapper;

    public UserDto toDto(User user) {
        var passwords = user.getPasswords().stream().map(passwordMapper::toDto).toList();
        return new UserDto(user.getId(), user.getName(), passwords);
    }
}
