package com.project.apidbtester.utils;

import com.project.apidbtester.testapis.constants.Constants;

import java.sql.*;

/**
 * ClientDBData clas is used fetch metadata related to the client db
 */
public class ClientDBData {

    /**
     * find the primary key of a table in client db
     * @param tableName name of the table
     * @param connection connection to client db
     * @return name of primary key column
     */
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
