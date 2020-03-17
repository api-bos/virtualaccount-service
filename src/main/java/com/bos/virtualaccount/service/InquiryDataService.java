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
        InquiryDataResponse l_inquiryDataResponse = new InquiryDataResponse();
        InquiryReason l_inquiryReason = new InquiryReason();

        try {
            //Check bill to transaction table
            if (g_transactionRepository.getTransactionByTransactionId(Integer.valueOf(p_inquiryDataRequest.getCustomerNumber())).equals(Optional.empty())){
                l_inquiryReason.setIndonesian("Tagihan tidak ditemukan");
                l_inquiryReason.setEnglish("Bill not found");

                l_inquiryDataResponse.setCompanyCode(p_inquiryDataRequest.getCompanyCode());
                l_inquiryDataResponse.setCustomerNumber(p_inquiryDataRequest.getCustomerNumber());
                l_inquiryDataResponse.setRequestID(p_inquiryDataRequest.getRequestID());
                l_inquiryDataResponse.setInquiryStatus("01");
                l_inquiryDataResponse.setInquiryReason(l_inquiryReason);

                return l_inquiryDataResponse;

            //Check status to transaction table
            }else if (g_transactionRepository.getStatusByTransactionId(Integer.valueOf(p_inquiryDataRequest.getCustomerNumber()))!=0){
                l_inquiryReason.setIndonesian("Tagihan tidak ditemukan");
                l_inquiryReason.setEnglish("Bill not found");

                l_inquiryDataResponse.setCompanyCode(p_inquiryDataRequest.getCompanyCode());
                l_inquiryDataResponse.setCustomerNumber(p_inquiryDataRequest.getCustomerNumber());
                l_inquiryDataResponse.setRequestID(p_inquiryDataRequest.getRequestID());
                l_inquiryDataResponse.setInquiryStatus("01");
                l_inquiryDataResponse.setInquiryReason(l_inquiryReason);

                return l_inquiryDataResponse;

            //If bill exist and status=0 (belum terbayar)
            }else {
                //update id_request to transaction table
                g_transactionRepository.updateRequestId(p_inquiryDataRequest.getRequestID(), Integer.valueOf(p_inquiryDataRequest.getCustomerNumber()));

                l_inquiryReason.setIndonesian("Sukses");
                l_inquiryReason.setEnglish("Success");

                Optional<Transaction> tmp_transaction = g_transactionRepository.getTransactionByTransactionId(Integer.valueOf(p_inquiryDataRequest.getCustomerNumber()));
                l_inquiryDataResponse.setCompanyCode(p_inquiryDataRequest.getCompanyCode());
                l_inquiryDataResponse.setCustomerNumber(p_inquiryDataRequest.getCustomerNumber());
                l_inquiryDataResponse.setRequestID(p_inquiryDataRequest.getRequestID());
                l_inquiryDataResponse.setTotalAmount(tmp_transaction.get().getTotal_payment());
                l_inquiryDataResponse.setInquiryStatus("00");
                l_inquiryDataResponse.setInquiryReason(l_inquiryReason);
                l_inquiryDataResponse.setCurrencyCode("IDR");

                return l_inquiryDataResponse;
            }

        }catch (Exception e){
            e.printStackTrace();
            l_inquiryReason.setIndonesian("Gagal inquiry ke database");
            l_inquiryReason.setEnglish("Failed inquiry to database");

            l_inquiryDataResponse.setCompanyCode(p_inquiryDataRequest.getCompanyCode());
            l_inquiryDataResponse.setCustomerNumber(p_inquiryDataRequest.getCustomerNumber());
            l_inquiryDataResponse.setRequestID(p_inquiryDataRequest.getRequestID());
            l_inquiryDataResponse.setInquiryStatus("01");
            l_inquiryDataResponse.setInquiryReason(l_inquiryReason);

            return l_inquiryDataResponse;
        }
    }
}
