package com.coopbank.onboarding.audit.repository;

import com.coopbank.onboarding.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository
        extends JpaRepository<AuditLog, Long> {
}