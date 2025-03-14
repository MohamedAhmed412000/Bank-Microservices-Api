package com.project.loans.controllers;

import com.project.loans.dto.ErrorResponseDto;
import com.project.loans.dto.LoanDto;
import com.project.loans.dto.ResponseDto;
import com.project.loans.enums.ResponsesEnum;
import com.project.loans.services.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD Rest APIs for Loans Microservice",
    description = "Provides RESTFUL APIs for managing loans."
)
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class LoansController {

    private final ILoanService iLoanService;

    @Operation(
        summary = "Create a new loan",
        description = "Creates a new loan based on the provided loan details.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Loan created successfully.",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request. Provided loan details are invalid.",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @PostMapping(value = "/create-loan")
    public ResponseEntity<ResponseDto> createLoan(
        @RequestParam("mobile-number")
        @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile Number must be 11 digits")
        String mobileNumber
    ) {
        iLoanService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(
            ResponsesEnum.RESPONSE_CREATED.getMessage(),
            ResponsesEnum.RESPONSE_CREATED.getStatusCode().toString()
        ));
    }

    @Operation(
        summary = "Fetch loan details",
        description = "Fetches loan details for a customer based on the provided mobile number.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Loan details retrieved successfully",
                content = @Content(schema = @Schema(implementation = LoanDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Loan not found for the provided mobile number",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @GetMapping(value = "/fetch-loan-details")
    public ResponseEntity<LoanDto> fetchLoanDetails(
        @RequestParam("mobile-number")
        @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile Number must be 11 digits")
        String mobileNumber
    ) {
        LoanDto loanDto = iLoanService.fetchLoanDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loanDto);
    }

    @Operation(
        summary = "Update loan details",
        description = "Updates loan details based on the provided mobile number.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Loan details updated successfully",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request. Provided loan details are invalid.",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @PutMapping(value = "/update-loan-info", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> updateLoanInfo(@Valid @RequestBody LoanDto loanDto) {
        boolean isUpdate = iLoanService.updateLoan(loanDto);
        if (isUpdate) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
                ResponsesEnum.RESPONSE_OK.getMessage(),
                ResponsesEnum.RESPONSE_OK.getStatusCode().toString()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(
                ResponsesEnum.RESPONSE_BAD_REQUEST.getMessage(),
                ResponsesEnum.RESPONSE_BAD_REQUEST.getStatusCode().toString()
            ));
        }
    }

    @Operation(
        summary = "Delete Loan Details",
        description = "Delete loan details based on the provided mobile number.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Loan details deleted successfully",
                content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Loan not found for the provided mobile number",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
        }
    )
    @DeleteMapping(value = "/delete-loan-info")
    public ResponseEntity<ResponseDto> deleteLoanInfo(
        @RequestParam(name = "mobile-number")
        @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits")
        String mobileNumber
    ) {
        boolean isDeleted = iLoanService.deleteLoan(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
                ResponsesEnum.RESPONSE_OK.getMessage(),
                ResponsesEnum.RESPONSE_OK.getStatusCode().toString()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(
                ResponsesEnum.RESPONSE_BAD_REQUEST.getMessage(),
                ResponsesEnum.RESPONSE_BAD_REQUEST.getStatusCode().toString()
            ));
        }
    }

}
