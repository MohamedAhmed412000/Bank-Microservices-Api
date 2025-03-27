package com.project.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
    name = "Loan",
    description = "Schema to hold the loan information"
)
public class LoanDto {

    @Schema(description = "Loan Number of the customer", example = "548732457654")
    @NotEmpty(message = "Loan Number can't be null or empty")
    private String loanNumber;

    @Schema(description = "Type of the loan", example = "GENERAL_LOAN", allowableValues = {
        "GENERAL_LOAN", "HOME_LOAN", "CAR_LOAN"
    })
    @NotEmpty(message = "Loan Type can't be null or empty")
    private String loanType;

    @Schema(description = "Loan Amount of the customer", example = "100000")
    @NotNull(message = "Loan Amount can't be null")
    @Positive(message = "Loan Amount should be greater than zero")
    private Long loanAmount;

    @Schema(description = "Paid amount of the loan", example = "50000")
    @NotNull(message = "Paid Amount can't be null")
    @PositiveOrZero(message = "Loan Amount Paid should be greater than or equal to zero")
    private Long amountPaid;

    @Schema(description = "Outstanding amount of the loan", example = "50000")
    @NotNull(message = "Outstanding Amount can't be null")
    @PositiveOrZero(message = "Loan Outstanding Amount should be greater than or equal to zero")
    private Long outstandingAmount;

}
