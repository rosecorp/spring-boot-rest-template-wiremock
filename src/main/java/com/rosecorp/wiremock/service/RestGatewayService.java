package com.rosecorp.wiremock.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestGatewayService {

    @Value("${third.party.endpoint}")
    private String endpoint;

    @Value("${third.party.port}")
    private Integer port;

    @Value("${third.party.host}")
    private String host;

    private RestTemplate restTemplate;

    public RestGatewayService() {
        restTemplate = new RestTemplate();
    }

    public String getThirdPartyResponse() {
        String address = "http://" + host + ":" + port + endpoint;
        ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);

        return response.getBody();
    }


}
