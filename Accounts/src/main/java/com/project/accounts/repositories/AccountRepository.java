package com.project.accounts.repositories;

import com.project.accounts.models.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.customer.id = :customerId")
    List<Account> findByCustomerId(Long customerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Account a WHERE a.customer.id = :customerId")
    void deleteAccountsByCustomerId(Long customerId);
    
}
