package org.omoro.learning_springboot_git_k8s.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LocalDateTime timestamp =  LocalDateTime.now(ZoneId.of("Europe/Amsterdam"));

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : null;

        ApiError apiError = new ApiError(
                400,
                "Bad Request",
                errorMessage,
                timestamp
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ApiError> handleContactNotFoundException(ContactNotFoundException exception) {

        ApiError apiError = new ApiError(
                404,
                "Not Found",
                exception.getMessage(),
                timestamp
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMalformedJson(HttpMessageNotReadableException exception) {

        ApiError apiError = new ApiError(
                400,
                "Bad Request",
                "Invalid or malformed JSON request",
                timestamp
        );

        return ResponseEntity.badRequest().body(apiError);
    }
}
