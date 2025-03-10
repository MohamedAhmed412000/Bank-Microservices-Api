package com.project.accounts.services;

import com.project.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     * create new account for the customer
     * @param customerDto as customer object
     */
    void createAccount(CustomerDto customerDto);
    
}
