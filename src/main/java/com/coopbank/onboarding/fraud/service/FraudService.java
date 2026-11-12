package com.coopbank.onboarding.fraud.service;

import com.coopbank.onboarding.device.entity.Device;
import com.coopbank.onboarding.device.repository.DeviceRepository;
import com.coopbank.onboarding.fraud.dto.LoginAssessmentRequest;
import com.coopbank.onboarding.fraud.entity.RiskLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FraudService {

    public record FraudAssessmentResponse(int score, RiskLevel level, String message) {
    }

    private final DeviceRepository deviceRepository;

    public FraudAssessmentResponse
    assessLoginRisk(
            LoginAssessmentRequest request
    ) {

        int score = 0;

        Device device =
                deviceRepository
                        .findByDeviceId(
                                request.deviceId()
                        )
                        .orElse(null);

        if (device == null) {

            score += 50;

        } else {

            if (!device.getIpAddress()
                    .equals(request.ipAddress())) {

                score += 20;
            }
        }

        RiskLevel level;

        if (score < 30) {

            level = RiskLevel.LOW;

        } else if (score < 60) {

            level = RiskLevel.MEDIUM;

        } else {

            level = RiskLevel.HIGH;
        }

        return new FraudAssessmentResponse(

                score,

                level,

                "Fraud assessment completed"

        );
    }
}