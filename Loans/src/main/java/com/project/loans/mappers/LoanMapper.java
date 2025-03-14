package com.project.loans.mappers;

import com.project.loans.dto.LoanDto;
import com.project.loans.models.Loan;

public class LoanMapper {

    public static Loan mapToLoan(LoanDto loanDto, Loan loan) {
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setLoanAmount(loanDto.getLoanAmount());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutstandingAmount(loanDto.getOutstandingAmount());
        return loan;
    }

    public static LoanDto mapToLoanDto(Loan loan, LoanDto loanDto) {
        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setMobileNumber(loan.getMobileNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setLoanAmount(loan.getLoanAmount());
        loanDto.setAmountPaid(loan.getAmountPaid());
        loanDto.setOutstandingAmount(loan.getOutstandingAmount());
        return loanDto;
    }

}
