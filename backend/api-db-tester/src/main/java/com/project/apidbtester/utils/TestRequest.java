package com.project.apidbtester.utils;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * TestRequest is used to handle forward api requests to client app
 */
public class TestRequest {

    /**
     * Forward the request to client app
     * @param testCaseDetails the details sent by the tester
     * @return client app response
     */
    public Response sendRequest(TestCaseDetails testCaseDetails) {
        try {
            RequestSpecification request = RestAssured.given();
            request.contentType(ContentType.JSON);
            request.baseUri(testCaseDetails.getUrl());
            if (!testCaseDetails.getType().equalsIgnoreCase("get"))
                request.body(testCaseDetails.getPayload());

            Response response = null;

            // forward request based on request type
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
