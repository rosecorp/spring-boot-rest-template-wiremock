package com.rosecorp.wiremock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;

@Service
public class RestGatewayService {

    @Value("${third.party.endpoint}")
    private String endpoint;

    @Value("${third.party.port}")
    private Integer port;

    @Value("${third.party.host}")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RetryTemplate retryTemplate;

    public String getThirdPartyResponse() {
        String address = "http://" + host + ":" + port + endpoint;
//        ResponseEntity<String> response;// = restTemplate.getForEntity(address, String.class);

        ResponseEntity<String> response = retryTemplate.execute(context -> {
            System.out.println("inside retry method");
            System.out.println(context.getLastThrowable());
            return restTemplate.getForEntity(address, String.class);
        });


        return response.getBody();
    }

}
