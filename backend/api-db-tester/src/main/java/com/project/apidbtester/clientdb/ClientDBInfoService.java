package com.project.apidbtester.clientdb;

import com.project.apidbtester.clientdb.constants.Constants;
import com.project.apidbtester.clientdb.dtos.ClientDBMetaData;
import com.project.apidbtester.constants.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.*;

@Service
public class ClientDBInfoService {

    @Autowired
    private ClientDBInfoRepository clientDBInfoRepository;

    public String  testClientDBConnection(ClientDBCredentialsEntity clientDBCredentialsEntity) {
        try {
            Class.forName(GlobalConstants.JDBC_DRIVER);
            DriverManager.getConnection(clientDBCredentialsEntity.getDatabaseUrl(), clientDBCredentialsEntity.getUserName(), clientDBCredentialsEntity.getPassword());
            clientDBCredentialsEntity.setDatabaseId(GlobalConstants.DB_CREDENTIALS_ID);
            clientDBInfoRepository.save(clientDBCredentialsEntity);
            return Constants.CLIENT_DB_CONNECTION_SUCCESSFUL;
        } catch (Exception e) {
            throw new ClientDBConnectionException();
        }
    }

    public ClientDBCredentialsEntity getClientDBCredentials() throws ClientDBCredentialsNotFoundException {
        return clientDBInfoRepository.findById(GlobalConstants.DB_CREDENTIALS_ID).orElseThrow(()-> new ClientDBCredentialsNotFoundException());
    }

    public Map<String, ClientDBMetaData> fetchClientDBMetaData() {
        try {
            Connection connection = getClientDBCConnection();
            Statement statement = connection.createStatement();

            Map<String, ClientDBMetaData> map = new HashMap<>();

            DatabaseMetaData meta = connection.getMetaData();

            ResultSet tables = meta.getTables(null, null, "%", new String[] { Constants.TABLE });

            while (tables.next()) {
                List<String> primaryKeys = new ArrayList<>();

                String catalog = tables.getString(Constants.TABLE_CAT);
                String schema = tables.getString(Constants.TABLE_SCHEM);
                String tableName = tables.getString(Constants.TABLE_NAME);

                if (!tableName.contains(Constants.SEQ)) {

                    try (ResultSet keys = meta.getPrimaryKeys(catalog, schema, tableName)) {
                        while (keys.next()) {
                            primaryKeys.add(keys.getString(Constants.COLUMN_NAME));
                        }
                    }

                    Statement statementCols = connection.createStatement();
                    ResultSet resultSetColumns = statementCols.executeQuery("select * from "+tableName+";");
                    ResultSetMetaData resultSetMetaDataCols = resultSetColumns.getMetaData();
                    int colCount = resultSetMetaDataCols.getColumnCount();
                    List<Map<String, String>> columnsList= new ArrayList<>();
                    for (int i = 1; i <= colCount; i++) {
                        Map<String, String > colNameDataTypeMap = new HashMap<>();
                        colNameDataTypeMap.put(resultSetMetaDataCols.getColumnName(i), resultSetMetaDataCols.getColumnTypeName(i));
                        columnsList.add(colNameDataTypeMap);
                    }
                    map.put(tableName, new ClientDBMetaData(primaryKeys, columnsList));
                    resultSetColumns.close();
                    statementCols.close();
                }
            }
            statement.close();
            connection.close();
            return map;
        } catch (Exception e) {
            throw new ClientDBConnectionException();
        }
    }

    public Connection getClientDBCConnection() {
        try {
            ClientDBCredentialsEntity clientDBCredentials = clientDBInfoRepository.findById(GlobalConstants.DB_CREDENTIALS_ID).orElseThrow(() -> new ClientDBConnectionException());
            Class.forName(GlobalConstants.JDBC_DRIVER);
            return DriverManager.getConnection(clientDBCredentials.getDatabaseUrl(), clientDBCredentials.getUserName(), clientDBCredentials.getPassword());
        } catch (Exception e) {
            throw new ClientDBConnectionException();
        }
    }

    public static class ClientDBConnectionException extends RuntimeException {
        public ClientDBConnectionException() {
            super("Connection to client db failed, please check the details again");
        }
    }

    public static class ClientDBCredentialsNotFoundException extends RuntimeException {
        public ClientDBCredentialsNotFoundException() {
            super("Connection to client db failed, please check the details again");
        }
    }
}
