package com.project.apidbtester.testapis.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TestResponse {
    private Integer httpStatusCode;
    private String httpErrorMsg = "";
    private Boolean allTestPassed=false;
    private List<ColumnResult> columnValues;
}
