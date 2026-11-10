package com.coopbank.onboarding.exception;

import com.coopbank.onboarding.common.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            VerificationException.class
    )
    public ResponseEntity<ApiError>
    handleVerificationException(
            VerificationException exception
    ) {

        ApiError error =
                new ApiError(

                        LocalDateTime.now(),

                        exception.getMessage(),

                        HttpStatus.BAD_REQUEST.value()

                );

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    @ExceptionHandler(
            ResourceNotFoundException.class
    )
    public ResponseEntity<ApiError>
    handleNotFound(
            ResourceNotFoundException exception
    ) {

        ApiError error =
                new ApiError(

                        LocalDateTime.now(),

                        exception.getMessage(),

                        HttpStatus.NOT_FOUND.value()

                );

        return ResponseEntity
                .status(
                        HttpStatus.NOT_FOUND
                )
                .body(error);
    }

}