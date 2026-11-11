package com.coopbank.onboarding.customer.service;

import com.coopbank.onboarding.customer.dto.RegisterCustomerRequest;
import com.coopbank.onboarding.customer.entity.Customer;
import com.coopbank.onboarding.customer.entity.CustomerStatus;
import com.coopbank.onboarding.customer.repository.CustomerRepository;
import com.coopbank.onboarding.exception.VerificationException;
import com.coopbank.onboarding.verification.entity.VerificationStatus;
import com.coopbank.onboarding.verification.repository.VerificationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final VerificationRequestRepository
            verificationRepository;

    public Customer registerCustomer(
            RegisterCustomerRequest request
    ) {

        verificationRepository
                .findFirstByPhoneNumberAndStatusOrderByCreatedAtDesc(
                        request.phoneNumber(),
                        VerificationStatus.VERIFIED
                )
                .orElseThrow(
                        () -> new VerificationException(
                                "Phone number is not verified"
                        )
                );

        customerRepository
                .findByPhoneNumber(
                        request.phoneNumber()
                )
                .ifPresent(
                        customer -> {

                            throw new VerificationException(
                                    "Customer already exists"
                            );

                        }
                );

        Customer customer =
                Customer.builder()
                        .fullName(
                                request.fullName()
                        )
                        .phoneNumber(
                                request.phoneNumber()
                        )
                        .email(
                                request.email()
                        )
                        .nationalId(
                                request.nationalId()
                        )
                        .status(
                         CustomerStatus.PENDING_KYC
                )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        return customerRepository.save(
                customer
        );
    }
}