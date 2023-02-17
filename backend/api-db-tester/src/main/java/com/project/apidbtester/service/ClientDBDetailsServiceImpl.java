package com.project.apidbtester.service;

import com.project.apidbtester.error.ClientDBConnectionException;
import com.project.apidbtester.model.ClientDBDetails;

import com.project.apidbtester.model.CustomApiResponseBody;
import com.project.apidbtester.repository.ClientDBDetailsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientDBDetailsServiceImpl implements ClientDBDetailsService {
    @Autowired
    private ClientDBDetailsRepository clientDBDetailsRepository;

    @Override
    public CustomApiResponseBody testClientDBConnection(ClientDBDetails clientDBDetails) throws ClientDBConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection(clientDBDetails.getDatabaseUrl(), clientDBDetails.getUserName(), clientDBDetails.getPassword());
            CustomApiResponseBody successResponse = new CustomApiResponseBody(HttpStatus.valueOf(200), "DB Connection Successful", 200);
            return successResponse;
        } catch (Exception e) {
            throw new ClientDBConnectionException("Database connection Failed, please check the details again");
        }
    }

    @Override
    public Map<String, Map<String, List<String>>> fetchClientDBSchema(ClientDBDetails clientDBDetails) throws ClientDBConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(clientDBDetails.getDatabaseUrl(), clientDBDetails.getUserName(), clientDBDetails.getPassword());

            Statement statement = connection.createStatement();
//            DatabaseMetaData databaseMetaData = connection.getMetaData();
//            ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"SYSTEM TABLE"});

//            ClientDBSchema clientDBSchema = ne
            Map<String, Map<String, List<String>>> map = new HashMap<>();

            ResultSet resultSetTables = statement.executeQuery("show tables");
//                    List<String> tables = new ArrayList<>();
            while (resultSetTables.next()) {
                String tableName = resultSetTables.getString(1);
                if (tableName.contains("_seq")==false) {
                    Statement statementCols = connection.createStatement();
                    ResultSet resultSetColumns = statementCols.executeQuery("select * from "+tableName+";");
                    ResultSetMetaData resultSetMetaDataCols = resultSetColumns.getMetaData();
                    int colCount = resultSetMetaDataCols.getColumnCount();
                    Map<String, List<String>> tableMap= new HashMap<>();
                    tableMap.put("columns", new ArrayList<>());
                    for (int i = 1; i <= colCount; i++) {
                        tableMap.get("columns").add(resultSetMetaDataCols.getColumnName(i));
                    }
                    map.put(tableName, tableMap);
                    resultSetColumns.close();
                    statementCols.close();
                }
            }
            resultSetTables.close();
            statement.close();
            connection.close();
            return map;
        } catch (Exception e) {
            throw new ClientDBConnectionException("Database connection Failed, please check the details again");
        }
    }
}
