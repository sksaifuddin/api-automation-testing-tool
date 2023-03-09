package com.project.apidbtester.testapis.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TestInput {
    private TestCaseDetails testCaseDetails;
    private List<ColumnValue> columnValues;
}
