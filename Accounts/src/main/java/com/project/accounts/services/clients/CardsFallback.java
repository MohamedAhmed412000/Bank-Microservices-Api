package com.project.accounts.services.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardsFallback implements CardsFeignClient {
    @Override
    public ResponseEntity<Object> fetchCardsDetails(String requestId, String mobileNumber, String cardNumber) {
        return ResponseEntity.internalServerError().body(List.of());
    }
}
