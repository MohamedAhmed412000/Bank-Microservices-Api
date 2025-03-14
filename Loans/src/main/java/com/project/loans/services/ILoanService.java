package com.project.loans.services;

import com.project.loans.dto.LoanDto;

public interface ILoanService {

    /**
     * @param mobileNumber of the customer
     */
    void createLoan(String mobileNumber);

    /**
     * @param mobileNumber of the customer
     * @return the details of the loan
     */
    LoanDto fetchLoanDetails(String mobileNumber);

    /**
     * @param loanDto indicating the details of the loan
     * @return boolean indicating whether the loan is updated or not
     */
    boolean updateLoan(LoanDto loanDto);

    /**
     * @param mobileNumber of the customer
     * @return boolean indicating whether the loan is deleted or not
     */
    boolean deleteLoan(String mobileNumber);

}
