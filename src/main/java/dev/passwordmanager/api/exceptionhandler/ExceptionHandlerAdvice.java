package dev.passwordmanager.api.exceptionhandler;

import dev.passwordmanager.shared.exceptions.HttpRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionHandlerDto> handle(Exception exception) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var responseBody = new ExceptionHandlerDto(exception, status);
        return new ResponseEntity<>(responseBody, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionHandlerDto> handle(MethodArgumentNotValidException exception) {
        var status = HttpStatus.BAD_REQUEST;
        var responseBody = new ExceptionHandlerDto(exception, status);
        return new ResponseEntity<>(responseBody, status);
    }

    @ExceptionHandler(HttpRequestException.class)
    public ResponseEntity<ExceptionHandlerDto> handle(HttpRequestException exception) {
        var responseBody = new ExceptionHandlerDto(exception);
        return new ResponseEntity<>(responseBody, exception.getStatus());
    }
}
