package com.project.apidbtester.testapis.controllers;

import com.project.apidbtester.testapis.constants.ApiRequestType;
import com.project.apidbtester.testapis.constants.Constants;
import com.project.apidbtester.testapis.services.DeleteApiService;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.services.GetApiService;
import com.project.apidbtester.testapis.services.PostApiService;
import com.project.apidbtester.testapis.services.PutApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestApiController {
    @Autowired
    private PostApiService postApiService;
    @Autowired
    private PutApiService putApiService;
    @Autowired
    private DeleteApiService deleteApiService;
    @Autowired
    private GetApiService getApiService;

    @PostMapping("/test")
    public ResponseEntity<TestResponse> fetchTestResult(@RequestBody TestInput testInput) {
        String inputRequestType = testInput.getTestCaseDetails().getType();
        try {
            ApiRequestType apiRequestType = ApiRequestType.valueOf(inputRequestType.toUpperCase());

            switch (apiRequestType) {
                case POST:
                    return ResponseEntity.ok(postApiService.fetchTestResult(testInput));
                case PUT:
                    return ResponseEntity.ok(putApiService.fetchTestResult(testInput));
                case DELETE:
                    return ResponseEntity.ok(deleteApiService.fetchTestResult(testInput));
                case GET:
                    return ResponseEntity.ok(getApiService.fetchTestResult(testInput));
                default:
                    throw new InvalidRequestTypeException();
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestTypeException();
        }
    }

    public static class InvalidRequestTypeException extends RuntimeException {
        public InvalidRequestTypeException() {
            super(Constants.INVALID_API_REQUEST_TYPE);
        }
    }
}
