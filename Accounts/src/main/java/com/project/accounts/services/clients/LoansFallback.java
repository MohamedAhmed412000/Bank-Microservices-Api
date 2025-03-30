package com.project.accounts.services.clients;

import com.project.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {
    @Override
    public ResponseEntity<LoanDto> fetchLoanDetails(String requestId, String mobileNumber) {
        return ResponseEntity.internalServerError().body(null);
    }
}
