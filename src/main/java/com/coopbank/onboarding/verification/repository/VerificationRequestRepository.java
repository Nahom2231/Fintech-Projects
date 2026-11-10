package com.coopbank.onboarding.verification.repository;

import com.coopbank.onboarding.verification.entity.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

public interface VerificationRequestRepository
        extends JpaRepository<VerificationRequest, Long> {
             Optional<VerificationRequest>
    findFirstByPhoneNumberOrderByCreatedAtDesc(
        String phoneNumber
    );
    List<VerificationRequest>
    findByPhoneNumberAndCreatedAtAfter(
            String phoneNumber,
            LocalDateTime createdAt
    );
}
