package com.project.loans.services.impl;

import com.project.loans.constants.ApplicationConstants;
import com.project.loans.dto.LoanDto;
import com.project.loans.enums.LoanTypeEnum;
import com.project.loans.exceptions.LoanAlreadyExistsException;
import com.project.loans.exceptions.ResourceNotFoundException;
import com.project.loans.mappers.LoanMapper;
import com.project.loans.models.Loan;
import com.project.loans.repositories.LoanRepository;
import com.project.loans.services.ILoanService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Data
@RequiredArgsConstructor
@Service
public class LoanServiceImpl implements ILoanService {

    private final LoanRepository loanRepository;

    /**
     * @param mobileNumber of the customer
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
        if (optionalLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already exists for " + mobileNumber);
        }
        loanRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber of the customer
     * @return the details of the loan
     */
    @Override
    public LoanDto fetchLoanDetails(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Loan", "Mobile number", mobileNumber)
        );
        return LoanMapper.mapToLoanDto(loan, new LoanDto());
    }

    /**
     * @param loanDto indicating the details of the loan
     * @return boolean indicating whether the loan is updated or not
     */
    @Override
    public boolean updateLoan(LoanDto loanDto) {
        Loan loan = loanRepository.findByMobileNumber(loanDto.getMobileNumber()).orElseThrow(
            () -> new ResourceNotFoundException("Loan", "Mobile Number", loanDto.getMobileNumber())
        );
        LoanMapper.mapToLoan(loanDto, loan);
        loanRepository.save(loan);
        return true;
    }

    /**
     * @param mobileNumber of the customer
     * @return boolean indicating whether the loan is deleted or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Loan", "Mobile Number", mobileNumber)
        );
        loanRepository.deleteById(loan.getId());
        return true;
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan loan = new Loan();
        loan.setMobileNumber(mobileNumber);
        long loanNumber = 1000000000L + new Random().nextInt(900000000);
        loan.setLoanNumber(Long.toString(loanNumber));
        loan.setLoanType(LoanTypeEnum.GENERAL_LOAN.toString());
        loan.setLoanAmount(ApplicationConstants.LOAN_AMOUNT);
        loan.setAmountPaid(0L);
        loan.setOutstandingAmount(ApplicationConstants.LOAN_AMOUNT);
        return loan;
    }

}
