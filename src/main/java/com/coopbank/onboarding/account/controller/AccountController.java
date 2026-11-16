package com.coopbank.onboarding.account.controller;

import com.coopbank.onboarding.account.dto.CreateAccountRequest;
import com.coopbank.onboarding.account.entity.Account;
import com.coopbank.onboarding.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public Account createAccount(

            @Valid
            @RequestBody
            CreateAccountRequest request

    ) {

        return accountService
                .createAccount(
                        request
                );
    }
}