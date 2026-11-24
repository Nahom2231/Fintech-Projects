package com.coopbank.onboarding.dashboard.service;

import com.coopbank.onboarding.account.entity.Account;
import com.coopbank.onboarding.account.repository.AccountRepository;
import com.coopbank.onboarding.dashboard.dto.DashboardSummaryResponse;
import com.coopbank.onboarding.ekyc.entity.KycDocument;
import com.coopbank.onboarding.ekyc.repository.KycDocumentRepository;
import com.coopbank.onboarding.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final KycDocumentRepository kycDocumentRepository;

    public DashboardSummaryResponse getSummary() {
        long totalAccounts = accountRepository.count();

        BigDecimal totalBalance = accountRepository.findAll()
                .stream()
                .map(Account::getAvailableBalance)
                .filter(balance -> balance != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalTransactions = transactionRepository.count();

        String kycStatus = "VERIFIED";
        List<KycDocument> documents = kycDocumentRepository.findAll();
        if (!documents.isEmpty()) {
            kycStatus = documents.get(documents.size() - 1).getStatus().name();
        } else {
            kycStatus = "PENDING";
        }

        return new DashboardSummaryResponse(
                totalAccounts,
                totalBalance,
                totalTransactions,
                kycStatus
        );
    }
}
