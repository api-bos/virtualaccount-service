package com.bos.virtualaccount.model.InquiryBills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.bos.virtualaccount.model.General.DetailBills;
import com.bos.virtualaccount.model.General.FreeText;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDataResponse {
    private String CompanyCode;
    private String CustomerNumber;
    private String RequestID;
    private String InquiryStatus;
    private InquiryReason InquiryReason;
    private String CustomerName;
    private String CurrencyCode;
    private String TotalAmount;
    private String SubCompany;
    private List<DetailBills> DetailBills;
    private List<FreeText> FreeText;
    private String AdditionalData;
}
