package com.mcode.transferservice.flutterwave.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcode.transferservice.contracts.TransferService;
import com.mcode.transferservice.enums.TransactionStatus;
import com.mcode.transferservice.enums.TransferProvider;
import com.mcode.transferservice.exceptions.ApplicationException;
import com.mcode.transferservice.exceptions.NotFoundException;
import com.mcode.transferservice.flutterwave.models.responses.FlutterwaveBanksResponse;
import com.mcode.transferservice.flutterwave.models.responses.FlutterwaveTransferResponse;
import com.mcode.transferservice.models.entities.Transaction;
import com.mcode.transferservice.models.entities.TransactionRepository;
import com.mcode.transferservice.models.requests.AccountValidationRequest;
import com.mcode.transferservice.models.requests.TransfersRequest;
import com.mcode.transferservice.models.responses.AccountValidationResponse;
import com.mcode.transferservice.models.responses.BanksResponse;
import com.mcode.transferservice.models.responses.TransferResponses;
import com.mcode.transferservice.services.TransactionService;
import com.mcode.transferservice.utils.HttpUtils;
import com.mcode.transferservice.webservice.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FlutterwaveService extends TransferService {

    public static final String token = "Bearer FLWSECK_TEST-3a2a94712ed5440f98760873ca63764e-X";
    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();



    private TransfersRequest transfersRequest;

    private static final String BASE_URL = "https://api.flutterwave.com/v3";

    @Autowired
    private WebService<FlutterwaveTransferResponse> webServices;

    @Autowired
    private WebService<FlutterwaveBanksResponse> webService;

    @Autowired
    private TransactionService transactionService;


    @Override
    public List<BanksResponse> banks(TransferProvider provider) {
        List<BanksResponse> responses = new ArrayList<>();
        //RestTemplate restTemplate = new RestTemplate();
        String endpoint = "/banks/NG";
        String urlEndpoint = BASE_URL + endpoint;
        ResponseEntity<FlutterwaveBanksResponse> responseEntity = webService.executeGetRequest(urlEndpoint, token, FlutterwaveBanksResponse.class);
        System.out.println(responseEntity.getBody());
        FlutterwaveBanksResponse banks = responseEntity.getBody();
        List<FlutterwaveBanksResponse.Data> data = banks.getData();
        for (FlutterwaveBanksResponse.Data d : data) {
            BanksResponse response = new BanksResponse(String.valueOf(d.getId()), d.getName(), d.getCode());
            responses.add(response);
        }

        return responses;
    }

    @Override
    public AccountValidationResponse validateBankAccountNumber(AccountValidationRequest accountValidationRequest) {
        if (!hasAccountNumberValidation())
            throw new ApplicationException(String.format("Provider %s does not support account lookup/validation", slug()));

        return new AccountValidationResponse();
    }


    @Override
    public TransferResponses getTransactionStatus(TransferProvider provider, String reference) {
        Transaction transaction = transactionService.getTransactionByReference(reference);
        String endpoint = "/transfers/" + transaction.getExternalReference();
        String urlEndpoint = BASE_URL + endpoint;
        System.out.println(urlEndpoint);

        ResponseEntity<FlutterwaveTransferResponse> res = webServices.executeGetRequest(urlEndpoint, token, FlutterwaveTransferResponse.class);
        if(res.getStatusCode().is2xxSuccessful())
            throw new ApplicationException("An error occurred while fetching the transaction status");
        FlutterwaveTransferResponse response = res.getBody();
        FlutterwaveTransferResponse.Data data = res.getBody().getData();
        TransferResponses transferResponses = getTransferResponses(res, data);
        transactionService.updateTransactionStatus(transferResponses, reference);
        return transferResponses;
    }



    @Override
    public void initTransfer(TransfersRequest request) {
        //move everything here to a separate method called  transfer
        //the implementation for this method will be logging/creating transaction record in the db
        //implement a crown job class that will fetch all pending transaction in the db and post it to the transfer method
        transactionService.initTransaction(request);

    }

    @Override
    public TransferResponses transfer(TransfersRequest request) {
        Transaction transaction = transactionService.getTransactionByReference(request.getReference());
        String reference = transaction.getReference();
        String endpoint = "/transfers";
        String urlEndpoint = BASE_URL + endpoint;
        Map<String, Object> payloadMap = payload(request, reference);
        String payload = "";
        try {
            payload = objectMapper.writeValueAsString(payloadMap);
        } catch (Exception e) {
            System.out.println(e);
        }
        ResponseEntity<FlutterwaveTransferResponse> res = webServices.executePostRequest(urlEndpoint, token, payload, FlutterwaveTransferResponse.class);
        FlutterwaveTransferResponse.Data data = res.getBody().getData();
        TransferResponses transferResponses = getTransferResponses(res, data);
        transaction.setStatus(transferResponses.getStatus());
        transaction.setExternalReference(transferResponses.getExternalReference());
        transactionService.updateTransaction(transaction);
        return transferResponses;

    }

    @Override
    public boolean canApply(TransferProvider transferProvider) {
        return TransferProvider.FLUTERWAVE.equals(transferProvider);
    }

    @Override
    public boolean hasAccountNumberValidation() {
        return false;
    }

    @Override
    public boolean hasTransfer() {
        return true;
    }

    @Override
    public boolean hasBanks() {
        return true;
    }

    @Override
    public boolean hasTransactionStatus() {
        return true;
    }

    @Override
    public String slug() {
        return TransferProvider.FLUTERWAVE.name();
    }



    public TransferResponses getTransferResponses(ResponseEntity<FlutterwaveTransferResponse> response, FlutterwaveTransferResponse.Data data) {
        TransferResponses responses = new TransferResponses();
        responses.setAmount(String.valueOf(data.getAmount()));
        responses.setBeneficiaryAccountName(data.getFull_name());
        responses.setBeneficiaryAccountNumber(data.getAccount_number());
        responses.setBeneficiaryBankCode(data.getBank_code());
        responses.setCurrencyCode(data.getCurrency());
        responses.setResponseCode(data.getApprover());
        responses.setExternalReference(String.valueOf(data.getId()));
        responses.setResponseMessage(data.getComplete_message());
        responses.setStatus(status(data.getStatus()));
        return responses;
    }

    private TransactionStatus status(String status){
        if(status.equals("SUCCESSFUL")){
            return TransactionStatus.SUCCESS;
        }else if(status.equals("Failed")){
            return TransactionStatus.FAILED;
        }else{
            return TransactionStatus.PENDING;
        }
    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("content-type", "application/json");
        headers.set("Authorization", token);
        return headers;
    }

    public Map<String, Object> payload(TransfersRequest request, String reference) {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("account_bank", request.getAccountBank());
        payloadMap.put("account_number", request.getAccountNumber());
        payloadMap.put("amount", request.getAmount());
        payloadMap.put("narration", request.getNarration());
        payloadMap.put("currency", request.getCurrency());
        payloadMap.put("reference", reference);
        payloadMap.put("callback_url", request.getCallbackUrl());
        payloadMap.put("debit_currency", request.getDebitCurrency());
        return payloadMap;
    }
}
