package com.coopbank.onboarding.verification.repository;

import com.coopbank.onboarding.verification.entity.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationRequestRepository
        extends JpaRepository<VerificationRequest, Long> {
             Optional<VerificationRequest>
    findFirstByPhoneNumberOrderByCreatedAtDesc(
        String phoneNumber
    );
}
