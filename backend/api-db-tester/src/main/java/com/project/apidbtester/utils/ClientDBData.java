package com.project.apidbtester.utils;

import com.project.apidbtester.testapis.constants.Constants;

import java.sql.*;

public class ClientDBData {

    public String getPrimaryKey(String tableName, Connection connection) {
        try {
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
