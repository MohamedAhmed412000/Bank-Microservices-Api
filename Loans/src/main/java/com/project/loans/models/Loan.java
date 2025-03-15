package com.project.loans.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter @Setter
@ToString(callSuper = true)
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "LOAN")
public class Loan extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOAN_ID")
    private Long id;
    @NotEmpty
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    @NotEmpty
    @Column(name = "LOAN_NUMBER")
    private String loanNumber;
    @NotEmpty
    @Column(name = "LOAN_TYPE")
    private String loanType;
    @Min(0)
    @Column(name = "TOTAL_LOAN")
    private Long loanAmount;
    @Min(0)
    @Column(name = "AMOUNT_PAID")
    private Long amountPaid;
    @Min(0)
    @Column(name = "OUTSTANDING_AMOUNT")
    private Long outstandingAmount;

}
