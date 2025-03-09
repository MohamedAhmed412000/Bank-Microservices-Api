package com.project.accounts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class Customer extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @NotNull
    @Max(100)
    @Column(name = "NAME")
    private String name;
    @NotNull
    @Max(255)
    @Email
    @Column(name = "EMAIL")
    private String email;
    @NotNull
    @Min(11) @Max(11)
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Account> accounts;

}
