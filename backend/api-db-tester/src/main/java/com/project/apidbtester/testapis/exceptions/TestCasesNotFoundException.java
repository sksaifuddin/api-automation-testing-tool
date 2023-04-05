package com.project.apidbtester.testapis.exceptions;

import com.project.apidbtester.testapis.constants.Constants;

public class TestCasesNotFoundException extends RuntimeException {
    public TestCasesNotFoundException() {
        super(Constants.TEST_CASES_NOT_FOUND_EX_MSG);
    }
}