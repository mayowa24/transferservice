package com.mcode.transferservice.services;

import com.mcode.transferservice.models.entities.Transaction;
import com.mcode.transferservice.models.requests.TransfersRequest;
import com.mcode.transferservice.models.responses.TransferResponses;

public interface TransactionService {
    void initTransaction(TransfersRequest request);
    void updateTransactionStatus(TransferResponses responses, String reference);
    Transaction getTransactionByReference(String reference);
    void updateTransaction(Transaction transaction);
}
