package com.coopbank.onboarding.dashboard.dto;

import java.math.BigDecimal;

public record DashboardSummaryResponse(
        long totalAccounts,
        BigDecimal totalBalance,
        long totalTransactions,
        String kycStatus
) {
}
