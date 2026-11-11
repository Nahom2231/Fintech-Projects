package com.coopbank.onboarding.ekyc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UploadKycRequest(

        @NotNull
        Long customerId,

        @NotBlank
        String documentType,

        @NotBlank
        String documentNumber,

        @NotBlank
        String fileName

) {
}