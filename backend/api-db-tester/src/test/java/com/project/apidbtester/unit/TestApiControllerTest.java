package com.project.apidbtester.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.apidbtester.testapis.controllers.TestApiController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.services.DeleteApiService;
import com.project.apidbtester.testapis.services.GetApiService;
import com.project.apidbtester.testapis.services.PostApiService;
import com.project.apidbtester.testapis.services.PutApiService;

@ExtendWith(MockitoExtension.class)
public class TestApiControllerTest {

	@Mock
	private PostApiService postApiService;
	@Mock
	private PutApiService putApiService;
	@Mock
	private DeleteApiService deleteApiService;
	@Mock
	private GetApiService getApiService;
	@InjectMocks
	private TestApiController controller;

	@Test
	public void testFetchTestResultWithPostRequest() {
		// Arrange
		TestInput testInput = new TestInput();
		TestCaseDetails testCaseDetails = new TestCaseDetails();
		testCaseDetails.setType("POST");
		testInput.setTestCaseDetails(testCaseDetails);
		TestResponse testResponse = new TestResponse();
		testResponse.setHttpStatusCode(HttpStatus.OK.value());
		when(postApiService.fetchTestResult(testInput)).thenReturn(testResponse);

		// Act
		ResponseEntity<TestResponse> response = controller.fetchTestResult(testInput);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(postApiService, times(1)).fetchTestResult(testInput);
	}

	@Test
	public void testFetchTestResultWithPutRequest() {
		// Arrange
		TestInput testInput = new TestInput();
		TestCaseDetails testCaseDetails = new TestCaseDetails();
		testCaseDetails.setType("PUT");
		testInput.setTestCaseDetails(testCaseDetails);
		TestResponse testResponse = new TestResponse();
		testResponse.setHttpStatusCode(HttpStatus.OK.value());
		when(putApiService.fetchTestResult(testInput)).thenReturn(testResponse);

		// Act
		ResponseEntity<TestResponse> response = controller.fetchTestResult(testInput);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(putApiService, times(1)).fetchTestResult(testInput);
	}

	@Test
	public void testFetchTestResultWithGetRequest() {
		// Arrange
		TestInput testInput = new TestInput();
		TestCaseDetails testCaseDetails = new TestCaseDetails();
		testCaseDetails.setType("GET");
		testInput.setTestCaseDetails(testCaseDetails);
		TestResponse testResponse = new TestResponse();
		testResponse.setHttpStatusCode(HttpStatus.OK.value());
		when(getApiService.fetchTestResult(testInput)).thenReturn(testResponse);

		// Act
		ResponseEntity<TestResponse> response = controller.fetchTestResult(testInput);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(getApiService, times(1)).fetchTestResult(testInput);
	}

	@Test
	public void testFetchTestResultWithDeleteRequest() {
		// Arrange
		TestInput testInput = new TestInput();
		TestCaseDetails testCaseDetails = new TestCaseDetails();
		testCaseDetails.setType("DELETE");
		testInput.setTestCaseDetails(testCaseDetails);
		TestResponse testResponse = new TestResponse();
		testResponse.setHttpStatusCode(HttpStatus.OK.value());
		when(deleteApiService.fetchTestResult(testInput)).thenReturn(testResponse);

		// Act
		ResponseEntity<TestResponse> response = controller.fetchTestResult(testInput);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(deleteApiService, times(1)).fetchTestResult(testInput);
	}

	@Test
	public void testFetchTestResultWithInvalidRequestType() throws TestApiController.InvalidRequestTypeException {
		// Arrange
		TestInput testInput = new TestInput();
		TestCaseDetails testCaseDetails = new TestCaseDetails();
		testCaseDetails.setType("INVALID_REQUEST_TYPE");
		testInput.setTestCaseDetails(testCaseDetails);

		// Act and Assert
		assertThrows(TestApiController.InvalidRequestTypeException.class, () -> controller.fetchTestResult(testInput));
	}

}
