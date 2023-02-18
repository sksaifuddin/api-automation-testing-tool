package com.project.apidbtester.model;

import java.util.List;
import java.util.Map;

public class ClientDBSchema {
    private List<String> primaryKeys;
    private Map<String, List<Map<String, String>>> tableMap;

    public ClientDBSchema(List<String> primaryKeys, Map<String, List<Map<String, String>>> tableMap) {
        this.primaryKeys = primaryKeys;
        this.tableMap = tableMap;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public Map<String, List<Map<String, String>>> getTableMap() {
        return tableMap;
    }

    public void setTableMap(Map<String, List<Map<String, String>>> tableMap) {
        this.tableMap = tableMap;
    }
}
