package com.coopbank.onboarding.transaction.repository;

import com.coopbank.onboarding.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;  

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {
       List<Transaction>
       findBySourceAccountNumber(
        String accountNumber
       );

      List<Transaction>
      findByDestinationAccountNumber(
        String accountNumber
        );
     
        List<Transaction>
      findTop5BySourceAccountNumberOrderByCreatedAtDesc(
        String accountNumber
);

}