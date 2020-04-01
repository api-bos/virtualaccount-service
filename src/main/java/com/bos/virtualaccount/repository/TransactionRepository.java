package com.bos.virtualaccount.repository;

import com.bos.virtualaccount.dim.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT * FROM transaction WHERE va_number = :va_number", nativeQuery = true)
    Optional<Transaction> getTransactionByVANumber(@Param("va_number") String va_number);

    @Query(value = "SELECT status FROM transaction WHERE id_transaction = :id_transaction", nativeQuery = true)
    Integer getStatusByTransactionId(@Param("id_transaction") Integer id_transaction);

    @Query(value = "SELECT status FROM transaction WHERE va_number = :va_number", nativeQuery = true)
    Integer getStatusByVANum(@Param("va_number") String va_number);

    @Transactional
    @Modifying
    @Query(value = "UPDATE transaction SET request_id = :request_id WHERE va_number = :va_number", nativeQuery = true)
    void updateRequestId(@Param("request_id") String request_id, @Param("va_number") String va_number);

    @Query(nativeQuery = true,
            value = "SELECT id_transaction " +
                    "FROM transaction " +
                    "WHERE va_number = :vaNum")
    Integer getVANum1(@Param("vaNum") String vaNum); // dapat id_trx berdasarkan id_trx

    @Query(nativeQuery = true,
            value = "SELECT request_id " +
                    "FROM transaction " +
                    "WHERE va_number = :vaNum")
    String getVANum2(@Param("vaNum") String vaNum); // dapat request_id berdasarkan id_transaksi

    @Query(nativeQuery = true,
            value = "SELECT request_id " +
                    "FROM transaction " +
                    "WHERE request_id = :reqId")
    String getReqId(@Param("reqId") String reqId);

    @Query(nativeQuery = true,
           value = "SELECT * " +
                   "FROM transaction " +
                   "WHERE va_number = :vaNum AND request_id = :reqId")
    List<Transaction> getTransactionByVANumAndReqId(@Param("vaNum") String vaNum, @Param("reqId") String reqId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
           value = "UPDATE transaction " +
                   "SET status = 1, payment_time = :paymentTime " +
                   "WHERE va_number = :vaNum AND request_id = :reqId ")
    void updateTransaction(@Param("paymentTime") Timestamp paymentTime, @Param("vaNum") String vaNum, @Param("reqId") String reqId);
}
