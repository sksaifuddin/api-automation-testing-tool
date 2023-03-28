package com.project.apidbtester.utils;

import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.net.ConnectException;
import java.util.List;

public class TestRequest {

    public Response sendRequest(TestCaseDetails testCaseDetails) {
        try {
            RequestSpecification request = RestAssured.given();
            request.contentType(ContentType.JSON);
            request.baseUri(testCaseDetails.getUrl());
            if (!testCaseDetails.getType().equalsIgnoreCase("get"))
                request.body(testCaseDetails.getPayload());

            Response response = null;

            switch (testCaseDetails.getType().toLowerCase()) {
                case "put" -> response = request.put();
                case "post" -> response = request.post();
                case "get" -> response = request.get();
                case "delete" -> response = request.delete();
                default -> {
                    return null;
                }
            }
            return response;
        } catch(Exception e) {
            return null;
        }
    }
}
