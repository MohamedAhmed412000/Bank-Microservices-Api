package com.project.loans.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
public record CustomerSupportInfoDto(
    String message,
    Map<String, String> accountInfo,
    List<String> contactList) {
}
