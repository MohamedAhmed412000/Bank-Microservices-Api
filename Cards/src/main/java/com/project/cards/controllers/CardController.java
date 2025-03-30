package com.project.cards.controllers;

import com.project.cards.constants.ApplicationConstants;
import com.project.cards.dto.CardDto;
import com.project.cards.dto.CardsFilterDto;
import com.project.cards.dto.ErrorResponseDto;
import com.project.cards.dto.ResponseDto;
import com.project.cards.enums.ResponsesEnum;
import com.project.cards.exceptions.ResourceNotFoundException;
import com.project.cards.services.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(
    name = "CRUD Rest APIs for Cards Microservice",
    description = "Provides RESTFUL APIs for managing cards."
)
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/cards")
@Validated
public class CardController {

    private final ICardService iCardService;

    @Operation(
        summary = "Create a new card for a customer",
        description = "Creates a new card for a customer based on the provided card details.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Card created successfully.",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request. Provided customer details are invalid.",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @PostMapping(value = "/issue-card", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDto> createAccount(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @Valid @RequestBody CardDto cardDto
    ) {
        iCardService.issueCard(cardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(
            ResponsesEnum.RESPONSE_CREATED.getMessage(),
            ResponsesEnum.RESPONSE_CREATED.getStatusCode().toString()
        ));
    }

    @Operation(
        summary = "Fetch cards details for a customer",
        description = "Fetches cards details for a customer based on the provided filters.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Cards details retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CardDto.class)))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Cards not found for the provided filters",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @GetMapping(value = "/fetch-cards")
    public ResponseEntity<List<CardDto>> fetchCards(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @Valid @ModelAttribute CardsFilterDto cardsFilterDto
    ) {
        log.debug("Invoked API & Found requestId: {}", requestId);
        List<CardDto> cardsDto = iCardService.fetchCards(cardsFilterDto);
        if (cardsDto.isEmpty()) {
            throw new ResourceNotFoundException("Card", "filters", cardsFilterDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(
        summary = "Update card details",
        description = "Updates card details for a customer.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Card details updated successfully",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request. Provided card details are invalid.",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @PutMapping(value = "/update-card-info", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDto> updateCardDetails(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @Valid @RequestBody CardDto cardDto
    ) {
        boolean isUpdated = iCardService.updateCard(cardDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
                ResponsesEnum.RESPONSE_OK.getMessage(),
                ResponsesEnum.RESPONSE_OK.getStatusCode().toString()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(
                ResponsesEnum.RESPONSE_NOT_FOUND.getMessage(),
                ResponsesEnum.RESPONSE_NOT_FOUND.getStatusCode().toString()
            ));
        }
    }

    @Operation(
        summary = "Delete Card Details",
        description = "Delete card details for a customer based on the provided filters.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Cards details deleted successfully",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Cards not found for the provided filters",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @DeleteMapping(value = "/delete-card-info")
    public ResponseEntity<ResponseDto> deleteCardInfo(
        @RequestHeader(ApplicationConstants.REQUEST_HEADER_NAME) String requestId,
        @Valid @ModelAttribute CardsFilterDto cardsFilterDto
    ) {
        boolean isDeleted = iCardService.deleteCard(cardsFilterDto);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
                ResponsesEnum.RESPONSE_OK.getMessage(),
                ResponsesEnum.RESPONSE_OK.getStatusCode().toString()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(
                ResponsesEnum.RESPONSE_NOT_FOUND.getMessage(),
                ResponsesEnum.RESPONSE_NOT_FOUND.getStatusCode().toString()
            ));
        }
    }

}
