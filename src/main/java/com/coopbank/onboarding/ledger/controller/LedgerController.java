package com.coopbank.onboarding.ledger.controller;

import com.coopbank.onboarding.ledger.dto.PostLedgerEntryRequest;
import com.coopbank.onboarding.ledger.entity.LedgerEntry;
import com.coopbank.onboarding.ledger.service.LedgerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    @PostMapping
    public LedgerEntry postEntry(

            @Valid
            @RequestBody
            PostLedgerEntryRequest request

    ) {

        return ledgerService.postEntry(
                request
        );
    }
}