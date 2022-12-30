package com.mcode.transferservice.models.requests;

import com.mcode.transferservice.enums.TransferProvider;
import com.mcode.transferservice.flutterwave.models.requests.FlutterwaveTransferRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransfersRequest {
    private TransferProvider provider;
    private String accountBank;
    private String accountNumber;
    private Double amount;
    private String narration;
    private String currency;
    private String reference;
    private String callbackUrl;
    private String debitCurrency;

    private String source;
    private String recipient;
    private String reason;

    private String type;
    private String name;
    private String bankCode;


}
