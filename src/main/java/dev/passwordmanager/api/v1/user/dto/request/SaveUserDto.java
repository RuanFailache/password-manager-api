package dev.passwordmanager.api.v1.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SaveUserDto(
        @NotBlank
        @Schema(description = "User name", example = "Ruan Failache")
        String name
) {
}
