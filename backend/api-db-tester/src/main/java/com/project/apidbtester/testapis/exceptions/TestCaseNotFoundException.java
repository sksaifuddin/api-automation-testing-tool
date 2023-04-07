package com.project.apidbtester.testapis.exceptions;

import com.project.apidbtester.testapis.constants.Constants;

public class TestCaseNotFoundException extends RuntimeException {
    public TestCaseNotFoundException() {
        super(Constants.TEST_CASE_NOT_FOUND_EX_MSG);
    }
}