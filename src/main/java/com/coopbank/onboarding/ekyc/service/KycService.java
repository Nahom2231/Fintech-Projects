package com.coopbank.onboarding.ekyc.service;

import com.coopbank.onboarding.customer.entity.Customer;
import com.coopbank.onboarding.customer.repository.CustomerRepository;
import com.coopbank.onboarding.ekyc.dto.UploadKycRequest;
import com.coopbank.onboarding.ekyc.entity.KycDocument;
import com.coopbank.onboarding.ekyc.entity.KycStatus;
import com.coopbank.onboarding.ekyc.repository.KycDocumentRepository;
import com.coopbank.onboarding.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.coopbank.onboarding.customer.entity.CustomerStatus;
import com.coopbank.onboarding.ekyc.dto.ReviewKycRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KycService {

    private final KycDocumentRepository kycRepository;

    private final CustomerRepository customerRepository;

    public KycDocument uploadDocument(
            UploadKycRequest request
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

        KycDocument document =
                KycDocument.builder()
                        .customer(customer)
                        .documentNumber(
                                request.documentNumber()
                        )
                        .documentType(
                                request.documentType()
                        )
                        .fileName(
                                request.fileName()
                        )
                        .status(KycStatus.PENDING)
                        .uploadedAt(
                                LocalDateTime.now()
                        )
                        .build();

        return kycRepository.save(document);
    }

 public KycDocument reviewDocument(
        ReviewKycRequest request
) {

    KycDocument document =
            kycRepository
                    .findById(
                            request.documentId()
                    )
                    .orElseThrow(
                            () ->
                                    new ResourceNotFoundException(
                                            "Document not found"
                                    )
                    );

    if (
            request.decision()
                    .equalsIgnoreCase(
                            "APPROVE"
                    )
    ) {

        document.setStatus(
                KycStatus.VERIFIED
        );

        document
                .getCustomer()
                .setStatus(
                        CustomerStatus.VERIFIED
                );

    } else {

        document.setStatus(
                KycStatus.REJECTED
        );

        document
                .getCustomer()
                .setStatus(
                        CustomerStatus.REJECTED
                );
    }

    return kycRepository.save(
            document
    );
}
}