package com.project.accounts.mappers;

import com.project.accounts.dto.AccountDto;
import com.project.accounts.models.Account;

import java.time.LocalDateTime;

public class AccountMapper {
    
    public static AccountDto mapToAccountDto(Account account, AccountDto accountDto) {
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }
    
    public static Account mapToAccount(AccountDto accountDto, Account account) {
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("SYSTEM");
        return account;
    }
    
}
