package com.project.cards.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "CARD")
public class Card extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARD_ID")
    private Long id;

    @NotEmpty
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @NotEmpty
    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @NotEmpty
    @Column(name = "CARD_TYPE")
    private String cardType;

    @Future
    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Positive
    @Column(name = "TOTAL_LIMIT")
    private Long totalLimit;

    @PositiveOrZero
    @Column(name = "AVAILABLE_AMOUNT")
    private Long availableBalance;

    @PositiveOrZero
    @Column(name = "AMOUNT_USED")
    private Long usedBalance;

}
