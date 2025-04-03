package com.project.accounts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@ToString(callSuper = true)
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "ACCOUNT")
public class Account extends BaseModel {

    @Id
    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID", updatable = false, nullable = false)
    @ToString.Exclude
    private Customer customer;
    @NotNull
    @Size(max = 20)
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;
    @NotNull
    @Size(max = 255)
    @Column(name = "BRANCH_ADDRESS")
    private String branchAddress;
    @Column(name = "NOTIFICATION_SENT")
    private Boolean notificationSent;

}
