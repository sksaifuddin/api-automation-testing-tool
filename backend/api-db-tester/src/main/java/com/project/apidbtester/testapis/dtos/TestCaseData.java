package com.project.apidbtester.testapis.dtos;

import lombok.Data;

@Data
public class TestCaseData {
    private Integer id;
    private String type;
    private String url;
    private String payload;
    private String tableName;
    private String primaryKeyName;
    private String primaryKeyValue;
    private Integer httpStatusCode;
    private String httpErrorMsg = "";
    private Boolean passed=false;
}
