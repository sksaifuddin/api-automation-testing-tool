package com.project.apidbtester;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.apidbtester.testapis.constants.Constants;
import com.project.apidbtester.testapis.exceptions.TestCaseNotFoundException;
import com.project.apidbtester.testapis.exceptions.TestCasesNotFoundException;
import com.project.apidbtester.testapis.services.TestCasesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;


@ExtendWith(MockitoExtension.class)
public class TestCasesServiceTest {

    @InjectMocks
    private TestCasesService testCasesService;

    @Mock
    private TestCaseDetailsRepository testCaseDetailsRepository;

    @Test
    public void testFetchAllTestCases() throws TestCasesNotFoundException {
        List<TestCaseDetails> testCaseDetailsList = new ArrayList<>();
        testCaseDetailsList.add(new TestCaseDetails());
        testCaseDetailsList.add(new TestCaseDetails());
        testCaseDetailsList.add(new TestCaseDetails());

        when(testCaseDetailsRepository.findAll()).thenReturn(testCaseDetailsList);

        List<TestCaseDetails> result = testCasesService.fetchAllTestCases();
        assertEquals(3, result.size());
    }

    @Test
    public void testFetchAllTestCases_whenNoTestCasesFound() throws TestCaseNotFoundException {
        when(testCaseDetailsRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(TestCasesNotFoundException.class, () -> testCasesService.fetchAllTestCases());
    }

    @Test
    public void testFetchTestCaseById() throws TestCaseNotFoundException {
        TestCaseDetails testCaseDetails = TestCaseDetails.builder().id(1).build();
        when(testCaseDetailsRepository.findById(1)).thenReturn(Optional.of(testCaseDetails));

        TestCaseDetails result = testCasesService.fetchTestCaseById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void testFetchTestCaseById_whenTestCaseNotFound() throws TestCaseNotFoundException {
        when(testCaseDetailsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TestCaseNotFoundException.class, () -> testCasesService.fetchTestCaseById(1));
    }

    @Test
    public void testDeleteTestCaseById() throws TestCaseNotFoundException {
        doNothing().when(testCaseDetailsRepository).deleteById(1);

        String result = testCasesService.deleteTestCaseById(1);

        assertEquals(Constants.TEST_CASE_DELETED_SUCCESSFULLY_MSG, result);
    }

    @Test
    public void testDeleteTestCaseById_whenTestCaseNotFound() throws TestCaseNotFoundException {
        doThrow(new RuntimeException()).when(testCaseDetailsRepository).deleteById(1);

        assertThrows(TestCaseNotFoundException.class, () -> testCasesService.deleteTestCaseById(1));
    }
}
