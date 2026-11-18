package com.coopbank.onboarding.ledger.dto;

import com.coopbank.onboarding.ledger.entity.EntryType;

import java.math.BigDecimal;

public record PostLedgerEntryRequest(

        Long accountId,

        EntryType entryType,

        BigDecimal amount,

        String narration

) {
}