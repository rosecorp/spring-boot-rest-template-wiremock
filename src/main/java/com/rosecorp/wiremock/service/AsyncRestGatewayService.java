package com.rosecorp.wiremock.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncRestGatewayService {

    private RestTemplate restTemplate;

    public AsyncRestGatewayService() {
        restTemplate = new RestTemplate();
    }

    public String retrieveDataAsync() {
        long startTime = System.nanoTime();
        CompletableFuture<String> google =
                CompletableFuture.supplyAsync(this::retrieveDataGoogle);

        CompletableFuture<String> bing =
                CompletableFuture.supplyAsync(this::retrieveDataBing);

        CompletableFuture<String> combinedFuture = google
                .thenCombine(bing, (a, b) -> a.toString() + b.toString());

        String data = combinedFuture.join();

        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("async retrieve data: " + estimatedTime);

        return data;
    }

    public String retrieveData() {
        long startTime = System.nanoTime();
        String google = retrieveDataGoogle();

        String bing = retrieveDataBing();
        String data = google + bing;

        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("non - async retrieve data: " + estimatedTime);

        return data;
    }

    public String retrieveDataGoogle() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> response = restTemplate.getForEntity("http://google.co.uk", String.class);

        return response.getBody();
    }

    public String retrieveDataBing() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> response = restTemplate.getForEntity("http://www.bing.com", String.class);

        return response.getBody();
    }
}
