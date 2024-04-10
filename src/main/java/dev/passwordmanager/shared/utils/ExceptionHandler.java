package dev.passwordmanager.shared.utils;

import dev.passwordmanager.shared.exceptions.HttpRequestException;
import dev.passwordmanager.shared.exceptions.InternalServerErrorException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionHandler {
    public static HttpRequestException handle(Exception exception, String message) {
        if (exception instanceof HttpRequestException) {
            return (HttpRequestException) exception;
        }
        return new InternalServerErrorException(message);
    }
}
