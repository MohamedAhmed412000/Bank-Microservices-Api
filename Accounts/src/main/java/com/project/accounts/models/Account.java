package com.project.accounts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "ACCOUNT")
public class Account extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", updatable = false, nullable = false)
    private Customer customer;
    @NotNull
    @Max(100)
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;
    @NotNull
    @Max(200)
    @Column(name = "BRANCH_ADDRESS")
    private String branchAddress;

}
