package com.bos.virtualaccount.model.PaymentConf;

import com.bos.virtualaccount.model.General.DetailBills;
import com.bos.virtualaccount.model.General.FreeText;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class PaymentConfResponse {
    private String CompanyCode;
    private String CustomerNumber;
    private String RequestID;
    private String PaymentFlagStatus;
    private PaymentReason PaymentFlagReason;
    private String CustomerName;
    private String CurrencyCode;
    private String PaidAmount;
    private String TotalAmount;
    private String TransactionDate;
    private List<DetailBills> DetailBills;  // null
    private List<FreeText> FreeText;
    private String AdditionalData;  // ""
}
