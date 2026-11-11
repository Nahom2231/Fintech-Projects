package com.coopbank.onboarding.ekyc.entity;

import com.coopbank.onboarding.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentNumber;

    private String documentType;

    private String fileName;

    @Enumerated(EnumType.STRING)
    private KycStatus status;

    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}