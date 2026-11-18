package com.coopbank.onboarding.login.service;

import com.coopbank.onboarding.fraud.dto.LoginAssessmentRequest;
import com.coopbank.onboarding.fraud.service.FraudService;
import com.coopbank.onboarding.fraud.service.FraudService.FraudAssessmentResponse;
import com.coopbank.onboarding.fraud.service.FraudService.FraudDecision;
import com.coopbank.onboarding.challenge.service.ChallengeService;
import com.coopbank.onboarding.exception.VerificationException;
import com.coopbank.onboarding.login.dto.LoginRequest;
import com.coopbank.onboarding.login.dto.LoginResponse;
import com.coopbank.onboarding.login.dto.RefreshTokenRequest;
import com.coopbank.onboarding.login.dto.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.coopbank.onboarding.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final FraudService fraudService;
    private final ChallengeService challengeService;
    private final JwtService jwtService;

    public LoginResponse login(
            LoginRequest request
    ) {

        LoginAssessmentRequest assessmentRequest =
                new LoginAssessmentRequest(
                        request.phoneNumber(),
                        request.deviceId(),
                        request.ipAddress()
                );

        FraudAssessmentResponse assessment =
                fraudService.assessLoginRisk(
                        assessmentRequest
                );

        if (assessment.decision() == FraudDecision.REJECT) {
            return new LoginResponse(
                    "BLOCKED",
                    null,
                    null
            );
        }

        if (assessment.decision() == FraudDecision.REVIEW) {
            challengeService.createChallenge(request.phoneNumber());
            return new LoginResponse(
                    "CHALLENGE_REQUIRED",
                    null,
                    null
            );
        }

        String accessToken =
        jwtService.generateAccessToken(
                request.phoneNumber()
        );

              String refreshToken =
               jwtService.generateRefreshToken(
                request.phoneNumber()
                 );

return new LoginResponse(
        "SUCCESS",
        accessToken,
        refreshToken
);
    }

    public RefreshTokenResponse refreshAccessToken(
        RefreshTokenRequest request
       ) {

        try{

    String phoneNumber =
            jwtService.extractPhoneNumber(
                    request.refreshToken()
            );

    String accessToken =
            jwtService.generateAccessToken(
                    phoneNumber
            );

    return new RefreshTokenResponse(
            accessToken
    );
} catch (Exception ex) {

        throw new VerificationException(
                "Invalid refresh token"
        );
    }
       
    }
}