package com.project.accounts.controllers;

import com.project.accounts.constants.ApplicationConstants;
import com.project.accounts.dto.CustomerDetailsDto;
import com.project.accounts.dto.ErrorResponseDto;
import com.project.accounts.services.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(
    name = "CRUD Rest APIs for Customer Microservice",
    description = "Provides RESTFUL APIs for fetching customer Details."
)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/customer", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private final ICustomerService iCustomerService;

    @Operation(
        summary = "Fetch customer details",
        description = "Fetches account, cards, loan details for a customer based on the provided " +
            "mobile number.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Customer details retrieved successfully",
                content = @Content(schema = @Schema(implementation = CustomerDetailsDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Customer not found for the provided mobile number",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @GetMapping(value = "/fetch-customer-details")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @RequestParam("mobile-number")
        @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile Number must be 11 digits")
        String mobileNumber
    ) {
        log.debug("Found requestId: {}", requestId);
        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(requestId, mobileNumber);
        return ResponseEntity.ok(customerDetailsDto);
    }

}
