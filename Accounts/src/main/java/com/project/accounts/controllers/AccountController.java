package com.project.accounts.controllers;

import com.project.accounts.constants.ApplicationConstants;
import com.project.accounts.dto.CustomerDto;
import com.project.accounts.dto.ErrorResponseDto;
import com.project.accounts.dto.ResponseDto;
import com.project.accounts.enums.ResponsesEnum;
import com.project.accounts.services.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Tag(
    name = "CRUD Rest APIs for Accounts Microservice",
    description = "Provides RESTFUL APIs for managing accounts."
)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {

    private final IAccountService iAccountService;

    @Operation(
        summary = "Create a new account for a customer",
        description = "Creates a new account for a customer based on the provided customer details.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Account created successfully.",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request. Provided customer details are invalid.",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @PostMapping(value = "/create-account", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDto> createAccount(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @Valid @RequestBody CustomerDto customerDto
    ) {
        log.debug("Found requestId: {}", requestId);
        iAccountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(
            ResponsesEnum.RESPONSE_CREATED.getMessage(),
            ResponsesEnum.RESPONSE_CREATED.getStatusCode().toString()
        ));
    }

    @Operation(
        summary = "Fetch account details for a customer",
        description = "Fetches account details for a customer based on the provided mobile number.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Account details retrieved successfully",
                content = @Content(schema = @Schema(implementation = CustomerDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Account not found for the provided mobile number",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @GetMapping(value = "/fetch-accounts")
    public ResponseEntity<CustomerDto> fetchAccount(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @RequestParam(name = "mobile-number")
        @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits")
        String mobileNumber
    ) {
        CustomerDto customerDto = iAccountService.fetchAccounts(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
        summary = "Update customer details",
        description = "Updates customer details for a customer based on the provided mobile number.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Customer details updated successfully",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request. Provided customer details are invalid.",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @PutMapping(value = "/update-customer-info", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDto> updateCustomerDetails(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @Valid @RequestBody CustomerDto customerDto
    ) {
        boolean isUpdated = iAccountService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
                ResponsesEnum.RESPONSE_OK.getMessage(),
                ResponsesEnum.RESPONSE_OK.getStatusCode().toString()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(
                ResponsesEnum.RESPONSE_BAD_REQUEST.getMessage(),
                ResponsesEnum.RESPONSE_BAD_REQUEST.getStatusCode().toString()
            ));
        }
    }

    @Operation(
        summary = "Delete Customer Details",
        description = "Delete customer details for a customer based on the provided mobile number.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Customer details deleted successfully",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Account not found for the provided mobile number",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @DeleteMapping(value = "/delete-customer-info")
    public ResponseEntity<ResponseDto> deleteCustomerInfo(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @RequestParam(name = "mobile-number")
        @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits")
        String mobileNumber
    ) {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
                ResponsesEnum.RESPONSE_OK.getMessage(),
                ResponsesEnum.RESPONSE_OK.getStatusCode().toString()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(
                ResponsesEnum.RESPONSE_BAD_REQUEST.getMessage(),
                ResponsesEnum.RESPONSE_BAD_REQUEST.getStatusCode().toString()
            ));
        }
    }

}
