package com.mcode.transferservice.models.entities;

import com.mcode.transferservice.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByStatus(TransactionStatus status);
    Optional<Transaction> findTransactionByReference(String reference);
}
