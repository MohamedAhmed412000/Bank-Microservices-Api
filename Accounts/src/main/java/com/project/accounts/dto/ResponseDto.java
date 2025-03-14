package com.project.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
    name = "Response",
    description = "Schema to hold successful response information"
)
public class ResponseDto {
    @Schema(description = "Status message of the response", example = "Request processed successfully")
    private String message;

    @Schema(description = "Status code of the response", example = "200")
    private String statusCode;
}
