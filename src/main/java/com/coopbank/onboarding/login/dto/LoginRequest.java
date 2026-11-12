package com.coopbank.onboarding.login.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank
        String phoneNumber,

        @NotBlank
        String deviceId,

        @NotBlank
        String ipAddress

) {
}