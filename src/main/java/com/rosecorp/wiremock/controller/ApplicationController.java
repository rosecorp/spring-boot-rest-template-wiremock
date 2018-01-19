package com.rosecorp.wiremock.controller;

import com.rosecorp.wiremock.service.RestGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ApplicationController {

    @Autowired
    private RestGatewayService restGatewayService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String index() {
        String response = restGatewayService.getThirdPartyResponse();

        return response;
    }
}
