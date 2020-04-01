package com.bos.virtualaccount.service;

import com.bos.virtualaccount.dim.Transaction;
import com.bos.virtualaccount.model.PaymentConf.PaymentConfRequest;
import com.bos.virtualaccount.model.PaymentConf.PaymentConfResponse;
import com.bos.virtualaccount.model.PaymentConf.PaymentReason;
import com.bos.virtualaccount.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
        String vaNum = request.getCustomerNumber();
        System.out.println("VaNum   : " +vaNum);
        Integer status = repository.getStatusByVANum(vaNum);
        if(status == null) status = 999;
        System.out.println("Status  : "+status);

        List<Transaction> data = repository.getTransactionByVANumAndReqId(vaNum,reqId);

        if(!(data.isEmpty()) && (status == 0)){
            System.out.println("Data tersedia");
            try{
                repository.updateTransaction(new Timestamp(System.currentTimeMillis()), vaNum,reqId);
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
            Integer vVaNum1 = repository.getVANum1(vaNum);
            if(vVaNum1 == null) vVaNum1 = 999;
            System.out.println("vVaNum1 (va_number)            : "+vVaNum1);
            String vVaNum2 = repository.getVANum2(vaNum);
            if(vVaNum2 == null) vVaNum2 = "null";
            System.out.println("vVaNum2 (reqId by va_number)   : "+vVaNum2);

            String vReqId = repository.getReqId(reqId);
            if(vReqId == null) vReqId = "null";
            System.out.println("vReqId (request_id)             : "+vReqId);

            System.out.println("Hasil: ");
            if(vVaNum1 == 999 || vReqId.equals("null"))
                System.out.print("VA Number atau Request Id tidak ditemukan/salah");
            else if(vVaNum2.equals("null") || !vVaNum2.equals(reqId))
                System.out.print("Request Id tidak ditemukan atau input VA Number salah");
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
