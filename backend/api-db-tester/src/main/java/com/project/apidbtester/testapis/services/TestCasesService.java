package com.project.apidbtester.testapis.services;

import com.project.apidbtester.testapis.constants.Constants;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCasesService {

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    public List<TestCaseDetails> fetchAllTestCases() {
        try {
            return testCaseDetailsRepository.findAll();
        } catch (Exception e) {
            throw new TestCasesNotFoundException();
        }
    }

    public TestCaseDetails fetchTestCaseById(int id) {
        try {
            return testCaseDetailsRepository.findById(id).get();
        } catch (Exception e) {
            throw new TestCaseNotFoundException();
        }
    }

    public String deleteTestCaseById(int id) {
        try {
            testCaseDetailsRepository.deleteById(id);
            return Constants.TEST_CASE_DELETED_SUCCESSFULLY_MSG;
        } catch (Exception e) {
            throw new TestCaseNotFoundException();
        }
    }

    public static class TestCaseNotFoundException extends RuntimeException {
        public TestCaseNotFoundException() {
            super(Constants.TEST_CASE_NOT_FOUND_EX_MSG);
        }
    }

    public static class TestCasesNotFoundException extends RuntimeException {
        public TestCasesNotFoundException() {
            super(Constants.TEST_CASES_NOT_FOUND_EX_MSG);
        }
    }
}
