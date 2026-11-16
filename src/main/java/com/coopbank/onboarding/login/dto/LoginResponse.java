package com.coopbank.onboarding.login.dto;

public record LoginResponse(

        String status,

        String accessToken,

        String refreshToken

) {
}