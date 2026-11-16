package com.coopbank.onboarding.login.dto;

public record AuthenticationResponse(

        String accessToken,

        String refreshToken

) {
}