package com.mcode.transferservice.flutterwave.models.requests;

import lombok.Getter;

@Getter
public class FlutterwaveTransferRequest {
    private String accountBank;
    private String accountNumber;
    private Double amount;
    private String narration;
    private String currency;
    private String reference;
    private String callbackUrl;
    private String debitCurrency;

}
