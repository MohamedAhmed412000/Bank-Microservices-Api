package com.project.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerMaxAccountsReachedException extends RuntimeException {

    public CustomerMaxAccountsReachedException(String message) {
        super(message);
    }

}
