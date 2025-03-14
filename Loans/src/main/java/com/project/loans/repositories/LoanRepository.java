package com.project.loans.repositories;

import com.project.loans.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT l FROM Loan l WHERE l.mobileNumber = :mobileNumber")
    Optional<Loan> findByMobileNumber(String mobileNumber);

}
