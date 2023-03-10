package com.project.apidbtester.testapis.dtos;

import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import lombok.Data;

import java.util.List;

@Data
public class TestInput {
    private TestCaseDetails testCaseDetails;
    private List<TestColumnValue> columnValues;
}
