package com.project.accounts.functions;

import com.project.accounts.services.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class AccountsFunctions {

    @Bean
    public Consumer<Long> updateNotification(IAccountService iAccountService) {
        return accountNumber -> {
            log.info("Notification sent to account {}", accountNumber);
            iAccountService.updateNotificationStatus(accountNumber);
        };
    }

}
