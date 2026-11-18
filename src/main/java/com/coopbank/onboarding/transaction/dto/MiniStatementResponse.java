package com.coopbank.onboarding.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MiniStatementResponse(

        String referenceNumber,

        BigDecimal amount,

        LocalDateTime createdAt

) {
}