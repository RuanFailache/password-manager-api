package dev.passwordmanager.api.v1.user;

import dev.passwordmanager.api.v1.user.dto.request.SaveUserDto;
import dev.passwordmanager.api.v1.user.dto.response.UserDto;
import dev.passwordmanager.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "User")
public interface UserOpenApi {
    @Operation(summary = "Find all users")
    @ApiResponse(responseCode = "200", description = "Users found")
    ResponseEntity<Page<UserDto>> get(@ParameterObject Pageable pageable);

    @Operation(summary = "Find user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<UserDto> getById(Long id);

    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    ResponseEntity<Void> post(SaveUserDto requestBody);

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    ResponseEntity<Void> put(Long id, SaveUserDto requestBody);
}
