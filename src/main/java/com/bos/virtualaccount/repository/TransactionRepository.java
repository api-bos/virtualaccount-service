package com.bos.virtualaccount.repository;

import com.bos.virtualaccount.dim.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT * FROM transaction WHERE id_transaction = :id_transaction", nativeQuery = true)
    Optional<Transaction> getTransactionByTransactionId(@Param("id_transaction") int id_transaction);

    @Query(value = "SELECT status FROM transaction WHERE id_transaction = :id_transaction", nativeQuery = true)
    int getStatusByTransactionId(@Param("id_transaction") int id_transaction);
}
