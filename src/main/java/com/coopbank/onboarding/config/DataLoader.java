package com.coopbank.onboarding.config;

import com.coopbank.onboarding.verification.entity.VerificationRequest;
import com.coopbank.onboarding.verification.entity.VerificationStatus;
import com.coopbank.onboarding.verification.repository.VerificationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final VerificationRequestRepository repository;

    @Override
    public void run(String... args) {

        VerificationRequest request =
                VerificationRequest.builder()
                        .phoneNumber("0912345678")
                        .otpCode("123456")
                        .status(VerificationStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusMinutes(5))
                        .build();

        repository.save(request);

        System.out.println("Verification Request Saved");
    }
}