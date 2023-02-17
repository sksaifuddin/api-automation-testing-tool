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
    public Map<String, List<String>> fetchClientDBSchema(ClientDBDetails clientDBDetails) throws ClientDBConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(clientDBDetails.getDatabaseUrl(), clientDBDetails.getUserName(), clientDBDetails.getPassword());

            Statement statement = connection.createStatement();
//            DatabaseMetaData databaseMetaData = connection.getMetaData();
//            ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"SYSTEM TABLE"});

            ResultSet resultSet = statement.executeQuery("show tables");
                    List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                if (tableName.contains("_seq")==false) {
                    tables.add(tableName);
                }
            }
            Map<String, List<String>> map = new HashMap<>();
            map.put("1", tables);
            return map;
        } catch (Exception e) {
            throw new ClientDBConnectionException("Database connection Failed, please check the details again");
        }
    }
}
