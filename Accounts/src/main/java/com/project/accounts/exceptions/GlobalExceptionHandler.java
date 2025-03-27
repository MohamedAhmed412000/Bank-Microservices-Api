package com.project.accounts.exceptions;

import com.project.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(
        Exception e, WebRequest webRequest) {
        e.printStackTrace();
        ErrorResponseDto errorResponse = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.INTERNAL_SERVER_ERROR,
            e.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(
        CustomerAlreadyExistsException e, WebRequest webRequest) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.BAD_REQUEST,
            e.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
        ResourceNotFoundException e, WebRequest webRequest) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.NOT_FOUND,
            e.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CustomerMaxAccountsReachedException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerMaxAccountsException(
        CustomerMaxAccountsReachedException e, WebRequest webRequest) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.BAD_REQUEST,
            e.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
}
