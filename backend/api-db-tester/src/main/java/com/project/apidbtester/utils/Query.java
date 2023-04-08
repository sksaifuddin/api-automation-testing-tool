package com.project.apidbtester.utils;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.entities.TestColumnValue;

import java.util.List;

/**
 * Query class is used to generate SQL queries from information provided to it
 */
public class Query {

    /**
     * fetch all rows of a table
     * @param tableName name of the table
     * @return generated query
     */
    public static String generateSelectQuery(String tableName) {
        return "select * from " + tableName + ";";
    }

    /**
     * fetch specific rows with specific columns of a table
     * @param testColumnValues details of columns to be fetched
     * @param testCaseDetails details of test performed needed for the primary key column name and value
     * @return generated query
     */
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

    /**
     * fetch the count of rows specific to a primary key value in a table
     * @param testCaseDetails details of test performed needed for the primary key column name and value
     * @return generated query
     */
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
