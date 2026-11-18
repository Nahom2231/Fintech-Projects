package com.coopbank.onboarding.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionHistoryResponse(

        String referenceNumber,

        String sourceAccount,

        String destinationAccount,

        BigDecimal amount,

        String status,

        LocalDateTime createdAt

) {
}