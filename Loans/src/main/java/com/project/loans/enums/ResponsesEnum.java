package com.project.loans.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponsesEnum {

    RESPONSE_OK("Success", HttpStatus.OK),
    RESPONSE_CREATED("Created", HttpStatus.CREATED),
    RESPONSE_BAD_REQUEST("Bad Request", HttpStatus.BAD_REQUEST),
    RESPONSE_NOT_FOUND("Not Found", HttpStatus.NOT_FOUND),
    RESPONSE_INTERNAL_SERVER_ERROR("An error occurred, Please try again", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final HttpStatus statusCode;
}
