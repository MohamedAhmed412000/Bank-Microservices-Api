package com.project.accounts.services.impl;

import com.project.accounts.constants.ApplicationConstants;
import com.project.accounts.dto.CustomerDto;
import com.project.accounts.enums.AccountTypeEnum;
import com.project.accounts.exceptions.CustomerAlreadyExistsException;
import com.project.accounts.mappers.CustomerMapper;
import com.project.accounts.models.Account;
import com.project.accounts.models.Customer;
import com.project.accounts.repositories.AccountRepository;
import com.project.accounts.repositories.CustomerRepository;
import com.project.accounts.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

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
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        account.setCustomer(customer);
        Long accountNumber = 1000000000L + new Random().nextInt(900000000);
        account.setAccountNumber(accountNumber);
        account.setAccountType(AccountTypeEnum.SAVING.name());
        account.setBranchAddress(ApplicationConstants.ADDRESS);
        return account;
    }

}
