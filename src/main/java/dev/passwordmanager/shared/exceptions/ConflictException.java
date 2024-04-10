package dev.passwordmanager.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends HttpRequestException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
