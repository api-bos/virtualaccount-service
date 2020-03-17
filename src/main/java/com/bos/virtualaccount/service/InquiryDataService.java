package com.bos.virtualaccount.service;

import com.bos.virtualaccount.dim.Transaction;
import com.bos.virtualaccount.model.InquiryDataRequest;
import com.bos.virtualaccount.model.InquiryDataResponse;
import com.bos.virtualaccount.model.InquiryReason;
import com.bos.virtualaccount.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InquiryDataService {
    @Autowired
    TransactionRepository g_transactionRepository;

    public InquiryDataResponse getBill(InquiryDataRequest p_inquiryDataRequest){
        try {
            System.out.println(g_transactionRepository.getTransactionByTransactionId(Integer.valueOf(p_inquiryDataRequest.getCustomerNumber())));
            if (g_transactionRepository.getTransactionByTransactionId(Integer.valueOf(p_inquiryDataRequest.getCustomerNumber())).equals(Optional.empty())){
                InquiryDataResponse tmp_inquiryDataResponse = new InquiryDataResponse();
                tmp_inquiryDataResponse.setCompanyCode(p_inquiryDataRequest.getCompanyCode());
                tmp_inquiryDataResponse.setRequestID(p_inquiryDataRequest.getRequestID());
                tmp_inquiryDataResponse.setInquiryStatus("01");

                return tmp_inquiryDataResponse;

            }else if (g_transactionRepository.getStatusByTransactionId(Integer.valueOf(p_inquiryDataRequest.getCustomerNumber()))!=1){
                InquiryDataResponse tmp_inquiryDataResponse = new InquiryDataResponse();
                tmp_inquiryDataResponse.setCompanyCode(p_inquiryDataRequest.getCompanyCode());
                tmp_inquiryDataResponse.setRequestID(p_inquiryDataRequest.getRequestID());
                tmp_inquiryDataResponse.setInquiryStatus("01");

                return tmp_inquiryDataResponse;

            }else {
                InquiryReason tmp_inquiryReason = new InquiryReason();
                tmp_inquiryReason.setIndonesian("Sukses");
                tmp_inquiryReason.setEnglish("Success");

                Optional<Transaction> tmp_transaction = g_transactionRepository.getTransactionByTransactionId(Integer.valueOf(p_inquiryDataRequest.getCustomerNumber()));
                InquiryDataResponse tmp_inquiryDataResponse = new InquiryDataResponse();
                tmp_inquiryDataResponse.setCompanyCode(p_inquiryDataRequest.getCompanyCode());
                tmp_inquiryDataResponse.setCustomerNumber(p_inquiryDataRequest.getCustomerNumber());
                tmp_inquiryDataResponse.setRequestID(p_inquiryDataRequest.getRequestID());
                tmp_inquiryDataResponse.setTotalAmount(tmp_transaction.get().getTotal_payment());
                tmp_inquiryDataResponse.setInquiryStatus("00");
                tmp_inquiryDataResponse.setInquiryReason(tmp_inquiryReason);
                tmp_inquiryDataResponse.setCurrencyCode("IDR");

                return tmp_inquiryDataResponse;
            }

        }catch (Exception e){
            e.printStackTrace();
            InquiryDataResponse tmp_inquiryDataResponse = new InquiryDataResponse();
            tmp_inquiryDataResponse.setCompanyCode(p_inquiryDataRequest.getCompanyCode());
            tmp_inquiryDataResponse.setRequestID(p_inquiryDataRequest.getRequestID());
            tmp_inquiryDataResponse.setInquiryStatus("01");

            return tmp_inquiryDataResponse;
        }
    }
}
