package com.project.apidbtester.unit;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.utils.Query;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryTest {

    private final int SUCCESS = 200;

    @Test
    public void testGenerateSelectQuery() {
        // Arrange
        String tableName = "table";
        String expectedQuery = "select * from table;";

        // Act
        String actualQuery = Query.generateSelectQuery(tableName);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testGenerateSelectQueryWithWhereClause() {
        // Arrange
        TestCaseDetails testCaseDetails = new TestCaseDetails(1, "type", "url", "payload", "tableName", "primaryKeyName", "primaryKeyValue", SUCCESS, "", false);

        List<TestColumnValue> testColumnValues = Arrays.asList(
                new TestColumnValue(1, "column1", "Hello1", "Hello1", false, testCaseDetails),
                new TestColumnValue(2, "column2", "Hello2", "Hello2", false, testCaseDetails),
                new TestColumnValue(3, "column3", "Hello3", "Hello3", false, testCaseDetails)
        );

        String expectedQuery = "select column1, column2, column3 from tableName where primaryKeyName = primaryKeyValue;";

        // Act
        String actualQuery = Query.generateSelectQueryWithWhereClause(testColumnValues, testCaseDetails);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testGenerateCountQueryWithWhereClause() {
        // Arrange
        TestCaseDetails testCaseDetails = new TestCaseDetails(1, "type", "url", "payload", "tableName", "primaryKeyName", "primaryKeyValue", SUCCESS, "", false);

        String expectedQuery = "select count(*) from tableName where primaryKeyName = primaryKeyValue;";

        // Act
        String actualQuery = Query.generateCountQueryWithWhereClause(testCaseDetails);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }
}
