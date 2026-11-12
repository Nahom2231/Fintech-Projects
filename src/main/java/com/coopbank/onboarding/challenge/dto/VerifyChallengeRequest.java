package com.coopbank.onboarding.challenge.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyChallengeRequest(

        @NotBlank
        String phoneNumber,

        @NotBlank
        String otp

) {
}