package com.bos.virtualaccount.controller;

import com.bos.virtualaccount.model.InquiryBills.InquiryDataRequest;
import com.bos.virtualaccount.model.InquiryBills.InquiryDataResponse;
import com.bos.virtualaccount.model.PaymentConf.PaymentConfRequest;
import com.bos.virtualaccount.model.PaymentConf.PaymentConfResponse;
import com.bos.virtualaccount.model.PaymentConf.PaymentReason;
import com.bos.virtualaccount.service.InquiryDataService;
import com.bos.virtualaccount.service.PaymentConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bos", produces = "application/json")
@CrossOrigin(origins = {"*"})
public class InquiryDataController {
    @Autowired
    InquiryDataService g_inquiryDataService;

    @Autowired
    PaymentConfService paymentConfService;

    @PostMapping(value = "/bill", consumes = "application/json")
    public InquiryDataResponse getBill(@RequestBody InquiryDataRequest p_inquiryDataRequest){
        return g_inquiryDataService.getBill(p_inquiryDataRequest);
    }

    @PostMapping(value = "/payment", consumes = "application/json")
    public PaymentConfResponse upStatus(@RequestBody PaymentConfRequest request){
        try {
            System.out.println("Try up status service");
            return paymentConfService.upStatus(request);
        }
        catch (Exception e){
            e.printStackTrace();

            PaymentReason reason = new PaymentReason();

            reason.setEnglish("System under maintenance-0");
            reason.setIndonesian("Sistem dalam perbaikan-0");

            return paymentConfService.jawaban(request,"01",reason);
        }
    }
}
