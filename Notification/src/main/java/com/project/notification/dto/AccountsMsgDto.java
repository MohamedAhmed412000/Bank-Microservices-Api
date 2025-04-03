package com.project.notification.dto;

public record AccountsMsgDto(
    Long accountNumber,
    String name,
    String email,
    String mobileNumber
) {
}
