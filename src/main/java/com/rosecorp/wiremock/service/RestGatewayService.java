package com.rosecorp.wiremock.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Service
public class RestGatewayService {

    @Value("${third.party.endpoint}")
    private String endpoint;

    @Value("${third.party.port}")
    private Integer port;

    @Value("${third.party.host}")
    private String host;

    private RestTemplate restTemplate;
    private WireMockServer wireMockServer;

    public RestGatewayService() {
        wiremockConfig();
        restTemplate = new RestTemplate();
    }

    public String getThirdPartyResponse() {
        String address = host + ":" + port + endpoint;
        ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);

        return response.getBody();
    }

    private void wiremockConfig() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        WireMock.configureFor("localhost", 8089);
        wireMockServer.start();

        stubFor(get(urlEqualTo(endpoint))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>Some content</response>")));
    }

}
