package com.mcode.transferservice.contracts;

import com.mcode.transferservice.enums.TransferProvider;
import com.mcode.transferservice.models.requests.TransfersRequest;
import com.mcode.transferservice.models.requests.AccountValidationRequest;
import com.mcode.transferservice.models.responses.BanksResponse;
import com.mcode.transferservice.models.responses.TransferResponses;
import com.mcode.transferservice.models.responses.AccountValidationResponse;

import java.util.List;

public abstract class TransferService {

    public abstract List<BanksResponse> banks(TransferProvider provider);

    public abstract AccountValidationResponse validateBankAccountNumber(AccountValidationRequest accountValidationRequest);

    public abstract TransferResponses getTransactionStatus(TransferProvider provider, String transferReference);

    public abstract void initTransfer(TransfersRequest request);

    public abstract TransferResponses transfer(TransfersRequest request);

    public abstract boolean canApply(TransferProvider transferProvider);

    public abstract boolean hasAccountNumberValidation();

    public abstract boolean hasTransfer();

    public abstract boolean hasBanks();

    public abstract boolean hasTransactionStatus();

    public abstract String slug();
}
