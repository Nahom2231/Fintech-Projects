package com.coopbank.onboarding.verification.controller;
import com.coopbank.onboarding.verification.dto.VerifyOtpDto;
import com.coopbank.onboarding.verification.dto.RequestOtpDto;
import com.coopbank.onboarding.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verifications")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOtp(
            @RequestBody RequestOtpDto dto
    ) {

        verificationService.requestOtp(dto);

        return ResponseEntity.ok(
                "OTP Generated Successfully"
        );
    }
    @PostMapping("/verify-otp")
    public String verifyOtp(

        @Valid
        @RequestBody
        VerifyOtpDto request

   ) {

    return verificationService
            .verifyOtp(request);
    }
   }