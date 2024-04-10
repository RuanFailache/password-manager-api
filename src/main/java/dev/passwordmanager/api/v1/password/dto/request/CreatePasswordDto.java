package dev.passwordmanager.api.v1.password.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePasswordDto(
        @NotNull
        @Schema(description = "User ID", example = "1")
        Long userId,

        @NotBlank
        @Schema(description = "Password description", example = "GitHub")
        String description,

        @NotBlank
        @Schema(description = "Password value", example = "123456")
        String password
) {
}
