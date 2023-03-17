package com.project.apidbtester;

import com.project.apidbtester.testapis.constants.Constants;
import com.project.apidbtester.testapis.controllers.TestCasesController;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.services.TestCasesService;
import com.project.apidbtester.utils.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCasesControllerTest {

    @InjectMocks
    private TestCasesController testCasesController;

    @Mock
    private TestCasesService testCasesService;

    @Test
    public void testFetchAllTestCases() {
        List<TestCaseDetails> testCaseDetailsList = new ArrayList<>();
        when(testCasesService.fetchAllTestCases()).thenReturn(testCaseDetailsList);

        ResponseEntity<List<TestCaseDetails>> responseEntity = testCasesController.fetchAllTestCases();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(testCaseDetailsList, responseEntity.getBody());
    }

    @Test
    public void testFetchTestCaseById() {
        int id = 1;
        TestCaseDetails testCaseDetails = new TestCaseDetails();
        when(testCasesService.fetchTestCaseById(id)).thenReturn(testCaseDetails);

        ResponseEntity<TestCaseDetails> responseEntity = testCasesController.fetchTestCaseById(id);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(testCaseDetails, responseEntity.getBody());
    }

    @Test
    public void testDeleteTestCaseById() {
        int id = 1;
        String message = Constants.TEST_CASE_DELETED_SUCCESSFULLY_MSG;
        when(testCasesService.deleteTestCaseById(id)).thenReturn(message);

        ResponseEntity<String> responseEntity = testCasesController.deleteTestCaseById(id);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(message, responseEntity.getBody());
    }

    @Test
    public void testHandleExceptions_whenTestCaseNotFound() {
        String errorMessage = Constants.TEST_CASE_NOT_FOUND_EX_MSG;
        TestCasesService.TestCaseNotFoundException exception = new TestCasesService.TestCaseNotFoundException();

        ResponseEntity<ErrorResponse> responseEntity = testCasesController.handleExceptions(exception);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(errorMessage, responseEntity.getBody().getMessage());
    }

    @Test
    public void testHandleExceptions_whenNoTestsCasesFound() {
        String errorMessage = Constants.TEST_CASES_NOT_FOUND_EX_MSG;
        TestCasesService.TestCasesNotFoundException exception = new TestCasesService.TestCasesNotFoundException();

        ResponseEntity<ErrorResponse> responseEntity = testCasesController.handleExceptions(exception);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(errorMessage, responseEntity.getBody().getMessage());
    }
}

