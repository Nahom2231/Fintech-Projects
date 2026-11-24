package com.coopbank.onboarding.account.service;

import com.coopbank.onboarding.account.dto.CreateAccountRequest;
import com.coopbank.onboarding.account.entity.*;
import com.coopbank.onboarding.account.repository.AccountRepository;
import com.coopbank.onboarding.customer.entity.Customer;
import com.coopbank.onboarding.customer.repository.CustomerRepository;
import com.coopbank.onboarding.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public Account createAccount(
            CreateAccountRequest request
    ) {

        Customer customer =
                customerRepository
                        .findById(request.customerId())
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Customer not found"
                                        )
                        );

        String accountNumber =
                generateAccountNumber();

        Account account =
                Account.builder()
                        .accountNumber(
                                accountNumber
                        )
                        .accountType(
                                request.accountType()
                        )
                        .status(
                                AccountStatus.ACTIVE
                        )
                        .availableBalance(
                                BigDecimal.ZERO
                        )
                        .ledgerBalance(
                                BigDecimal.ZERO
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .customer(
                                customer
                        )
                        .build();

        return accountRepository.save(
                account
        );
    }

    private String generateAccountNumber() {

        return "100"
                + (1000000
                + new Random().nextInt(9000000));
    }

    public List<Account> getAccounts() {

    return accountRepository.findAll();
}
}