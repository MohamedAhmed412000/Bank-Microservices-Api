package com.project.accounts.repositories;

import com.project.accounts.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.mobileNumber = :mobileNumber")
    Optional<Customer> findByMobileNumber(String mobileNumber);

}
