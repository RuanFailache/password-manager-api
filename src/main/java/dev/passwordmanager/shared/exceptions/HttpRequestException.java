package dev.passwordmanager.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpRequestException extends RuntimeException {
    private final HttpStatus status;

    protected HttpRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
