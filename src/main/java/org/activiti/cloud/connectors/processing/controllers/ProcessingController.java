package org.activiti.cloud.connectors.processing.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessingController {
    @RequestMapping(method = RequestMethod.GET, path = "/v1/home")
    public String welcome() {
        return " { \"welcome\" : \"Welcome to the Content Processing and Sentiment Analysis Cloud Connector\" }";
    }

}
