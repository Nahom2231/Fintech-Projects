package com.coopbank.onboarding.ledger.repository;

import com.coopbank.onboarding.ledger.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository
        extends JpaRepository<LedgerEntry, Long> {

    List<LedgerEntry>
    findByAccountId(Long accountId);

}