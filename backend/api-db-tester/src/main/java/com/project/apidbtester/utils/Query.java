package com.project.apidbtester.utils;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.entities.TestColumnValue;

import java.util.List;

public class Query {

    public static String generateSelectQuery(String tableName) {
        return "select * from " + tableName + ";";
    }

    public static String generateSelectQueryWithWhereClause(List<TestColumnValue> testColumnValues, TestCaseDetails testCaseDetails) {
        StringBuilder query = new StringBuilder("select ");

        for (int i = 0; i < testColumnValues.size(); i++) {
            if (i == testColumnValues.size() - 1) query.append(testColumnValues.get(i).getColumnName());
            else query.append(testColumnValues.get(i).getColumnName()).append(", ");
        }

        query.append(" from ")
                .append(testCaseDetails.getTableName())
                .append(" where ")
                .append(testCaseDetails.getPrimaryKeyName())
                .append(" = ")
                .append(testCaseDetails.getPrimaryKeyValue())
                .append(";");

        return query.toString();
    }

    public static String generateCountQueryWithWhereClause(TestCaseDetails testCaseDetails) {
        StringBuilder query = new StringBuilder("select count(*)");

        query.append(" from ")
                .append(testCaseDetails.getTableName())
                .append(" where ")
                .append(testCaseDetails.getPrimaryKeyName())
                .append(" = ")
                .append(testCaseDetails.getPrimaryKeyValue())
                .append(";");

        return query.toString();
    }
}
