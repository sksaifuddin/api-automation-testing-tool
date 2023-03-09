package com.project.apidbtester.testapis.dtos;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ColumnValue {
    @Id
    @GeneratedValue
    private int id;
    private String columnName;
    private String expectedValue;
    private String actualValue;
    private boolean passed = true;
    @ManyToOne
    @JoinColumn(name = "test_case_details_id", referencedColumnName = "id")
    private TestCaseDetails testCaseDetails;
}
