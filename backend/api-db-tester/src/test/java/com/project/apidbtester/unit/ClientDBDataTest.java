package com.project.apidbtester.unit;

import com.project.apidbtester.utils.ClientDBData;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClientDBDataTest {

    @Test
    public void testGetPrimaryKey() throws SQLException {

        // Arrange
        Connection mockConnection = mock(Connection.class);
        DatabaseMetaData mockMetaData = mock(DatabaseMetaData.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.getMetaData()).thenReturn(mockMetaData);
        when(mockMetaData.getPrimaryKeys(null, null, "tableName")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("COLUMN_NAME")).thenReturn("id");

        ClientDBData clientDBData = new ClientDBData();
        String expectedPrimaryKey = "id";

        // Act
        String actualPrimaryKey = clientDBData.getPrimaryKey("tableName", mockConnection);

        // Assert
        assertEquals(expectedPrimaryKey, actualPrimaryKey);
    }

    @Test
    public void testGetPrimaryKey_throwsSQLException() throws SQLException {

        // Arrange
        Connection mockConnection = mock(Connection.class);

        when(mockConnection.getMetaData()).thenThrow(new SQLException());

        ClientDBData clientDBData = new ClientDBData();

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            clientDBData.getPrimaryKey("my_table", mockConnection);
        });
    }
}
