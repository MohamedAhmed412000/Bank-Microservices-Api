package com.project.accounts.services.clients;

import com.project.accounts.constants.ApplicationConstants;
import com.project.accounts.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "loans", dismiss404 = true)
public interface LoansFeignClient {

    @GetMapping(value = "/api/v1/loans/fetch-loan-details",
        produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoanDto> fetchLoanDetails(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @RequestParam("mobile-number") String mobileNumber);

}
