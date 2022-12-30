package com.mcode.transferservice.contracts;

import com.mcode.transferservice.enums.TransferProvider;
import com.mcode.transferservice.models.responses.AccountValidationResponse;


public interface VerifyBankAccount {
    public AccountValidationResponse verifyAccount(TransferProvider provider, String AccountNumber, String BankCode);
}
