package com.coopbank.onboarding.transaction.dto;

public record TransferResponse(

        String referenceNumber,

        String status,

        String message

) {
}