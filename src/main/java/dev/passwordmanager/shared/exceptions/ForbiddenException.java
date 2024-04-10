package dev.passwordmanager.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpRequestException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
