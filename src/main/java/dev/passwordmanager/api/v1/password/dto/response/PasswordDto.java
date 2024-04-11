package dev.passwordmanager.api.v1.password.dto.response;

import java.util.List;

public record PasswordDto(Long id, String description, String password, List<String> tags) {
}
