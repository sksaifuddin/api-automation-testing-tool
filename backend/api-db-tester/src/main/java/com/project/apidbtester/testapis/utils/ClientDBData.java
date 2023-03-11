package com.project.apidbtester.testapis.utils;

import com.project.apidbtester.clientdbinfo.ClientDBCredentialsEntity;
import com.project.apidbtester.constants.GlobalConstants;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.entities.TestColumnValue;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.List;

public class ClientDBData {

    public static String getPrimaryKey(String tableName, ClientDBCredentialsEntity clientDBCredentials) {
        try (Connection connection = DriverManager.getConnection(clientDBCredentials.getDatabaseUrl(),
                clientDBCredentials.getUserName(),
                clientDBCredentials.getPassword())) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getPrimaryKeys(null, null, tableName);

            String primaryKey = "";
            while (resultSet.next()) {
                primaryKey = resultSet.getString("COLUMN_NAME");
            }
            System.out.println("Primary key: " + primaryKey);
            return primaryKey;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
