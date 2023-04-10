package com.project.apidbtester.testapis.exceptions;

import com.project.apidbtester.testapis.constants.Constants;

/**
 * TestCaseNotFoundException throws an exception if the requested list of test cases
 * does not exist in db
 */
public class TestCasesNotFoundException extends RuntimeException {
    public TestCasesNotFoundException() {
        super(Constants.TEST_CASES_NOT_FOUND_EX_MSG);
    }
}