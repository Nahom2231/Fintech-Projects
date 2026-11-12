package com.coopbank.onboarding.fraud.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginAssessmentRequest(

        @NotBlank
        String phoneNumber,

        @NotBlank
        String deviceId,

        @NotBlank
        String ipAddress

) {
}