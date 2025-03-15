package com.project.cards.services;

import com.project.cards.dto.CardDto;
import com.project.cards.dto.CardsFilterDto;

import java.util.List;

public interface ICardService {

    /**
     * @param cardDto contains the card information
     */
    void issueCard(CardDto cardDto);

    /**
     * @param cardsFilterDto contains mobile number and card number
     * @return the list of cards with filters
     */
    List<CardDto> fetchCards(CardsFilterDto cardsFilterDto);

    /**
     * @param cardDto contains the card information
     * @return boolean indicating whether the card is updated or not
     */
    boolean updateCard(CardDto cardDto);

    /**
     * @param cardsFilterDto contains mobile number and card number
     * @return boolean indicating whether the card is deleted or not
     */
    boolean deleteCard(CardsFilterDto cardsFilterDto);

}
