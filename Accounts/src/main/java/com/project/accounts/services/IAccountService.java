package com.project.accounts.services;

import com.project.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     * create new account for the customer
     * @param customerDto as customer object
     */
    void createAccount(CustomerDto customerDto);

    /**
     * @param mobileNumber of the customer
     * @return the customer details with his accounts list
     */
    CustomerDto fetchAccounts(String mobileNumber);
    
    /**
     * @param customerDto is the customer account with his accounts
     * @return boolean to indicate if the customer details is updated
     */
    boolean updateAccount(CustomerDto customerDto);


    /**
     * @param mobileNumber of the customer
     * @return boolean to indicate if the customer is deleted
     */
    boolean deleteAccount(String mobileNumber);

    /**
     * @param accountNumber of the account
     * @return boolean to indicate if the notification status is updated
     */
    boolean updateNotificationStatus(Long accountNumber);

}
