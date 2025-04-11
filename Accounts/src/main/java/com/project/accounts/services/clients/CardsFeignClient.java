package com.project.accounts.services.clients;

import com.project.accounts.constants.ApplicationConstants;
import com.project.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "cards", dismiss404 = true, fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/v1/cards/fetch-cards", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> fetchCardsDetails(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @RequestParam("mobileNumber") String mobileNumber,
        @RequestParam(value = "cardNumber", required = false) String cardNumber
    );

}
