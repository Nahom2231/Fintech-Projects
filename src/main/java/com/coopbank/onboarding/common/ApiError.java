package com.coopbank.onboarding.common;

import java.time.LocalDateTime;

public record ApiError(

        LocalDateTime timestamp,

        String message,

        int status

) {
}