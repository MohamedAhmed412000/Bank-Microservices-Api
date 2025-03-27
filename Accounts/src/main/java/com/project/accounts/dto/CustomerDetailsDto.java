package com.project.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(
    name = "Customer Details",
    description = "Schema to hold customer, accounts, cards and loans information"
)
public class CustomerDetailsDto {
    @Schema(description = "Name of the customer", example = "John Doe")
    @NotEmpty(message = "Customer name can't be null or empty")
    @Size(min = 5, max = 50, message = "Customer name must be between 5 and 50 characters")
    private String name;

    @Schema(description = "Email of the customer", example = "john.doe@example.com")
    @NotEmpty(message = "Customer email can't be null or empty")
    @Email(message = "Email address must be valid value")
    private String email;

    @Schema(description = "Mobile number of the customer", example = "01234567890")
    @NotEmpty(message = "Customer mobile number can't be null or empty")
    @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits")
    private String mobileNumber;

    @Schema(description = "List of customer accounts")
    private List<AccountDto> accounts;

    @Schema(description = "List of customer cards")
    private List<CardDto> cards;

    @Schema(description = "Customer loan information")
    private LoanDto loan;
}
