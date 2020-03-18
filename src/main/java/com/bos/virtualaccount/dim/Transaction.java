package com.bos.virtualaccount.dim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_transaction;
    private String total_payment;
    private int status;
    /*
    status:
    0 --> pesanan baru
    1 --> sudah terbayar
    2 --> sudah dikirim
    3 --> selesai
     */
}
