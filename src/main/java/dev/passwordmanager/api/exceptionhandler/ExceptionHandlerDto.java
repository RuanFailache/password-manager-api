package dev.passwordmanager.api.exceptionhandler;

import dev.passwordmanager.shared.exceptions.HttpRequestException;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

public record ExceptionHandlerDto(String message, int status, OffsetDateTime timestamp) {
    public ExceptionHandlerDto(Exception exception, HttpStatus status) {
        this(exception.getMessage(), status.value(), OffsetDateTime.now());
    }

    public ExceptionHandlerDto(HttpRequestException exception) {
        this(exception, exception.getStatus());
    }
}
