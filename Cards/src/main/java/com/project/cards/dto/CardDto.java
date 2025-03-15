package com.project.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Schema(
    name = "Card",
    description = "Schema to hold the card information"
)
public class CardDto {

    @Schema(description = "The customer mobile number", example = "01234567890")
    @NotEmpty(message = "Customer Mobile Number can't be null or empty")
    @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits")
    private String mobileNumber;

    @Schema(description = "The card number generated", example = "0123456789012345")
    @Pattern(regexp = "(^$|[0-9]{16})", message = "Card number must be 16 digits")
    private String cardNumber;

    @Schema(description = "The card type", example = "DEBIT", allowableValues = {"DEBIT", "CREDIT"})
    @NotEmpty(message = "Card Type can't be null or empty")
    private String cardType;

    @Schema(description = "The card maximum limit", example = "1000000", minimum = "0")
    @Positive(message = "Card total amount should be greater than zero")
    private Long totalLimit;

    @Schema(description = "The customer card available balance", example = "5000", minimum = "0")
    @PositiveOrZero(message = "Card available balance should be greater than or equal to zero")
    private Long availableBalance;

    @Schema(description = "The account number generated", example = "10000", minimum = "0")
    @PositiveOrZero(message = "Card used balance should be greater than or equal to zero")
    private Long usedBalance;

}
