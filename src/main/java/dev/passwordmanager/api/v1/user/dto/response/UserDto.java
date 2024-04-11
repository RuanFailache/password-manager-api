package dev.passwordmanager.api.v1.user.dto.response;

import dev.passwordmanager.api.v1.password.dto.response.PasswordDto;

import java.util.List;

public record UserDto(
        Long id,
        String name,
        List<PasswordDto> passwords
) {
}
