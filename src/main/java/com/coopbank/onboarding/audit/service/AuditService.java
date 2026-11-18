package com.coopbank.onboarding.audit.service;

import com.coopbank.onboarding.audit.dto.AuditLogResponse;
import com.coopbank.onboarding.audit.entity.AuditLog;
import com.coopbank.onboarding.audit.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository repository;

    public void log(String action, String username) {
        repository.save(
                AuditLog.builder()
                        .action(action)
                        .username(username)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

   public List<AuditLogResponse> getLogs() {

    return repository
            .findAll()
            .stream()
            .map(
                    log -> new AuditLogResponse(
                            log.getAction(),
                            log.getUsername(),
                            log.getCreatedAt()
                    )
            )
            .toList();
}
}