package com.project.apidbtester;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.utils.Query;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryTest {

    @Test
    public void testGenerateSelectQuery() {
        String tableName = "table";
        String expectedQuery = "select * from table;";
        String actualQuery = Query.generateSelectQuery(tableName);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testGenerateSelectQueryWithWhereClause() {
        TestCaseDetails testCaseDetails = new TestCaseDetails(1, "type", "url", "payload", "tableName", "primaryKeyName", "primaryKeyValue", 200, "", false);

        List<TestColumnValue> testColumnValues = Arrays.asList(
                new TestColumnValue(1, "column1", "Hello1", "Hello1", false, testCaseDetails),
                new TestColumnValue(2, "column2", "Hello2", "Hello2", false, testCaseDetails),
                new TestColumnValue(3, "column3", "Hello3", "Hello3", false, testCaseDetails)
        );


        String expectedQuery = "select column1, column2, column3 from tableName where primaryKeyName = primaryKeyValue;";
        String actualQuery = Query.generateSelectQueryWithWhereClause(testColumnValues, testCaseDetails);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testGenerateCountQueryWithWhereClause() {
        TestCaseDetails testCaseDetails = new TestCaseDetails(1, "type", "url", "payload", "tableName", "primaryKeyName", "primaryKeyValue", 200, "", false);

        String expectedQuery = "select count(*) from tableName where primaryKeyName = primaryKeyValue;";
        String actualQuery = Query.generateCountQueryWithWhereClause(testCaseDetails);
        assertEquals(expectedQuery, actualQuery);
    }
}
