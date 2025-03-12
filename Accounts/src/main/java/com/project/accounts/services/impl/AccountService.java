package com.project.accounts.services.impl;

import com.project.accounts.constants.ApplicationConstants;
import com.project.accounts.dto.AccountDto;
import com.project.accounts.dto.CustomerDto;
import com.project.accounts.enums.AccountTypeEnum;
import com.project.accounts.exceptions.CustomerAlreadyExistsException;
import com.project.accounts.exceptions.ResourceNotFoundException;
import com.project.accounts.mappers.AccountMapper;
import com.project.accounts.mappers.CustomerMapper;
import com.project.accounts.models.Account;
import com.project.accounts.models.Customer;
import com.project.accounts.repositories.AccountRepository;
import com.project.accounts.repositories.CustomerRepository;
import com.project.accounts.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.
            findByMobileNumber(customer.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with the given mobile number already exists.");
        }
        accountRepository.save(createNewAccount(customer));
    }

    @Override
    public CustomerDto fetchAccounts(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "Mobile number", mobileNumber)
        );
        List<Account> customerAccounts = accountRepository.findByCustomerId(customer.getId());
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccounts(customerAccounts.stream().map(account ->
            AccountMapper.mapToAccountDto(account, new AccountDto())).toList());
        return customerDto;
    }
    
    /**
     * @param customerDto is the customer account with his accounts
     * @return boolean to indicate if the customer details is updated
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        Customer customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber())
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile number",
                customerDto.getMobileNumber())
        );
        Set<String> customerAccountNumbers = customer.getAccounts().stream().
            map(account -> account.getAccountNumber().toString()).
            collect(Collectors.toSet());
        
        customerDto.getAccounts().forEach(accountDto -> {
            if (accountDto.getAccountNumber() == null || !customerAccountNumbers.contains(
                accountDto.getAccountNumber().toString())) {
                accountRepository.save(createNewAccount(customer));
            } else {
                accountRepository.save(AccountMapper.mapToAccount(accountDto, new Account()));
            }
        });
        customerRepository.save(CustomerMapper.mapToCustomer(customerDto, customer));
        return true;
    }
    
    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        Long accountNumber = 1000000000L + new Random().nextInt(900000000);
        account.setAccountNumber(accountNumber);
        account.setAccountType(AccountTypeEnum.SAVING.toString());
        account.setBranchAddress(ApplicationConstants.ADDRESS);
        account.setCreatedBy("SYSTEM");
        account.setCreatedAt(LocalDateTime.now());
        customer.addAccount(account);
        return account;
    }

}
