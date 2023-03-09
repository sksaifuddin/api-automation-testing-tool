package com.project.apidbtester.testapis.put;

import com.project.apidbtester.responses.ClientDBConnectionException;
import com.project.apidbtester.testapis.dtos.TestCaseDetails;
import com.project.apidbtester.testapis.dtos.TestInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutApiController {

    @Autowired
    private PutApiService putApiService;

    @PostMapping("/test")
    public String fetchTestResult(@RequestBody TestInput testInput) throws ClientDBConnectionException {
        return putApiService.fetchTestResult(testInput);
    }
}
