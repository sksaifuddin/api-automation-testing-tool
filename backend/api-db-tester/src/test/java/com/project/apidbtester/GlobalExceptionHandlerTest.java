package com.project.apidbtester;

import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.clientdb.constants.Constants;
import com.project.apidbtester.clientdb.exceptions.ClientDBConnectionException;
import com.project.apidbtester.clientdb.exceptions.ClientDBCredentialsNotFoundException;
import com.project.apidbtester.exceptions.GlobalExceptionHandler;
import com.project.apidbtester.testapis.controllers.TestApiController;
import com.project.apidbtester.testapis.exceptions.TestCaseNotFoundException;
import com.project.apidbtester.testapis.exceptions.TestCasesNotFoundException;
import com.project.apidbtester.testapis.services.TestCasesService;
import com.project.apidbtester.utils.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.apidbtester.testapis.constants.Constants.*;
import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

    @Mock
    private ClientDBInfoService clientDBInfoService;

    @Mock
    private TestCasesService testCasesService;

    @Mock
    private TestApiController testApiController;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleClientDBCredentialsNotFoundException() {
        String errorMessage = Constants.CLIENT_DB_CREDENTIALS_NOT_FOUND;
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleExceptions(new ClientDBCredentialsNotFoundException());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    public void handleClientDBConnectionExceptionTest() {
        String errorMessage = Constants.CLIENT_DB_CONNECTION_FAIL;
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleExceptions(new ClientDBConnectionException());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    public void testHandleTestCaseNotFoundException() {
        String errorMessage = TEST_CASE_NOT_FOUND_EX_MSG;
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleExceptions(new TestCaseNotFoundException());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    public void testHandleTestCasesNotFoundException() {
        String errorMessage = TEST_CASES_NOT_FOUND_EX_MSG;
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleExceptions(new TestCasesNotFoundException());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    public void testHandleInvalidRequestTypeException() {
        String errorMessage = INVALID_API_REQUEST_TYPE;
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleExceptions(new TestApiController.InvalidRequestTypeException());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
    }
}

