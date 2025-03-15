package com.project.cards.repositories;

import com.project.cards.models.Card;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT c FROM Card c WHERE c.mobileNumber = :mobileNumber")
    List<Card> findByMobileNumber(String mobileNumber);

    @Query("SELECT c FROM Card c WHERE c.mobileNumber = :mobileNumber AND " +
        "c.cardNumber = :cardNumber OR :cardNumber IS NULL")
    List<Card> findByMobileNumberAndCardNumber(String mobileNumber, String cardNumber);

    @Transactional
    @Modifying
    @Query("DELETE FROM Card c WHERE c.id IN :cardsIds")
    void deleteAllById(List<Long> cardsIds);

}
