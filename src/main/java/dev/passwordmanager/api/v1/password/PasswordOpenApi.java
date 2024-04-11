package dev.passwordmanager.api.v1.password;

import dev.passwordmanager.api.v1.password.dto.request.CreatePasswordDto;
import dev.passwordmanager.api.v1.password.dto.request.UpdatePasswordDto;
import dev.passwordmanager.api.v1.password.dto.response.PasswordDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Password")
public interface PasswordOpenApi {
    @Operation(summary = "Find password by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passwords found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<Page<PasswordDto>> getByUser(Long id, Pageable pageable);

    @Operation(summary = "Create password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Password created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "User's password limit reached"),
    })
    ResponseEntity<Void> post(CreatePasswordDto dto);

    @Operation(summary = "Update password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Password not found"),
    })
    ResponseEntity<Void> put(Long id, UpdatePasswordDto dto);
}
