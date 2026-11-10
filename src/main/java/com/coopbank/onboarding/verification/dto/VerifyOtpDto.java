package com.coopbank.onboarding.verification.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyOtpDto(

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "OTP is required")
        String otp

) {
}