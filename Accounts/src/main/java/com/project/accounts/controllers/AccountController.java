package com.project.accounts.controllers;

import com.project.accounts.dto.CustomerDto;
import com.project.accounts.dto.ResponseDto;
import com.project.accounts.enums.ResponsesEnum;
import com.project.accounts.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountController {

    private final IAccountService iAccountService;

    @PostMapping(value = "/create-account", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDto> createAccount(
        @RequestBody CustomerDto customerDto
    ) {
        iAccountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(
            ResponsesEnum.RESPONSE_CREATED.getMessage(),
            ResponsesEnum.RESPONSE_CREATED.getStatusCode().toString()
        ));
    }

    @GetMapping(value = "/fetch-accounts")
    public ResponseEntity<CustomerDto> fetchAccount(
        @RequestParam(name = "mobile-number") String mobileNumber
    ) {
        CustomerDto customerDto = iAccountService.fetchAccounts(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }
    
    @PutMapping(value = "/update-customer-info", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDto> updateCustomerDetails(
        @RequestBody CustomerDto customerDto
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

    @DeleteMapping(value = "/delete-customer-info")
    public ResponseEntity<ResponseDto> deleteCustomerInfo(
        @RequestParam(name = "mobile-number") String mobileNumber
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
