package com.project.apidbtester.controller;

import com.project.apidbtester.model.ClientDBDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClientDBDetailsController {
    @PostMapping("/test-client-db-connection")
    public String testClientDBConnection(@RequestBody ClientDBDetails clientDBDetails) {
        return "Connection Successful";
    }
}
