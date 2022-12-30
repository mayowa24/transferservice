package com.mcode.transferservice.paystack.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcode.transferservice.contracts.TransferService;
import com.mcode.transferservice.contracts.VerifyBankAccount;
import com.mcode.transferservice.enums.TransferProvider;
import com.mcode.transferservice.models.requests.AccountValidationRequest;
import com.mcode.transferservice.models.requests.TransfersRequest;
import com.mcode.transferservice.models.responses.BanksResponse;
import com.mcode.transferservice.models.responses.TransferResponses;
import com.mcode.transferservice.models.responses.AccountValidationResponse;
import com.mcode.transferservice.paystack.models.requests.TransferRecipientRequest;
import com.mcode.transferservice.paystack.models.responses.PaystackBanksResponse;
import com.mcode.transferservice.paystack.models.responses.PaystackValidateAccountResponse;
import com.mcode.transferservice.webservice.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PaystackServices extends TransferService implements VerifyBankAccount {

    //mpve to an environment variable
    public static final String token = "Bearer sk_test_9484253093bc8720a868bace0acbefec013edb0d";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebService<PaystackValidateAccountResponse> webService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "https://api.paystack.co";

    @Autowired
    private WebService<PaystackBanksResponse> webServices;


    @Override
    public List<BanksResponse> banks(TransferProvider provider) {
        List<BanksResponse> banksResponses = new ArrayList<>();
        String urlEndpoint = BASE_URL +"/bank";
        ResponseEntity<PaystackBanksResponse> responseEntity = webServices.executeGetRequest(urlEndpoint, token,PaystackBanksResponse.class);
        PaystackBanksResponse responseBody = responseEntity.getBody();
        List<PaystackBanksResponse.Data>  banks = responseBody.getData();
        for(PaystackBanksResponse.Data bank:banks){
            BanksResponse response = new BanksResponse(bank.getCode(), bank.getName(), bank.getLongcode());
            banksResponses.add(response);
        }
        return banksResponses;
    }

    @Override
    public AccountValidationResponse validateBankAccountNumber(AccountValidationRequest accountValidationRequest) {
        return null;
    }

    @Override
    public TransferResponses getTransactionStatus(TransferProvider provider, String transferReference) {
        return null;
    }

    @Override
    public void initTransfer(TransfersRequest request) {


    }

    @Override
    public TransferResponses transfer(TransfersRequest request) {
        return null;
    }

    @Override
    public boolean canApply(TransferProvider transferProvider) {
        return TransferProvider.PAYSTACK.equals(transferProvider);
    }

    @Override
    public boolean hasAccountNumberValidation() {
        return true;
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
        return TransferProvider.PAYSTACK.name();
    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("content-type", "application/json");
        headers.set("Authorization", token);
        return headers;
    }

    @Override
    public AccountValidationResponse verifyAccount(TransferProvider provider, String AccountNumber, String BankCode) {
        String urlEndpoint = BASE_URL +"/bank/resolve?account_number="+AccountNumber+"&bank_code="+BankCode;
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //ResponseEntity<PaystackValidateAccountResponse> responseEntity = restTemplate.exchange(urlEndpoint, HttpMethod.GET,entity, PaystackValidateAccountResponse.class);
        ResponseEntity<PaystackValidateAccountResponse> responseEntity = webService.executeGetRequest(urlEndpoint, token, PaystackValidateAccountResponse.class);
        System.out.println(responseEntity.getBody());
        PaystackValidateAccountResponse.Data data = responseEntity.getBody().getData();
        AccountValidationResponse responses = new AccountValidationResponse(data.getAccount_number(),data.getAccount_name(),BankCode,"");
        return responses;
    }

    public String  createRecipient(TransferRecipientRequest request){
        String urlEndpoint = BASE_URL +"/transferrecipient";
        HttpHeaders headers = getHttpHeaders();
        Map<String, Object> recipientPayloadMap = recipientPayloadMap(request);
        String payload ="";
        try{
            payload = objectMapper.writeValueAsString(recipientPayloadMap);
        }catch (Exception e){

        }
        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(urlEndpoint,entity, Object.class);
        System.out.println(responseEntity.getBody());
        return null;
    }

    public Map<String, Object> recipientPayloadMap(TransferRecipientRequest request){
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", request.getType());
        payload.put("name", request.getName());
        payload.put("account_number", request.getAccount_number());
        payload.put("bank_code",request.getBank_code());
        payload.put("currency", request.getCurrency());
        return payload;
    }
}
