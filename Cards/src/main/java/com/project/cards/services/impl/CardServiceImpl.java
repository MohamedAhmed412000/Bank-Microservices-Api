package com.project.cards.services.impl;

import com.project.cards.constants.ApplicationConstants;
import com.project.cards.dto.CardDto;
import com.project.cards.dto.CardsFilterDto;
import com.project.cards.enums.CardTypeEnum;
import com.project.cards.exceptions.MaxCustomerCardsReachedException;
import com.project.cards.exceptions.ResourceNotFoundException;
import com.project.cards.mappers.CardMapper;
import com.project.cards.models.Card;
import com.project.cards.repositories.CardRepository;
import com.project.cards.services.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {

    private final CardRepository cardRepository;

    /**
     * @param cardDto contains the card information
     */
    @Override
    public void issueCard(CardDto cardDto) {
        List<Card> cards = cardRepository.findByMobileNumber(cardDto.getMobileNumber());
        if (cards.size() == ApplicationConstants.MAX_AVAILABLE_ISSUED_CARDS) {
            throw new MaxCustomerCardsReachedException(
                "Customer already have the max available number of cards");
        }
        cardRepository.save(createNewCard(cardDto));
    }

    /**
     * @param cardsFilterDto contains mobile number and card number
     * @return the list of cards with filters
     */
    @Override
    public List<CardDto> fetchCards(CardsFilterDto cardsFilterDto) {
        List<Card> cards = cardRepository.findByMobileNumberAndCardNumber(cardsFilterDto.getMobileNumber(),
            cardsFilterDto.getCardNumber());
        List<CardDto> cardsDto = cards.stream().map(card -> CardMapper.mapToCardDto(card,
            new CardDto())).toList();
        if (cardsDto.isEmpty()) {
            throw new ResourceNotFoundException("Card", "filters", cardsFilterDto.toString());
        }
        return cardsDto;
    }

    /**
     * @param cardDto contains the card information
     * @return boolean indicating whether the card is updated or not
     */
    @Override
    public boolean updateCard(CardDto cardDto) {
        List<Card> cards = cardRepository.findByMobileNumberAndCardNumber(cardDto.getMobileNumber(),
            cardDto.getCardNumber());
        if (cards.isEmpty()) return false;
        Card card = CardMapper.mapToCard(cardDto, cards.get(0));
        cardRepository.save(card);
        return true;
    }

    /**
     * @param cardsFilterDto contains mobile number and card number
     * @return boolean indicating whether the card is deleted or not
     */
    @Override
    public boolean deleteCard(CardsFilterDto cardsFilterDto) {
        List<Card> cards = cardRepository.findByMobileNumberAndCardNumber(
            cardsFilterDto.getMobileNumber(), cardsFilterDto.getCardNumber());
        if (cards.isEmpty()) return false;
        cardRepository.deleteAllById(cards.stream().map(Card::getId).toList());
        return true;
    }

    private Card createNewCard(CardDto cardDto) {
        Card card = new Card();
        card.setMobileNumber(cardDto.getMobileNumber());
        long randomCardNumber = 4973_2500_0000_0000L + new Random().nextInt(900000000);
        card.setCardNumber(Long.toString(randomCardNumber));
        card.setCardType(CardTypeEnum.DEBIT.toString());
        card.setTotalLimit(ApplicationConstants.TOTAL_CARD_LIMIT);
        card.setUsedBalance(0L);
        card.setAvailableBalance(ApplicationConstants.TOTAL_CARD_LIMIT);
        card.setExpirationDate(LocalDate.now().plusYears(3));
        return card;
    }

}
