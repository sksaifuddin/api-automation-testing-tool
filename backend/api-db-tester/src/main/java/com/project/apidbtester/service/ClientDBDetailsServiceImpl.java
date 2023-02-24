package com.project.apidbtester.service;

import com.project.apidbtester.error.ClientDBConnectionException;
import com.project.apidbtester.model.ClientDBDetails;

import com.project.apidbtester.model.ClientDBSchema;
import com.project.apidbtester.model.CustomApiResponseBody;
import com.project.apidbtester.model.TestDetails;
import com.project.apidbtester.repository.ClientDBDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    public Map<String, ClientDBSchema> fetchClientDBSchema(ClientDBDetails clientDBDetails) throws ClientDBConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(clientDBDetails.getDatabaseUrl(), clientDBDetails.getUserName(), clientDBDetails.getPassword());


            Statement statement = connection.createStatement();
//            DatabaseMetaData databaseMetaData = connection.getMetaData();
//            ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"SYSTEM TABLE"});

//            ClientDBSchema clientDBSchema = ne
            Map<String, ClientDBSchema> map = new HashMap<>();

            DatabaseMetaData meta = connection.getMetaData();

            ResultSet tables = meta.getTables(null, null, "%", new String[] { "TABLE" });

//            ResultSet resultSetTables = statement.executeQuery("show tables");
//                    List<String> tables = new ArrayList<>();


            while (tables.next()) {
//                tables.next();
                List<String> primaryKeys = new ArrayList<>();

                String catalog = tables.getString("TABLE_CAT");
                String schema = tables.getString("TABLE_SCHEM");
                String tableName = tables.getString("TABLE_NAME");

//                try (ResultSet keys = meta.getPrimaryKeys(catalog, schema, tableName)) {
//                    while (keys.next()) {
//                        primaryKeys.add(keys.getString("COLUMN_NAME"));
//                    }
//                }

                if (tableName.contains("_seq")==false) {

                    try (ResultSet keys = meta.getPrimaryKeys(catalog, schema, tableName)) {
                        while (keys.next()) {
                            primaryKeys.add(keys.getString("COLUMN_NAME"));
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
                    map.put(tableName, new ClientDBSchema(primaryKeys, columnsList));
                    resultSetColumns.close();
                    statementCols.close();
                }
            }
//            resultSetTables.close();
            statement.close();
            connection.close();
            return map;
        } catch (Exception e) {
            throw new ClientDBConnectionException("Database connection Failed, please check the details again");
        }
    }

    @Override
    public String fetchTestResult(TestDetails testDetails) throws ClientDBConnectionException {
        try {

            URL url = new URL(testDetails.getUrl());
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod(testDetails.getType().toUpperCase());
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");

            String payload = testDetails.getPayload();
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            http.disconnect();


            return null;
        } catch (Exception e) {
            throw new ClientDBConnectionException("Database connection Failed, please check the details again");
        }
    }
}
