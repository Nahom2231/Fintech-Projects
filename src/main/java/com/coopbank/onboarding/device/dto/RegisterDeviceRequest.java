package com.coopbank.onboarding.device.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDeviceRequest(

        @NotNull
        Long customerId,

        @NotBlank
        String deviceId,

        @NotBlank
        String deviceName,

        @NotBlank
        String ipAddress

) {
}