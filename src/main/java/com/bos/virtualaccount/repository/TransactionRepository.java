package com.bos.virtualaccount.repository;

import com.bos.virtualaccount.dim.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT * FROM transaction WHERE id_transaction = :id_transaction", nativeQuery = true)
    Optional<Transaction> getTransactionByTransactionId(@Param("id_transaction") int id_transaction);

    @Query(value = "SELECT status FROM transaction WHERE id_transaction = :id_transaction", nativeQuery = true)
    Integer getStatusByTransactionId(@Param("id_transaction") Integer id_transaction);

    @Transactional
    @Modifying
    @Query(value = "UPDATE transaction SET request_id = :request_id WHERE id_transaction = :id_transaction", nativeQuery = true)
    void updateRequestId(@Param("request_id") String request_id, @Param("id_transaction") int id_transaction);

    @Query(nativeQuery = true,
            value = "SELECT id_transaction " +
                    "FROM transaction " +
                    "WHERE id_transaction = :idTrx")
    Integer getTrxId1(@Param("idTrx") Integer idTrx); // dapat id_trx berdasarkan id_trx

    @Query(nativeQuery = true,
            value = "SELECT request_id " +
                    "FROM transaction " +
                    "WHERE id_transaction = :idTrx")
    String getTrxId2(@Param("idTrx") Integer idTrx); // dapat request_id berdasarkan id_transaksi

    @Query(nativeQuery = true,
            value = "SELECT request_id " +
                    "FROM transaction " +
                    "WHERE request_id = :reqId")
    String getReqId(@Param("reqId") String reqId);

    @Query(nativeQuery = true,
           value = "SELECT * " +
                   "FROM transaction " +
                   "WHERE id_transaction = :idTrx AND request_id = :reqId")
    List<Transaction> getTransactionByTrxIdAndReqId(@Param("idTrx") Integer idTrx, @Param("reqId") String reqId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
           value = "UPDATE transaction " +
                   "SET status = 1 " +
                   "WHERE id_transaction = :idTrx AND request_id = :reqId ")
    void updateStatus(@Param("idTrx") Integer idTrx, @Param("reqId") String reqId);
}
