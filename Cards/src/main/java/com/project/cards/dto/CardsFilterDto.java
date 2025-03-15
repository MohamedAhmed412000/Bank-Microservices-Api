package com.project.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;

@ToString
@Data
@Schema(
    name = "Card Filters",
    description = "Schema to hold the card filters information"
)
public class CardsFilterDto {

    @Schema(description = "The customer mobile number", example = "01234567890")
    @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits")
    private String mobileNumber;

    @Schema(description = "The card number generated", example = "0123456789012345")
    @Pattern(regexp = "(^$|[0-9]{16})", message = "Card number must be 16 digits")
    private String cardNumber;

    public CardsFilterDto(
        @RequestParam(name = "mobile-number") String mobileNumber,
        @RequestParam(name = "card-number", required = false) String cardNumber) {
        this.mobileNumber = mobileNumber;
        this.cardNumber = cardNumber;
    }
}
