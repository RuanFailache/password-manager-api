package dev.passwordmanager.api.v1.password;

import dev.passwordmanager.api.v1.password.dto.request.CreatePasswordDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Password")
public interface PasswordOpenApi {
    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Password created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "User's password limit reached"),
    })
    ResponseEntity<Void> post(CreatePasswordDto dto);
}
