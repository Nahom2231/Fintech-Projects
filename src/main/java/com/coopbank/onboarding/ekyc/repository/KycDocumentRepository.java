package com.coopbank.onboarding.ekyc.repository;

import com.coopbank.onboarding.ekyc.entity.KycDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KycDocumentRepository
        extends JpaRepository<KycDocument, Long> {

}