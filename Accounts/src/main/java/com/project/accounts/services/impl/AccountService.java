package com.project.accounts.services.impl;

import com.project.accounts.constants.ApplicationConstants;
import com.project.accounts.dto.AccountDto;
import com.project.accounts.dto.CustomerDto;
import com.project.accounts.enums.AccountTypeEnum;
import com.project.accounts.exceptions.CustomerAlreadyExistsException;
import com.project.accounts.exceptions.CustomerMaxAccountsReachedException;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final static int MAX_CUSTOMER_ACCOUNTS = 3;
    
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
        AtomicInteger customerAccountsNumber = new AtomicInteger(customerAccountNumbers.size());

        List<Account> customerAccounts = new ArrayList<>();
        customerDto.getAccounts().forEach(accountDto -> {
            if (accountDto.getAccountNumber() == null || !customerAccountNumbers.contains(
                accountDto.getAccountNumber().toString())) {
                if (customerAccountsNumber.get() < MAX_CUSTOMER_ACCOUNTS) {
                    customerAccounts.add(createNewAccount(customer));
                    customerAccountsNumber.getAndIncrement();
                } else {
                    throw new CustomerMaxAccountsReachedException(
                        String.format("Customer exceeded the max account numbers count: %s", MAX_CUSTOMER_ACCOUNTS));
                }
            } else {
                customerAccounts.add(AccountMapper.mapToAccount(accountDto, new Account()));
            }
        });
        accountRepository.saveAll(customerAccounts);
        customerRepository.save(CustomerMapper.mapToCustomer(customerDto, customer));
        return true;
    }

    /**
     * @param mobileNumber of the customer
     * @return boolean to indicate if the customer is deleted
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "Mobile number", mobileNumber)
        );
        accountRepository.deleteAccountsByCustomerId(customer.getId());
        customerRepository.deleteCustomersById(customer.getId());
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
