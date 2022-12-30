package com.mcode.transferservice.services.impl;

import com.mcode.transferservice.enums.TransactionStatus;
import com.mcode.transferservice.enums.TransferProvider;
import com.mcode.transferservice.exceptions.NotFoundException;
import com.mcode.transferservice.models.entities.Transaction;
import com.mcode.transferservice.models.entities.TransactionRepository;
import com.mcode.transferservice.models.requests.TransfersRequest;
import com.mcode.transferservice.models.responses.TransferResponses;
import com.mcode.transferservice.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public void initTransaction(TransfersRequest request) {
        Transaction transaction = new Transaction();
        transaction.setReference(UUID.randomUUID().toString());
        transaction.setTransferProvider(TransferProvider.FLUTERWAVE);
        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setBankCode(request.getBankCode());
        transaction.setStatus(TransactionStatus.PENDING);
        transactionRepository.save(transaction);

    }

    @Override
    public void updateTransactionStatus(TransferResponses response, String reference) {
        Transaction transaction = getTransactionByReference(reference);
        transaction.setStatus(response.getStatus());
        transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionByReference(String reference) {
        return transactionRepository.findTransactionByReference(reference).orElseThrow(() -> new NotFoundException(String.format("Unable to found transaction with %s", reference)));
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
