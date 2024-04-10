package dev.passwordmanager.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpRequestException extends RuntimeException {
    private final Integer statusCode;

    protected HttpRequestException(String message, HttpStatus status) {
        super(message);
        this.statusCode = status.value();
    }
}
