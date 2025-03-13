package com.project.accounts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString(callSuper = true)
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class Customer extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @NotNull
    @Size(max = 100)
    @Column(name = "NAME")
    private String name;
    @NotNull
    @Size(max = 255)
    @Email
    @Column(name = "EMAIL")
    private String email;
    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account) {
        account.setCustomer(this);
    }

}
