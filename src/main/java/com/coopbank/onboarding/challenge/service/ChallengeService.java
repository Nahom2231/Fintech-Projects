package com.coopbank.onboarding.challenge.service;

import com.coopbank.onboarding.challenge.dto.VerifyChallengeRequest;
import com.coopbank.onboarding.challenge.entity.ChallengeStatus;
import com.coopbank.onboarding.challenge.entity.LoginChallenge;
import com.coopbank.onboarding.challenge.repository.LoginChallengeRepository;
import com.coopbank.onboarding.exception.VerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final LoginChallengeRepository repository;

    public void createChallenge(
            String phoneNumber
    ) {

        String otp =
                String.valueOf(
                        ThreadLocalRandom.current()
                                .nextInt(100000, 999999)
                );

        LoginChallenge challenge =
                LoginChallenge.builder()
                        .phoneNumber(phoneNumber)
                        .otp(otp)
                        .status(ChallengeStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(
                                LocalDateTime.now()
                                        .plusMinutes(5)
                        )
                        .build();

        repository.save(challenge);

        System.out.println(
                "LOGIN OTP = " + otp
        );
    }

    public String verifyChallenge(
            VerifyChallengeRequest request
    ) {

        LoginChallenge challenge =
                repository
                        .findFirstByPhoneNumberOrderByCreatedAtDesc(
                                request.phoneNumber()
                        )
                        .orElseThrow(
                                () ->
                                        new VerificationException(
                                                "Challenge not found"
                                        )
                        );

        if (
                LocalDateTime.now()
                        .isAfter(
                                challenge.getExpiresAt()
                        )
        ) {

            challenge.setStatus(
                    ChallengeStatus.EXPIRED
            );

            repository.save(challenge);

            throw new VerificationException(
                    "Challenge expired"
            );
        }

        if (
                !challenge.getOtp()
                        .equals(
                                request.otp()
                        )
        ) {

            throw new VerificationException(
                    "Invalid OTP"
            );
        }

        challenge.setStatus(
                ChallengeStatus.VERIFIED
        );

        repository.save(challenge);

        return "Challenge verified successfully";
    }
}