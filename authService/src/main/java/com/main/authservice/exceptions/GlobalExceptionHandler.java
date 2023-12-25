package com.main.authservice.exceptions;

import com.main.authservice.model.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> constraintViolationException(ConstraintViolationException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            errors.put(String.valueOf(constraintViolation.getPropertyPath()), constraintViolation.getMessage());
        });

        return  ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return  ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> DataIntegrityViolationException(DataIntegrityViolationException ex){
        return  ResponseEntity.badRequest().body("There was error while updating database! Please try again!");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> badCredentialsException(BadCredentialsException ex, WebRequest  webRequest){
        return  ResponseEntity.badRequest().body(ErrorResponseDto.builder()
                                            .msg(ex.getMessage())
                                            .timestamp(LocalDateTime.now())
                                            .description(webRequest.getDescription(false))
                                            .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> resourceNotFoundException(ResourceNotFoundException ex, WebRequest  webRequest){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto.builder()
                                            .msg(ex.getMessage())
                                            .timestamp(LocalDateTime.now())
                                            .description(webRequest.getDescription(false))
                                            .build());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponseDto> userException(UserException ex, WebRequest  webRequest){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto.builder()
                                            .msg(ex.getMessage())
                                            .timestamp(LocalDateTime.now())
                                            .description(webRequest.getDescription(false))
                                            .build());
    }
}
