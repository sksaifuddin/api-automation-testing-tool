package com.project.apidbtester.testapis.dtos;

import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import lombok.Data;

import java.util.List;

/**
 * TestInput is used to parse the test api request body in the required format so that it can
 * be processed by the service classes
 */
@Data
public class TestInput {
    private TestCaseDetails testCaseDetails;
    private List<TestColumnValue> columnValues;
}
