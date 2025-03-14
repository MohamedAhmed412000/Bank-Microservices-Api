package com.project.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
    name = "Account",
    description = "Schema to hold the account information"
)
public class AccountDto {
    @Schema(description = "The account number generated", example = "1234567890")
    @NotEmpty(message = "Account number can't be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(description = "The account type", example = "SAVING", allowableValues = {"SAVING", "CURRENT"})
    @NotEmpty(message = "Account type can't be null or empty")
    private String accountType;

    @Schema(description = "The bank branch address", examples = {"29 Main Street, Ain Shams"})
    @NotEmpty(message = "Branch address can't be null or empty")
    private String branchAddress;
}
