package com.project.apidbtester.testapis.services;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.responses.TestCasesApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCasesService {

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    public List<TestCaseDetails> fetchAllTestCases() throws TestCasesApiException {
        try {
            return testCaseDetailsRepository.findAll();
        } catch (Exception e) {
            throw new TestCasesApiException("No Test cases found");
        }
    }

    public TestCaseDetails fetchTestCaseById(int id) throws TestCasesApiException {
        try {
            return testCaseDetailsRepository.findById(id).get();
        } catch (Exception e) {
            throw new TestCasesApiException("Test case not found");
        }
    }

    public String deleteTestCaseById(int id) throws TestCasesApiException {
        try {
            testCaseDetailsRepository.deleteById(id);
            return "Test case deleted successfully";
        } catch (Exception e) {
            throw new TestCasesApiException("Test case not found to delete");
        }
    }
}
