package com.mcode.transferservice.paystack.models.requests;

import lombok.Getter;

@Getter
public class TransferRecipientRequest {
    private String type;
    private String name;
    private String account_number;
    private String bank_code;
    private String currency;
}
