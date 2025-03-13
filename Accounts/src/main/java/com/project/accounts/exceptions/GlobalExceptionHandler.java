package com.project.accounts.exceptions;

import com.project.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

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
        return ResponseEntity.badRequest().body(errorResponse);
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

}
