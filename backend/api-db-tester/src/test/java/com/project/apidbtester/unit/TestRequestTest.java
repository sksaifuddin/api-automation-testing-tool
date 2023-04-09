package com.project.apidbtester.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.project.apidbtester.utils.TestRequest;
import org.junit.Before;
import org.junit.Test;

import com.project.apidbtester.testapis.entities.TestCaseDetails;

import io.restassured.response.Response;

public class TestRequestTest {

    private TestRequest testRequest;

    @Before
    public void setUp() {
        testRequest = new TestRequest();
    }

    @Test
    public void testSendRequestWithValidTestCaseDetails() {
        // Arrange
        TestCaseDetails testCaseDetails = new TestCaseDetails();
        testCaseDetails.setUrl("https://jsonplaceholder.typicode.com/posts/1");
        testCaseDetails.setType("get");

        // Act
        Response response = testRequest.sendRequest(testCaseDetails);

        // Assert
        assertNotNull(response);
//        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSendRequestWithInvalidUrl() {
        // Arrange
        TestCaseDetails testCaseDetails = new TestCaseDetails();
        testCaseDetails.setUrl("https://invalid.url.com");
        testCaseDetails.setType("get");

        // Act
        Response response = testRequest.sendRequest(testCaseDetails);

        // Assert
        assertNull(response);
    }

    @Test
    public void testSendRequestWithInvalidType() {
        // Arrange
        TestCaseDetails testCaseDetails = new TestCaseDetails();
        testCaseDetails.setUrl("https://jsonplaceholder.typicode.com/posts");
        testCaseDetails.setType("invalid");

        // Act
        Response response = testRequest.sendRequest(testCaseDetails);

        // Assert
        assertNull(response);
    }
}
