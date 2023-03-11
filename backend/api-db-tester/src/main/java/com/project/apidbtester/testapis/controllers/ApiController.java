package com.project.apidbtester.testapis.controllers;

import com.project.apidbtester.responses.ClientDBConnectionException;
import com.project.apidbtester.testapis.services.DeleteApiService;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.services.GetApiService;
import com.project.apidbtester.testapis.services.PostApiService;
import com.project.apidbtester.testapis.services.PutApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private PostApiService postApiService;
    @Autowired
    private PutApiService putApiService;
    @Autowired
    private DeleteApiService deleteApiService;
    @Autowired
    private GetApiService getApiService;

    @PostMapping("/test")
    public TestResponse fetchTestResult(@RequestBody TestInput testInput) throws ClientDBConnectionException {
        switch (testInput.getTestCaseDetails().getType().toUpperCase()) {
            case "POST":
                return postApiService.fetchTestResult(testInput);
            case "PUT":
                return putApiService.fetchTestResult(testInput);
            case "DELETE":
                return deleteApiService.fetchTestResult(testInput);
            case "GET":
                return getApiService.fetchTestResult(testInput);
            default:
                TestResponse testResponse = new TestResponse();
                testResponse.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
                testResponse.setHttpErrorMsg("Invalid request type");
                return testResponse;
        }
    }
}
