package com.rosecorp.wiremock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@Service
public class AsyncRestGatewayService {

    @Autowired
    private RestTemplate restTemplate;

    public String retrieveDataAsync() throws TimeoutException {
        long startTime = System.nanoTime();
        
        CompletableFuture<String> google =
                CompletableFuture.supplyAsync(this::retrieveDataGoogle);

        CompletableFuture<String> bing =
                CompletableFuture.supplyAsync(this::retrieveDataBing);

        CompletableFuture<String> google2 =
                CompletableFuture.supplyAsync(this::retrieveDataGoogle);

        CompletableFuture<String> bing2 =
                CompletableFuture.supplyAsync(this::retrieveDataBing);

        CompletableFuture<String> combinedFuture = google
                .thenCombine(bing, (a, b) -> a.toString() + b.toString())
                .thenCombine(bing2, (a, b) -> a.toString() + b.toString())
                .thenCombine(google2, (a, b) -> a.toString() + b.toString());

        String data = combinedFuture.join();

        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("async retrieve data: " + estimatedTime);

        return data;
    }

    public String retrieveData() {
        long startTime = System.nanoTime();

        String google = retrieveDataGoogle();
        String bing = retrieveDataBing();
        String google2 = retrieveDataGoogle();
        String bing2 = retrieveDataBing();
        String data = google + bing + google2 + bing2;

        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("non - async retrieve data: " + estimatedTime);

        return data;
    }

    public String retrieveDataGoogle() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> response = restTemplate.getForEntity("http://google.co.uk", String.class);

        return response.getBody();
    }

    public String retrieveDataBing() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> response = restTemplate.getForEntity("http://www.bing.com", String.class);

        return response.getBody();
    }

}
