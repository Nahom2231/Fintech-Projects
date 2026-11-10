package com.coopbank.onboarding.verification.dto;

import jakarta.validation.constraints.NotBlank;
public record RequestOtpDto(

        @NotBlank(message = "Phone number is required")
        String phoneNumber
) {
}