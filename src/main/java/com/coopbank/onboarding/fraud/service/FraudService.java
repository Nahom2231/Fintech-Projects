package com.coopbank.onboarding.fraud.service;

import com.coopbank.onboarding.device.entity.Device;
import com.coopbank.onboarding.device.repository.DeviceRepository;
import com.coopbank.onboarding.fraud.dto.LoginAssessmentRequest;
import com.coopbank.onboarding.fraud.entity.RiskLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.coopbank.onboarding.customer.entity.Customer;
import com.coopbank.onboarding.customer.entity.CustomerStatus;
import com.coopbank.onboarding.customer.repository.CustomerRepository;
import com.coopbank.onboarding.verification.repository.VerificationRequestRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FraudService {

    public enum FraudDecision {
        APPROVE,
        REVIEW,
        REJECT
    }

    public record FraudAssessmentResponse(int score, RiskLevel level, FraudDecision decision, List<String> triggeredRules) {
    }
     
    private final CustomerRepository
        customerRepository;

    private final VerificationRequestRepository
        verificationRepository;
    private final DeviceRepository deviceRepository;

    public FraudAssessmentResponse
       assessLoginRisk(
        LoginAssessmentRequest request
) {

    int score = 0;

    List<String> rules =
            new ArrayList<>();

    Customer customer =
            customerRepository
                    .findByPhoneNumber(
                            request.phoneNumber()
                    )
                    .orElse(null);

    Device device =
            deviceRepository
                    .findByDeviceId(
                            request.deviceId()
                    )
                    .orElse(null);

    score += calculateDeviceRisk(
            device,
            request,
            rules
    );

    score += calculateKycRisk(
            customer,
            rules
    );

    score += calculateAccountAgeRisk(
            customer,
            rules
    );

    RiskLevel riskLevel =
            determineRiskLevel(
                    score
            );

    FraudDecision decision =
            determineDecision(
                    score
            );

    return new FraudAssessmentResponse(
            score,
            riskLevel,
            decision,
            rules
    );
}

private int calculateDeviceRisk(

        Device device,

        LoginAssessmentRequest request,

        List<String> rules

) {

    int score = 0;

    if (device == null) {

        score += 50;

        rules.add(
                "UNKNOWN_DEVICE"
        );

        return score;
    }

    if (!device.getIpAddress()
            .equals(
                    request.ipAddress()
            )) {

        score += 20;

        rules.add(
                "NEW_IP_ADDRESS"
        );
    }

    return score;
}

private int calculateKycRisk(

        Customer customer,

        List<String> rules

) {

    if (customer == null) {

        rules.add(
                "CUSTOMER_NOT_FOUND"
        );

        return 100;
    }

    if (
            customer.getStatus()
                    != CustomerStatus.VERIFIED
    ) {

        rules.add(
                "KYC_NOT_VERIFIED"
        );

        return 40;
    }

    return 0;
}

private int calculateAccountAgeRisk(

        Customer customer,

        List<String> rules

) {

    if (customer == null) {

        return 0;
    }

    long daysOld =
            java.time.Duration
                    .between(
                            customer.getCreatedAt(),
                            LocalDateTime.now()
                    )
                    .toDays();

    if (daysOld < 7) {

        rules.add(
                "NEW_CUSTOMER"
        );

        return 10;
    }

    return 0;
   }

   private RiskLevel determineRiskLevel(
        int score
) {

    if (score < 30) {

        return RiskLevel.LOW;
    }

    if (score < 60) {

        return RiskLevel.MEDIUM;
    }

    return RiskLevel.HIGH;
}

private FraudDecision determineDecision(
        int score
) {

    if (score < 30) {

        return FraudDecision.APPROVE;
    }

    if (score < 60) {

        return FraudDecision.REVIEW;
    }

        return FraudDecision.REJECT;

    }
}