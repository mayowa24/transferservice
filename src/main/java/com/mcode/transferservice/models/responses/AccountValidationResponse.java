package com.mcode.transferservice.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountValidationResponse {
    private String accountNumber;
    private String accountName;
    private String bankCode;
    private String bankName;
}
