package com.project.apidbtester.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TestDetails {
    String type;
    String url;
    String payload;
    String tableName;
    List<Map<String, String >> primaryKey;
    List<Map<String, String >> colsValsList;
}
