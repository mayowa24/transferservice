package com.mcode.transferservice.utils;

import com.mcode.transferservice.flutterwave.models.responses.FlutterwaveTransferResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Collections;

@AllArgsConstructor
@Getter
@Setter
public class HttpUtils {

    //private static AsyncRestOperations restTemplate;
    private static RestTemplate restTemplate;


    public static HttpHeaders getRequestHeader(String urlEndpoint, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("content-type", "application/json");
        headers.set("Authorization", token);
        return headers;
    }

    public static ResponseEntity executeGetRequest(String urlEndpoint, String token){
        HttpHeaders requestHeader = getRequestHeader(urlEndpoint, token);
        HttpEntity<String> entity  = new HttpEntity<>(requestHeader);
        return restTemplate.exchange(urlEndpoint, HttpMethod.GET, entity, ResponseEntity.class);
    }

    public static ResponseEntity executePostRequest(String urlEndpoint, String token, String payload){
        HttpHeaders requestHeader = getRequestHeader(urlEndpoint, token);
        requestHeader.setAllow(Collections.singleton(HttpMethod.POST));
        HttpEntity<String> entity  = new HttpEntity<>(payload,requestHeader);
        return restTemplate.postForEntity(urlEndpoint,entity, ResponseEntity.class);
    }
}
