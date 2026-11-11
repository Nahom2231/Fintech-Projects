package com.coopbank.onboarding.ekyc.controller;

import com.coopbank.onboarding.ekyc.dto.UploadKycRequest;
import com.coopbank.onboarding.ekyc.entity.KycDocument;
import com.coopbank.onboarding.ekyc.service.KycService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.coopbank.onboarding.ekyc.dto.ReviewKycRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;

    @PostMapping("/upload")
    public KycDocument uploadDocument(

            @Valid
            @RequestBody
            UploadKycRequest request

    ) {

        return kycService
                .uploadDocument(request);
    }

    @PostMapping("/review")
    
    public KycDocument reviewDocument(

        @Valid
        @RequestBody
        ReviewKycRequest request

) {

    return kycService
            .reviewDocument(
                    request
            );
   }
}