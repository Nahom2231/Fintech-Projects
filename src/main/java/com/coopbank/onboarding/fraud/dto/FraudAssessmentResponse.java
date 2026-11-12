package com.coopbank.onboarding.fraud.dto;

import com.coopbank.onboarding.fraud.entity.RiskLevel;

import java.util.List;

public record FraudAssessmentResponse(

        Integer score,

        RiskLevel riskLevel,

        String decision,

        List<String> triggeredRules

) {
}