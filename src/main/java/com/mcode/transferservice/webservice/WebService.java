package com.mcode.transferservice.webservice;

import com.mcode.transferservice.exceptions.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Component
@AllArgsConstructor
public class WebService<T> {

    private final RestTemplate restTemplate;



    private static HttpHeaders getRequestHeader(String urlEndpoint, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("content-type", "application/json");
        headers.set("Authorization", token);
        return headers;
    }

    public ResponseEntity<T> executeGetRequest(String urlEndpoint, String token, Class<T> clazz){
        HttpHeaders requestHeader = getRequestHeader(urlEndpoint, token);
        HttpEntity<String> entity  = new HttpEntity<>(requestHeader);
        return  restTemplate.exchange(urlEndpoint, HttpMethod.GET, entity, clazz);
    }

    public ResponseEntity<T> executePostRequest(String urlEndpoint, String token, String payload, Class<T> clazz){
        HttpHeaders requestHeader = getRequestHeader(urlEndpoint, token);
        requestHeader.setAllow(Collections.singleton(HttpMethod.POST));
        HttpEntity<String> entity  = new HttpEntity<>(payload,requestHeader);
        return restTemplate.postForEntity(urlEndpoint,entity, clazz);
    }
}

