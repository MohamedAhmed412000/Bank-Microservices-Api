package com.project.cards.mappers;

import com.project.cards.dto.CardDto;
import com.project.cards.models.Card;

public class CardMapper {

    public static Card mapToCard(CardDto cardDto, Card card) {
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setCardNumber(cardDto.getCardNumber());
        card.setCardType(cardDto.getCardType());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAvailableBalance(cardDto.getAvailableBalance());
        card.setUsedBalance(cardDto.getUsedBalance());
        return card;
    }

    public static CardDto mapToCardDto(Card card, CardDto cardDto) {
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setCardType(card.getCardType());
        cardDto.setTotalLimit(card.getTotalLimit());
        cardDto.setAvailableBalance(card.getAvailableBalance());
        cardDto.setUsedBalance(card.getUsedBalance());
        return cardDto;
    }

}
