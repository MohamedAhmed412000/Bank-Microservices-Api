package com.project.accounts.services;

import com.project.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    /**
     * @param mobileNumber of the customer
     * @return the customer details with his accounts list, cards list, and loan
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
