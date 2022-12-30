package com.mcode.transferservice.paystack.models.requests;

import lombok.Getter;

@Getter
public class PaystackTransferRequest {
    private String source;
    private String amount;
    private String reference;
    private String recipient;
    private String reason;
}
