package com.coopbank.onboarding.fraud.controller;

import com.coopbank.onboarding.fraud.dto.LoginAssessmentRequest;
import com.coopbank.onboarding.fraud.service.FraudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud")
@RequiredArgsConstructor
public class FraudController {

    private final FraudService fraudService;

    @PostMapping("/assess-login")
        public Object
    assessLoginRisk(

            @Valid
            @RequestBody
            LoginAssessmentRequest request

    ) {

        return fraudService
                .assessLoginRisk(
                        request
                );
    }
}