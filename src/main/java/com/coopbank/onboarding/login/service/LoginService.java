package com.coopbank.onboarding.login.service;

import com.coopbank.onboarding.fraud.dto.FraudAssessmentResponse;
import com.coopbank.onboarding.fraud.dto.LoginAssessmentRequest;
import com.coopbank.onboarding.fraud.service.FraudService;
import com.coopbank.onboarding.fraud.service.FraudService.FraudDecision;
import com.coopbank.onboarding.login.dto.LoginRequest;
import com.coopbank.onboarding.login.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final FraudService fraudService;

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

        if (
                assessment.decision()
                        == FraudDecision.REJECT
        ) {

            return new LoginResponse(
                    "BLOCKED",
                    "Login blocked due to high fraud risk"
            );
        }

        if (
                assessment.decision()
                        == FraudDecision.REVIEW
        ) {

            return new LoginResponse(
                    "CHALLENGE_REQUIRED",
                    "Additional OTP verification required"
            );
        }

        return new LoginResponse(
                "SUCCESS",
                "Login successful"
        );
    }
}