package com.coopbank.onboarding.ekyc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewKycRequest(

        @NotNull
        Long documentId,

        @NotBlank
        String decision

) {
}