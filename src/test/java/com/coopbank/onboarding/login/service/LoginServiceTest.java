package com.coopbank.onboarding.login.service;

import com.coopbank.onboarding.challenge.service.ChallengeService;
import com.coopbank.onboarding.fraud.dto.LoginAssessmentRequest;
import com.coopbank.onboarding.fraud.entity.RiskLevel;
import com.coopbank.onboarding.fraud.service.FraudService;
import com.coopbank.onboarding.fraud.service.FraudService.FraudAssessmentResponse;
import com.coopbank.onboarding.fraud.service.FraudService.FraudDecision;
import com.coopbank.onboarding.login.dto.LoginRequest;
import com.coopbank.onboarding.login.dto.LoginResponse;
import com.coopbank.onboarding.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private FraudService fraudService;

    @Mock
    private ChallengeService challengeService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginService loginService;

    @Test
    void reviewDecisionCreatesChallengeAndRequiresVerification() {
        LoginRequest request = new LoginRequest("5550100", "device-1", "192.0.2.1");

        when(fraudService.assessLoginRisk(
                new LoginAssessmentRequest(
                        request.phoneNumber(),
                        request.deviceId(),
                        request.ipAddress()
                )
        )).thenReturn(new FraudAssessmentResponse(
                40,
                RiskLevel.MEDIUM,
                FraudDecision.REVIEW,
                List.of()
        ));

        LoginResponse response = loginService.login(request);

        assertEquals("CHALLENGE_REQUIRED", response.status());
        verify(challengeService).createChallenge(request.phoneNumber());
    }
}
