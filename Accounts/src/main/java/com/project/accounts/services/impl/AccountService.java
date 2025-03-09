package com.project.accounts.services.impl;

import com.project.accounts.dto.CustomerDto;
import com.project.accounts.repositories.AccountRepository;
import com.project.accounts.repositories.CustomerRepository;
import com.project.accounts.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    
    @Override
    public void createAccount(CustomerDto customerDto) {
    
    }
    
}
