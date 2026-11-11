package com.coopbank.onboarding.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterCustomerRequest(

        @NotBlank
        String fullName,

        @NotBlank
        String phoneNumber,

        @NotBlank
        String email,

        @NotBlank
        String nationalId

) {
}