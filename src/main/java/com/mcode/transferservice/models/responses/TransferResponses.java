package com.mcode.transferservice.models.responses;

import com.mcode.transferservice.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponses {
    private String amount;
    private String beneficiaryAccountNumber;
    private String beneficiaryAccountName;
    private String beneficiaryBankCode;
    private String transactionReference;
    private String transactionDateTime;
    private String externalReference;
    private String currencyCode;
    private String responseMessage;
    private String responseCode;
    private String sessionId;
    private TransactionStatus status;
}
