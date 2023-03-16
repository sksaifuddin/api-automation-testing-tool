package com.project.apidbtester.testapis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDetails {
    @Id
    @GeneratedValue
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
