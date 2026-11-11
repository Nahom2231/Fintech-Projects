package com.coopbank.onboarding.customer.dto;

public record CustomerResponse(

        Long id,

        String fullName,

        String phoneNumber,

        String email,

        String nationalId

) {
}