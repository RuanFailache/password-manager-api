package dev.passwordmanager.shared.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends HttpRequestException {
    public InternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
