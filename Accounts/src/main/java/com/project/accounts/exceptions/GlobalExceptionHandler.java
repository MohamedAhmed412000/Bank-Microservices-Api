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
            webRequest.getContextPath(),
            HttpStatus.BAD_REQUEST,
            e.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
