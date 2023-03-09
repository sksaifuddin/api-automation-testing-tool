package com.project.apidbtester.testapis.dtos;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class TestCaseDetails {
    @Id
    @GeneratedValue
    private int id;
    private String type;
    private String url;
    private String payload;
    private String tableName;
    private String primaryKeyName;
    private String primaryKeyValue;
//    @OneToMany(mappedBy = "testCaseDetails", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ColumnValue> columnValues;
}
