package com.coopbank.onboarding.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountStatementResponse(

        String referenceNumber,

        BigDecimal amount,

        String sourceAccount,

        String destinationAccount,

        String status,

        LocalDateTime createdAt

) {
}