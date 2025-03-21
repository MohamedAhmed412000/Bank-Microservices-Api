package com.project.cards.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "cards")
public class CustomerSupportInfoDto {
    private String message;
    private Map<String, String> accountInfo;
    private List<String> contactList;
}
