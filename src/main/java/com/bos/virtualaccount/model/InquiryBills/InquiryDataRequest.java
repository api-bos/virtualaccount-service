package com.bos.virtualaccount.model.InquiryBills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDataRequest {
    private String CompanyCode;
    private String CustomerNumber;
    private String RequestID;
    private String ChannelType;
    private String TransactionDate;
    private String AdditionalData;
}
