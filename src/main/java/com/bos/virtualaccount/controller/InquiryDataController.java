package com.bos.virtualaccount.controller;

import com.bos.virtualaccount.model.InquiryBills.InquiryDataRequest;
import com.bos.virtualaccount.model.InquiryBills.InquiryDataResponse;
import com.bos.virtualaccount.service.InquiryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bos", produces = "application/json")
public class InquiryDataController {
    @Autowired
    InquiryDataService g_inquiryDataService;

    @PostMapping(value = "/bill", consumes = "application/json")
    public InquiryDataResponse getBill(@RequestBody InquiryDataRequest p_inquiryDataRequest){
        return g_inquiryDataService.getBill(p_inquiryDataRequest);
    }
}
