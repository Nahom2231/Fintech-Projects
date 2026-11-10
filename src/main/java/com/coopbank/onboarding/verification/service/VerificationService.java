package com.coopbank.onboarding.verification.service;

import com.coopbank.onboarding.verification.dto.RequestOtpDto;
import com.coopbank.onboarding.verification.entity.VerificationRequest;
import com.coopbank.onboarding.verification.entity.VerificationStatus;
import com.coopbank.onboarding.verification.repository.VerificationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRequestRepository repository;

    public void requestOtp(RequestOtpDto request) {

        String otp =
                String.valueOf(
                        ThreadLocalRandom.current()
                                .nextInt(100000, 999999)
                );

        VerificationRequest verificationRequest =
                VerificationRequest.builder()
                        .phoneNumber(request.phoneNumber())
                        .otpCode(otp)
                        .status(VerificationStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusMinutes(5))
                        .build();

        repository.save(verificationRequest);

        System.out.println(
                "Generated OTP: " + otp
        );
    }
}