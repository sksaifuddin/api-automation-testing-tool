package com.project.apidbtester.testapis.put;

import com.project.apidbtester.responses.ClientDBConnectionException;
import com.project.apidbtester.testapis.dtos.TestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutApiController {

    @Autowired
    private PutApiService putApiService;

    @PostMapping("/test")
    public String fetchTestResult(@RequestBody TestDetails testDetails) throws ClientDBConnectionException {
        return putApiService.fetchTestResult(testDetails);
    }
}
