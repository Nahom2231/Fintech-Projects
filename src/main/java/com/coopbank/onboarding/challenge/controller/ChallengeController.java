package com.coopbank.onboarding.challenge.controller;

import com.coopbank.onboarding.challenge.dto.VerifyChallengeRequest;
import com.coopbank.onboarding.challenge.service.ChallengeService;
import com.coopbank.onboarding.login.dto.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challenge")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("/verify")
    public AuthenticationResponse verifyChallenge(

            @Valid
            @RequestBody
            VerifyChallengeRequest request

    ) {

        return challengeService.verifyChallenge(
                request
        );
    }
}