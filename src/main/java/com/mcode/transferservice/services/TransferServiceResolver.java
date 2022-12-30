package com.mcode.transferservice.services;

import com.mcode.transferservice.contracts.TransferService;
import com.mcode.transferservice.enums.TransferProvider;
import com.mcode.transferservice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferServiceResolver {

    private final List<TransferService> transferServices;

    public TransferService resolveTransferProvider(TransferProvider transferProvider) {
        return transferServices.stream()
                .filter(provider -> provider.canApply(transferProvider))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Unable to resolve transfer provider"));
    }
}
