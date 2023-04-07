package com.project.apidbtester.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import com.project.apidbtester.utils.TestRequest;
import org.junit.Before;
import org.junit.Test;

import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.entities.TestColumnValue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestRequestTest {

    private TestRequest testRequest;

    @Before
    public void setUp() {
        testRequest = new TestRequest();
    }

    @Test
    public void testSendRequestWithValidTestCaseDetails() {
        // create a sample test case
        TestCaseDetails testCaseDetails = new TestCaseDetails();
        testCaseDetails.setUrl("https://jsonplaceholder.typicode.com/posts/1");
        testCaseDetails.setType("get");

        // send the request
        Response response = testRequest.sendRequest(testCaseDetails);

        // assert that the response is not null
        assertNotNull(response);
        // assert that the response status code is 200
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSendRequestWithInvalidUrl() {
        // create a sample test case with invalid URL
        TestCaseDetails testCaseDetails = new TestCaseDetails();
        testCaseDetails.setUrl("https://invalid.url.com");
        testCaseDetails.setType("get");

        // send the request
        Response response = testRequest.sendRequest(testCaseDetails);

        // assert that the response is null
        assertNull(response);
    }

    @Test
    public void testSendRequestWithInvalidType() {
        // create a sample test case with invalid request type
        TestCaseDetails testCaseDetails = new TestCaseDetails();
        testCaseDetails.setUrl("https://jsonplaceholder.typicode.com/posts");
        testCaseDetails.setType("invalid");

        // send the request
        Response response = testRequest.sendRequest(testCaseDetails);

        // assert that the response is null
        assertNull(response);
    }

}
