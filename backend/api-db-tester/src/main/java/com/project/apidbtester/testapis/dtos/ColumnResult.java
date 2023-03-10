package com.project.apidbtester.testapis.dtos;

import lombok.Data;

@Data
public class ColumnResult {
    private String columnName;
    private String expectedValue;
    private String actualValue;
    private Boolean passed;
}
