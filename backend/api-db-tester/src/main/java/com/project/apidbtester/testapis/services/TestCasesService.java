package com.project.apidbtester.testapis.services;

import com.project.apidbtester.testapis.constants.Constants;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.exceptions.TestCaseNotFoundException;
import com.project.apidbtester.testapis.exceptions.TestCasesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TestCasesService is used handle fetching and deleting of test cases history saved in db
 */
@Service
public class TestCasesService {

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    /**
     * fetch list of all tests performed
     * @return list of tests details
     */
    public List<TestCaseDetails> fetchAllTestCases() {
        try {
            return testCaseDetailsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        } catch (Exception e) {
            throw new TestCasesNotFoundException();
        }
    }

    /**
     * fetch test by id
     * @param id
     * @return test details
     */
    public TestCaseDetails fetchTestCaseById(int id) {
        try {
            return testCaseDetailsRepository.findById(id).get();
        } catch (Exception e) {
            throw new TestCaseNotFoundException();
        }
    }

    /**
     * delete test by id
     * @param id
     * @return success/failure if deleted/not deleted
     */
    public String deleteTestCaseById(int id) {
        try {
            testCaseDetailsRepository.deleteById(id);
            return Constants.TEST_CASE_DELETED_SUCCESSFULLY_MSG;
        } catch (Exception e) {
            throw new TestCaseNotFoundException();
        }
    }
}
