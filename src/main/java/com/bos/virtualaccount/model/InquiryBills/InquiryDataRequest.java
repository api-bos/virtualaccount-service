package com.bos.virtualaccount.model.InquiryBills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class InquiryDataRequest {
    private String CompanyCode;
    private String CustomerNumber;
    private String RequestID;
    private String ChannelType;
    private String TransactionDate;
    private String AdditionalData;
}
