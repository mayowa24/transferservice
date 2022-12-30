package com.mcode.transferservice.models.requests;

import com.mcode.transferservice.enums.TransferProvider;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountValidationRequest {
    private String accountNumber;
    private String bankCode;
    private TransferProvider transferProvider;
}
