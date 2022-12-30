package com.mcode.transferservice.controllers;

import com.mcode.transferservice.contracts.TransferService;
import com.mcode.transferservice.enums.TransferProvider;
import com.mcode.transferservice.models.requests.AccountValidationRequest;
import com.mcode.transferservice.models.requests.TransfersRequest;
import com.mcode.transferservice.models.responses.AccountValidationResponse;
import com.mcode.transferservice.models.responses.BanksResponse;
import com.mcode.transferservice.models.responses.GenericResponses;
import com.mcode.transferservice.models.responses.TransferResponses;
import com.mcode.transferservice.paystack.services.PaystackServices;
import com.mcode.transferservice.services.TransferServiceResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BankTransferController {

    private final TransferServiceResolver transferServiceResolver;

    private PaystackServices paystackServices;

    @GetMapping("/api/v1/core-banking/banks")
    public GenericResponses banks(@RequestParam  TransferProvider provider ){
        TransferProvider transferProvider = provider == null ? TransferProvider.FLUTERWAVE : provider;
        System.out.println(provider);
        final TransferService transferService = transferServiceResolver.resolveTransferProvider(transferProvider);


        final List<BanksResponse> banks = transferService.banks(provider);

        return GenericResponses.builder().
                isRequestSuccessful(true).
                message("success").
                data(banks)
                .build();
    }

    @PostMapping("/api/v1/core-banking/bankTransfer")
    public GenericResponses transfer(@RequestBody TransfersRequest request){
        TransferProvider transferProvider = request.getProvider() == null ? TransferProvider.FLUTERWAVE : request.getProvider();

        final TransferService transferService = transferServiceResolver.resolveTransferProvider(transferProvider);
        transferService.initTransfer(request);

        return GenericResponses.builder()
                .isRequestSuccessful(true)
                .message("transfer initialized successfully")
                .data(null)
                .build();
    }

    @GetMapping("/api/v1/core-banking/transaction/{reference}")
    public GenericResponses getStatus(@RequestParam TransferProvider provider, @PathVariable String reference){
        TransferProvider transferProvider = provider == null ? TransferProvider.FLUTERWAVE : provider;
        final TransferService transferService = transferServiceResolver.resolveTransferProvider(transferProvider);
        final TransferResponses transaction = transferService.getTransactionStatus(provider, reference);

        return GenericResponses.builder()
                .isRequestSuccessful(true)
                .message("status fetched successfully")
                .data(transaction)
                .build();
    }

    @GetMapping("/api/v1/core-banking/validateBankAccount")
    public GenericResponses validateAccount(@RequestParam TransferProvider provider, @RequestParam String bankAccount, @RequestParam String bankCode){
        AccountValidationResponse data = paystackServices.verifyAccount(provider, bankAccount, bankCode);
        return GenericResponses.builder()
                .isRequestSuccessful(true)
                .message("validated Successfully")
                .data(data)
                .build();
    }
}
