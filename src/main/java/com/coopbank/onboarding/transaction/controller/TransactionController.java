package com.coopbank.onboarding.transaction.controller;

import com.coopbank.onboarding.transaction.dto.MiniStatementResponse;
import com.coopbank.onboarding.transaction.dto.AccountStatementResponse;
import com.coopbank.onboarding.transaction.dto.TransactionHistoryResponse;
import com.coopbank.onboarding.transaction.dto.TransferRequest;
import com.coopbank.onboarding.transaction.dto.TransferResponse;
import com.coopbank.onboarding.transaction.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransactionController {

    private final TransferService transferService;

    @PostMapping
    public TransferResponse transfer(

            @Valid
            @RequestBody
            TransferRequest request

    ) {

        return transferService.transfer(
                request
        );
    }

    @GetMapping("/history/{accountNumber}")
public List<TransactionHistoryResponse>
     getTransactionHistory(

        @PathVariable
        String accountNumber

) {

    return transferService
            .getTransactionHistory(
                    accountNumber
            );
}

@GetMapping("/mini-statement/{accountNumber}")
public List<MiniStatementResponse>
miniStatement(

        @PathVariable
        String accountNumber

) {

    return transferService
            .getMiniStatement(
                    accountNumber
            );
    }
    @GetMapping("/statement/{accountNumber}")
public List<AccountStatementResponse>
statement(

        @PathVariable
        String accountNumber

) {

    return transferService
            .getAccountStatement(
                    accountNumber
            );
}
}