package com.project.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
    name = "Error Response",
    description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    @Schema(description = "Api path invoked by the client")
    private String apiPath;

    @Schema(description = "Error code associated with the response")
    private HttpStatus errorCode;

    @Schema(description = "Error message returned by the server")
    private String message;

    @Schema(description = "The time when the response is returned")
    private LocalDateTime errorTime;
}
