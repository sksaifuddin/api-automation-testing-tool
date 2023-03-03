package com.project.apidbtester.testapis.put;

import com.project.apidbtester.clientdbinfo.ClientDBCredentialsEntity;
import com.project.apidbtester.clientdbinfo.ClientDBInfoRepository;
import com.project.apidbtester.clientdbinfo.ClientDBInfoService;
import com.project.apidbtester.constants.GlobalConstants;
import com.project.apidbtester.responses.ClientDBConnectionException;
import com.project.apidbtester.testapis.dtos.TestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PutApiService {

    @Autowired
    ClientDBInfoRepository clientDBInfoRepository;

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

            http.disconnect();

            Optional<ClientDBCredentialsEntity> clientDBCredentials = clientDBInfoRepository.findById(GlobalConstants.DB_CREDENTIALS_ID);

            if (clientDBCredentials==null) {
                throw new ClientDBConnectionException("Client db credentials not found");
            }
            
            Class.forName(GlobalConstants.JDBC_DRIVER);
            Connection connection = DriverManager
                    .getConnection(clientDBCredentials.get().getDatabaseUrl(),clientDBCredentials.get().getUserName(),clientDBCredentials.get().getPassword());

            Statement statement = connection.createStatement();

            StringBuilder query = new StringBuilder("select ");

            for (int i = 0; i < testDetails.getColsValsList().size(); i++) {
                for (String key : testDetails.getColsValsList().get(i).keySet()) {
                    if (i == testDetails.getColsValsList().size() - 1) query.append(key);
                    else query.append(key ).append(", ");
                }
            }

            query.append(" from ").append(testDetails.getTableName()).append(" where ");

            for (int i = 0; i < testDetails.getPrimaryKey().size(); i++) {
                for (String key : testDetails.getPrimaryKey().get(i).keySet()) {
                    if (i == 0) query.append(key).append(" = ").append(testDetails.getPrimaryKey().get(i).get(key));
                    else query.append(" and ").append(key).append(" = ").append(testDetails.getPrimaryKey().get(i).get(key));
                }
            }

            query.append(";");

            System.out.println("Query: " + query);
            ResultSet result = statement.executeQuery(String.valueOf(query));

            boolean mismatch = false;
            StringBuilder response = new StringBuilder();

            while(result.next()) {
                for (int i = 0; i < testDetails.getColsValsList().size(); i++) {
                    for (Map.Entry<String,String> entry : testDetails.getColsValsList().get(i).entrySet()){
                        System.out.println(result.getString(entry.getKey()));
                        if (!entry.getValue().equals(result.getString(entry.getKey()))) mismatch = false;
//                        if (!entry.getValue().equals("abc")) mismatch = true;
                        response.append("\nColumn name: ")
                                .append(entry.getKey())
                                .append("\tExpected value: ")
                                .append(entry.getValue())
                                .append("\tActual value: ")
                                .append(result.getString(entry.getKey()));
                    }
                }
            }
            connection.close();
            return mismatch ? "Test Failed: " + response.toString() : "Test Passed: " + response.toString();
        } catch (Exception e) {
            throw new ClientDBConnectionException("Database connection Failed, please check the details again");
        }
    }
}
