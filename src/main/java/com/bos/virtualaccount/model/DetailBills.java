package com.bos.virtualaccount.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailBills {
    private List<BillDescription> BillDescription;
    private String BillAmount;
    private String BillNumber;
    private String BillSubCompany;
}
