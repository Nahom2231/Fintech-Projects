package com.coopbank.onboarding.ledger.service;

import com.coopbank.onboarding.account.entity.Account;
import com.coopbank.onboarding.account.repository.AccountRepository;
import com.coopbank.onboarding.exception.ResourceNotFoundException;
import com.coopbank.onboarding.ledger.dto.PostLedgerEntryRequest;
import com.coopbank.onboarding.ledger.entity.EntryType;
import com.coopbank.onboarding.ledger.entity.LedgerEntry;
import com.coopbank.onboarding.ledger.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;
    private final AccountRepository accountRepository;

    public LedgerEntry postEntry(
            PostLedgerEntryRequest request
    ) {

       Account account =
        accountRepository
                .findById(
                        request.accountId()
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Account not found"
                        )
                );

if (request.entryType() == EntryType.CREDIT) {

    account.setAvailableBalance(
            account.getAvailableBalance()
                    .add(request.amount())
    );

    account.setLedgerBalance(
            account.getLedgerBalance()
                    .add(request.amount())
    );

} else {

    account.setAvailableBalance(
            account.getAvailableBalance()
                    .subtract(request.amount())
    );

    account.setLedgerBalance(
            account.getLedgerBalance()
                    .subtract(request.amount())
    );
}

accountRepository.save(account);

LedgerEntry entry =
        LedgerEntry.builder()
                .referenceNumber(
                        UUID.randomUUID().toString()
                )
                .entryType(
                        request.entryType()
                )
                .amount(
                        request.amount()
                )
                .narration(
                        request.narration()
                )
                .createdAt(
                        LocalDateTime.now()
                )
                .account(
                        account
                )
                .build();

return ledgerRepository.save(entry);
        
    }
}