package com.coopbank.onboarding.transaction.service;

import com.coopbank.onboarding.account.entity.Account;
import com.coopbank.onboarding.account.repository.AccountRepository;
import com.coopbank.onboarding.audit.service.AuditService;
import com.coopbank.onboarding.exception.VerificationException;
import com.coopbank.onboarding.ledger.dto.PostLedgerEntryRequest;
import com.coopbank.onboarding.ledger.entity.EntryType;
import com.coopbank.onboarding.ledger.service.LedgerService;
import com.coopbank.onboarding.transaction.dto.AccountStatementResponse;
import com.coopbank.onboarding.transaction.dto.MiniStatementResponse;
import com.coopbank.onboarding.transaction.dto.TransactionHistoryResponse;
import com.coopbank.onboarding.transaction.dto.TransferRequest;
import com.coopbank.onboarding.transaction.dto.TransferResponse;
import com.coopbank.onboarding.transaction.entity.Transaction;
import com.coopbank.onboarding.transaction.entity.TransactionStatus;
import com.coopbank.onboarding.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LedgerService ledgerService;
    private final AuditService auditService;

    @Transactional
    public TransferResponse transfer(
            TransferRequest request
    ) {

        Account sourceAccount =
                accountRepository
                        .findByAccountNumber(
                                request.sourceAccount()
                        )
                        .orElseThrow(
                                () -> new VerificationException(
                                        "Source account not found"
                                )
                        );

        Account destinationAccount =
                accountRepository
                        .findByAccountNumber(
                                request.destinationAccount()
                        )
                        .orElseThrow(
                                () -> new VerificationException(
                                        "Destination account not found"
                                )
                        );

        if (
                sourceAccount
                        .getAvailableBalance()
                        .compareTo(
                                request.amount()
                        ) < 0
        ) {

            throw new VerificationException(
                    "Insufficient balance"
            );
        }

        String reference =
                UUID.randomUUID()
                        .toString();

        ledgerService.postEntry(
                new PostLedgerEntryRequest(
                        sourceAccount.getId(),
                        EntryType.DEBIT,
                        request.amount(),
                        "Transfer Out"
                )
        );

        ledgerService.postEntry(
                new PostLedgerEntryRequest(
                        destinationAccount.getId(),
                        EntryType.CREDIT,
                        request.amount(),
                        "Transfer In"
                )
        );

        sourceAccount.setAvailableBalance(
                sourceAccount
                        .getAvailableBalance()
                        .subtract(
                                request.amount()
                        )
        );

        destinationAccount.setAvailableBalance(
                destinationAccount
                        .getAvailableBalance()
                        .add(
                                request.amount()
                        )
        );

        accountRepository.save(
                sourceAccount
        );

        accountRepository.save(
                destinationAccount
        );

        Transaction transaction =
                Transaction.builder()
                        .referenceNumber(
                                reference
                        )
                        .sourceAccountNumber(
                                sourceAccount
                                        .getAccountNumber()
                        )
                        .destinationAccountNumber(
                                destinationAccount
                                        .getAccountNumber()
                        )
                        .amount(
                                request.amount()
                        )
                        .status(
                                TransactionStatus.COMPLETED
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        transactionRepository.save(
        transaction
            );

     auditService.log(
        "TRANSFER_COMPLETED",
        sourceAccount.getAccountNumber()
);

    return new TransferResponse(
        reference,
        "SUCCESS",
        "Transfer completed successfully"
);
    }

    public List<TransactionHistoryResponse>
     getTransactionHistory(
        String accountNumber
      ) {

    return transactionRepository
            .findBySourceAccountNumberOrDestinationAccountNumberOrderByCreatedAtDesc(
                    accountNumber,
                    accountNumber
            )
            .stream()
            .map(
                    transaction ->
                            new TransactionHistoryResponse(
                                    transaction.getReferenceNumber(),
                                    transaction.getSourceAccountNumber(),
                                    transaction.getDestinationAccountNumber(),
                                    transaction.getAmount(),
                                    transaction.getStatus().name(),
                                    transaction.getCreatedAt()
                            )
            )
            .toList();
}

     public List<MiniStatementResponse>
     getMiniStatement(
        String accountNumber
     ) {

    return transactionRepository
            .findTop5BySourceAccountNumberOrderByCreatedAtDesc(
                    accountNumber
            )
            .stream()
            .map(
                    transaction ->
                            new MiniStatementResponse(
                                    transaction.getReferenceNumber(),
                                    transaction.getAmount(),
                                    transaction.getCreatedAt()
                            )
            )
            .toList();
       }

       public List<AccountStatementResponse>
        getAccountStatement(
        String accountNumber
        ) {

    return transactionRepository
            .findBySourceAccountNumber(
                    accountNumber
            )
            .stream()
            .map(
                    transaction ->
                            new AccountStatementResponse(
                                    transaction.getReferenceNumber(),
                                    transaction.getAmount(),
                                    transaction.getSourceAccountNumber(),
                                    transaction.getDestinationAccountNumber(),
                                    transaction.getStatus().name(),
                                    transaction.getCreatedAt()
                            )
            )
            .toList();
    }
}