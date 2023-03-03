package com.project.apidbtester.clientdbinfo;

import com.project.apidbtester.clientdbinfo.dtos.ClientDBSchema;
import com.project.apidbtester.response.ClientDBConnectionException;
import com.project.apidbtester.response.CustomApiResponseBody;
import com.project.apidbtester.testapis.dtos.TestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class ClientDbCredentialsController {
    @Autowired
    private ClientDBCredentialsService clientDBCredentialsService;
    @PostMapping("/test-client-db-connection")
    public CustomApiResponseBody testClientDBConnection(@RequestBody ClientDBCredentialsEntity clientDBCredentialsEntity) throws ClientDBConnectionException {
        return clientDBCredentialsService.testClientDBConnection(clientDBCredentialsEntity);
    }

    @PostMapping("/fetch-client-db-schema")
    public Map<String, ClientDBSchema> fetchClientDBSchema(@RequestBody ClientDBCredentialsEntity clientDBCredentialsEntity) throws ClientDBConnectionException {
        return clientDBCredentialsService.fetchClientDBSchema(clientDBCredentialsEntity);
    }

    @PostMapping("/test")
    public String fetchTestResult(@RequestBody TestDetails testDetails) throws ClientDBConnectionException {
        return clientDBCredentialsService.fetchTestResult(testDetails);
    }
}
