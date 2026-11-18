package com.coopbank.onboarding.transaction.dto;

import java.math.BigDecimal;

public record TransferRequest(

        String sourceAccount,

        String destinationAccount,

        BigDecimal amount

) {
}