package dev.passwordmanager.api.v1.password.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdatePasswordDto(
        @NotBlank
        @Schema(description = "Password description", example = "GitHub")
        String description,

        @NotBlank
        @Schema(description = "Password value", example = "123456")
        String password
) {
}
