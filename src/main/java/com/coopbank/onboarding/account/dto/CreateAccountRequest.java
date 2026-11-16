package com.coopbank.onboarding.account.dto;

import com.coopbank.onboarding.account.entity.AccountType;

public record CreateAccountRequest(

        Long customerId,

        AccountType accountType

) {
}