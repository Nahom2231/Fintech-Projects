package com.coopbank.onboarding.verification.service;

import com.coopbank.onboarding.verification.dto.RequestOtpDto;
import com.coopbank.onboarding.verification.entity.VerificationRequest;
import com.coopbank.onboarding.verification.entity.VerificationStatus;
import com.coopbank.onboarding.verification.repository.VerificationRequestRepository;
import lombok.RequiredArgsConstructor;
import com.coopbank.onboarding.verification.dto.VerifyOtpDto;
import com.coopbank.onboarding.exception.VerificationException;
import com.coopbank.onboarding.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
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

    public String verifyOtp(
        VerifyOtpDto request
) {

    VerificationRequest verificationRequest =
            repository
                    .findFirstByPhoneNumberOrderByCreatedAtDesc(
                            request.phoneNumber()
                    )
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Verification request not found"
                            )
                    );

    if (
            verificationRequest.getStatus()
                    == VerificationStatus.VERIFIED
    ) {
        throw new VerificationException(
                "OTP already verified"
        );
    }

    if (
            LocalDateTime.now()
                    .isAfter(
                            verificationRequest.getExpiresAt()
                    )
    ) {

        verificationRequest.setStatus(
                VerificationStatus.EXPIRED
        );

        repository.save(
                verificationRequest
        );

        throw new RuntimeException(
                "OTP expired"
        );
    }

    if (
            !verificationRequest.getOtpCode()
                    .equals(
                            request.otp()
                    )
    ) {

        throw new VerificationException(
                "Invalid OTP"
        );
    }

    verificationRequest.setStatus(
            VerificationStatus.VERIFIED
    );

    verificationRequest.setVerifiedAt(
            LocalDateTime.now()
    );

    repository.save(
            verificationRequest
    );

    return "Phone number verified successfully";
}
}