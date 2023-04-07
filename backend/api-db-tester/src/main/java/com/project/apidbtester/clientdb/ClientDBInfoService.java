package com.project.apidbtester.clientdb;

import com.project.apidbtester.clientdb.constants.Constants;
import com.project.apidbtester.clientdb.dtos.ClientDBMetaData;
import com.project.apidbtester.utils.Query;
import com.project.apidbtester.clientdb.exceptions.ClientDBConnectionException;
import com.project.apidbtester.clientdb.exceptions.ClientDBCredentialsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.*;

@Service
public class ClientDBInfoService {

    @Autowired
    private ClientDBInfoRepository clientDBInfoRepository;

    public String testClientDBConnection(ClientDBCredentialsEntity clientDBCredentialsEntity) {
        try {
            String dbUrl = clientDBCredentialsEntity.getDatabaseUrl();
            String userName = clientDBCredentialsEntity.getUserName();
            String password = clientDBCredentialsEntity.getPassword();

            Class.forName(Constants.JDBC_DRIVER);
            DriverManager.getConnection(dbUrl, userName, password);
            clientDBCredentialsEntity.setDatabaseId(Constants.DB_CREDENTIALS_ID);
            clientDBInfoRepository.save(clientDBCredentialsEntity);
            return Constants.CLIENT_DB_CONNECTION_SUCCESSFUL;
        } catch (Exception e) {
            throw new ClientDBConnectionException();
        }
    }

    public ClientDBCredentialsEntity getClientDBCredentials() {
        return clientDBInfoRepository.findById(Constants.DB_CREDENTIALS_ID).orElseThrow(()-> new ClientDBCredentialsNotFoundException());
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
                    String query = Query.generateSelectQuery(tableName);
                    ResultSet resultSetColumns = statementCols.executeQuery(query);
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
            ClientDBCredentialsEntity clientDBCredentials = clientDBInfoRepository.findById(Constants.DB_CREDENTIALS_ID).orElseThrow(() -> new ClientDBCredentialsNotFoundException());
            Class.forName(Constants.JDBC_DRIVER);

            String dbUrl = clientDBCredentials.getDatabaseUrl();
            String userName = clientDBCredentials.getUserName();
            String password = clientDBCredentials.getPassword();

            return DriverManager.getConnection(dbUrl, userName, password);
        } catch (Exception e) {
            if (e instanceof ClientDBCredentialsNotFoundException) {
                throw new ClientDBCredentialsNotFoundException();
            }
            throw new ClientDBConnectionException();
        }
    }
}

