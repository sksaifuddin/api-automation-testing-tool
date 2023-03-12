package com.project.apidbtester.clientdbinfo;

import com.project.apidbtester.clientdbinfo.constants.Constants;
import com.project.apidbtester.clientdbinfo.dtos.ClientDBMetaData;
import com.project.apidbtester.constants.GlobalConstants;
import com.project.apidbtester.responses.ClientDBConnectionException;

import com.project.apidbtester.responses.dtos.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class ClientDBInfoService {

    @Autowired
    private ClientDBInfoRepository clientDBInfoRepository;

    public ApiResponse testClientDBConnection(ClientDBCredentialsEntity clientDBCredentialsEntity) throws ClientDBConnectionException {
        try {
            Class.forName(GlobalConstants.JDBC_DRIVER);
            DriverManager.getConnection(clientDBCredentialsEntity.getDatabaseUrl(), clientDBCredentialsEntity.getUserName(), clientDBCredentialsEntity.getPassword());
            clientDBCredentialsEntity.setDatabaseId(GlobalConstants.DB_CREDENTIALS_ID);
            clientDBInfoRepository.save(clientDBCredentialsEntity);
            ApiResponse successResponse = new ApiResponse(HttpStatus.valueOf(200), "DB Connection Successful", 200);
            return successResponse;
        } catch (Exception e) {
            throw new ClientDBConnectionException();
        }
    }

    public List<ClientDBCredentialsEntity> fetchClientDBCredentials() throws ClientDBConnectionException {
       return clientDBInfoRepository.findAll();
    }

    public Map<String, ClientDBMetaData> fetchClientDBMetaData(ClientDBCredentialsEntity clientDBCredentialsEntity) throws ClientDBConnectionException {
        try {
            Class.forName(GlobalConstants.JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(clientDBCredentialsEntity.getDatabaseUrl(), clientDBCredentialsEntity.getUserName(), clientDBCredentialsEntity.getPassword());
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

}
