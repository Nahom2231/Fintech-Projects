package com.coopbank.onboarding.verification.repository;

import com.coopbank.onboarding.verification.entity.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRequestRepository
        extends JpaRepository<VerificationRequest, Long> {
}