package com.project.apidbtester.model;

import java.util.List;
import java.util.Map;

public class ClientDBSchema {
    private List<String> primaryKeyList;
    private List<Map<String, String>> columnsList;

    public ClientDBSchema(List<String> primaryKeyList, List<Map<String, String>> columnsList) {
        this.primaryKeyList = primaryKeyList;
        this.columnsList = columnsList;
    }

    public List<String> getPrimaryKeyList() {
        return primaryKeyList;
    }

    public void setPrimaryKeyList(List<String> primaryKeyList) {
        this.primaryKeyList = primaryKeyList;
    }

    public List<Map<String, String>> getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List<Map<String, String>> columnsList) {
        this.columnsList = columnsList;
    }
}
