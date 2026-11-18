package com.coopbank.onboarding.audit.controller;

import com.coopbank.onboarding.audit.dto.AuditLogResponse;
import com.coopbank.onboarding.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public List<AuditLogResponse> getAuditLogs() {

        return auditService.getLogs();
    }

}