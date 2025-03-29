package com.project.accounts.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.accounts.dto.*;
import com.project.accounts.exceptions.ResourceNotFoundException;
import com.project.accounts.mappers.AccountMapper;
import com.project.accounts.mappers.CustomerMapper;
import com.project.accounts.models.Account;
import com.project.accounts.models.Customer;
import com.project.accounts.repositories.AccountRepository;
import com.project.accounts.repositories.CustomerRepository;
import com.project.accounts.services.ICustomerService;
import com.project.accounts.services.clients.CardsFeignClient;
import com.project.accounts.services.clients.LoansFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param mobileNumber of the customer
     * @return the customer details with his accounts list, cards list, and loan
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String requestId, String mobileNumber) {
        // Fetch the customer details
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "Mobile number", mobileNumber)
        );
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,
            new CustomerDetailsDto());

        // Fetch the customer accounts details
        List<Account> customerAccounts = accountRepository.findByCustomerId(customer.getId());
        customerDetailsDto.setAccounts(
            customerAccounts.stream().map(customerAccount ->
                AccountMapper.mapToAccountDto(customerAccount, new AccountDto())
            ).toList()
        );

        // Fetch the customer loan details
        ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(requestId,
            mobileNumber);
        if (loanDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            LoanDto loanDto = loanDtoResponseEntity.getBody();
            customerDetailsDto.setLoan(loanDto);
        }

        // Fetch the customer cards details
        ResponseEntity<Object> cardsResponseEntity = cardsFeignClient.fetchCardsDetails(requestId,
            mobileNumber, null);
        if (cardsResponseEntity.getStatusCode().is2xxSuccessful()) {
            List<CardDto> cards = objectMapper.convertValue(cardsResponseEntity.getBody(),
                new TypeReference<>() {});
            customerDetailsDto.setCards(cards);
        } else {
            customerDetailsDto.setCards(List.of());
        }

        return customerDetailsDto;
    }
}
