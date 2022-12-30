package com.mcode.transferservice.jobs;

import com.mcode.transferservice.contracts.TransferService;
import com.mcode.transferservice.enums.TransactionStatus;
import com.mcode.transferservice.models.entities.Transaction;
import com.mcode.transferservice.models.entities.TransactionRepository;
import com.mcode.transferservice.models.requests.TransfersRequest;
import com.mcode.transferservice.services.TransferServiceResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionProcessorJob {

    private final TransactionRepository transactionRepository;

    private final TransferServiceResolver transferServiceResolver;
    @Scheduled(cron = "0 */5 * ? * *")
    public void processTransfers(){
        //Note: we can optimized this query using batch method or pageable
        transactionRepository.findAllByStatus(TransactionStatus.PENDING).parallelStream().forEach(transaction -> {
            TransferService transferService = transferServiceResolver.resolveTransferProvider(transaction.getTransferProvider());
            TransfersRequest request = new TransfersRequest();
            request.setReference(transaction.getReference());
            request.setAccountBank(transaction.getBankName());
            request.setAccountNumber(transaction.getAccountNumber());
            //request.setAmount();
            transferService.transfer(request);

        });
    }
}
