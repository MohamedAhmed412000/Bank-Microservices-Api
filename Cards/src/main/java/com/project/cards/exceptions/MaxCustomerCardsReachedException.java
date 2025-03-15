package com.project.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MaxCustomerCardsReachedException extends RuntimeException {

    public MaxCustomerCardsReachedException(String message) {
        super(message);
    }

}
