package com.project.apidbtester.testapis.utils;

import com.project.apidbtester.clientdb.ClientDBCredentialsEntity;
import com.project.apidbtester.testapis.constants.Constants;

import java.sql.*;

public class ClientDBData {

    public static String getPrimaryKey(String tableName, ClientDBCredentialsEntity clientDBCredentials) {
        try (Connection connection = DriverManager.getConnection(clientDBCredentials.getDatabaseUrl(),
                clientDBCredentials.getUserName(),
                clientDBCredentials.getPassword())) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getPrimaryKeys(null, null, tableName);

            String primaryKey = "";
            while (resultSet.next()) {
                primaryKey = resultSet.getString(Constants.COLUMN_NAME);
            }
            return primaryKey;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
