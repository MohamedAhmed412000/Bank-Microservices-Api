package com.project.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDto {
    @NotEmpty(message = "Customer name can't be null or empty")
    @Size(min = 5, max = 50, message = "Customer name must be between 5 and 50 characters")
    private String name;
    @NotEmpty(message = "Customer email can't be null or empty")
    @Email(message = "Email address must be valid value")
    private String email;
    @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits")
    private String mobileNumber;
    private List<AccountDto> accounts;
}
