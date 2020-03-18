package com.bos.virtualaccount.service;

import com.bos.virtualaccount.dim.Transaction;
import com.bos.virtualaccount.model.PaymentConf.PaymentConfRequest;
import com.bos.virtualaccount.model.PaymentConf.PaymentConfResponse;
import com.bos.virtualaccount.model.PaymentConf.PaymentReason;
import com.bos.virtualaccount.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentConfService {
    @Autowired
    TransactionRepository repository;

    public PaymentConfResponse jawaban(PaymentConfRequest request, String status, PaymentReason reason){
        PaymentConfResponse response = new PaymentConfResponse();

        response.setCompanyCode(request.getCompanyCode());
        response.setCustomerNumber(request.getCustomerNumber());
        response.setRequestID(request.getRequestID());
        response.setPaymentFlagStatus(status);
        response.setPaymentFlagReason(reason);
        response.setCustomerName(request.getCustomerName());
        response.setCurrencyCode(request.getCurrencyCode());
        response.setPaidAmount(request.getPaidAmount());
        response.setTotalAmount(request.getTotalAmount());
        response.setTransactionDate(request.getTransactionDate());

        return response;
    }

    public PaymentConfResponse upStatus(PaymentConfRequest request){
        PaymentConfResponse response;
        PaymentReason reason = new PaymentReason();

        System.out.println("\n======================Inputs======================");
        String reqId = request.getRequestID();
        System.out.println("ReqId   : "+reqId);
        Integer trxId = Integer.valueOf(request.getCustomerNumber());
        System.out.println("TrxId   : " +trxId);
        Integer status = repository.getStatusByTransactionId(trxId);
        if(status == null) status = 999;
        System.out.println("Status  : "+status);

        List<Transaction> data = repository.getTransactionByTrxIdAndReqId(trxId,reqId);

        if(!(data.isEmpty()) && (status == 0)){
            System.out.println("Data tersedia");
            try{
                repository.updateStatus(trxId,reqId);
                System.out.println("Update Success");

                reason.setEnglish("Success");
                reason.setIndonesian("Sukses");

                response = jawaban(request,"00",reason);
            }
            catch (Exception ex){
                ex.printStackTrace();

                reason.setEnglish("Status update failed");
                reason.setIndonesian("Pembaharuan status gagal");

                response = jawaban(request,"01",reason);
            }
        }
        else
        {
            System.out.println("\n====================Verifikasi====================");
            Integer vTrxId1 = repository.getTrxId1(trxId);
            if(vTrxId1 == null) vTrxId1 = 999;
            System.out.println("vTrxId1 (transaction_id)            : "+vTrxId1);
            String vTrxId2 = repository.getTrxId2(trxId);
            if(vTrxId2 == null) vTrxId2 = "null";
            System.out.println("vTrxId2 (reqId by transaction_id)   : "+vTrxId2);

            String vReqId = repository.getReqId(reqId);
            if(vReqId == null) vReqId = "null";
            System.out.println("vReqId (request_id)                 : "+vReqId);

            System.out.println("Hasil: ");
            if(vTrxId1 == 999 || vReqId.equals("null"))
                System.out.print("Transaction Id atau Request Id tidak ditemukan/salah");
            else if(vTrxId2.equals("null") || !vTrxId2.equals(reqId))
                System.out.print("Request Id tidak ditemukan atau input Transaction Id salah");
            else if (status != 0 && status != 999) {
                System.out.print("Status transaksi bukan 0");
            }
            else System.out.print("DATA ERROR");

            reason.setEnglish("Payment transaction not found");
            reason.setIndonesian("Transaksi pembayaran tidak ditemukan");

            response = jawaban(request,"01",reason);

        }

        return response;
    }
}
