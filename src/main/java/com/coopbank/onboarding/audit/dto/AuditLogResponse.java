package com.coopbank.onboarding.audit.dto;

import java.time.LocalDateTime;

public record AuditLogResponse(

        String action,

        String username,

        LocalDateTime createdAt

) {
}