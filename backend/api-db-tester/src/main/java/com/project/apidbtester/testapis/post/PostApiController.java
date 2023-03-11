package com.project.apidbtester.testapis.post;

import com.project.apidbtester.responses.ClientDBConnectionException;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.put.PutApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class PostApiController {
    @Autowired
    private PostApiService postApiService;

    @PostMapping("/test-post-api")
    public TestResponse fetchTestResult(@RequestBody TestInput testInput) throws ClientDBConnectionException {
        return postApiService.fetchTestResult(testInput);
    }
}