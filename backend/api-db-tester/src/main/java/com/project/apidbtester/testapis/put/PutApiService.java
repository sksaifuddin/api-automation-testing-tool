package com.project.apidbtester.testapis.put;

import com.project.apidbtester.clientdbinfo.ClientDBCredentialsEntity;
import com.project.apidbtester.clientdbinfo.ClientDBInfoRepository;
import com.project.apidbtester.constants.GlobalConstants;
import com.project.apidbtester.repository.ColumnValueRepository;
import com.project.apidbtester.repository.TestCaseDetailsRepository;
import com.project.apidbtester.responses.ClientDBConnectionException;
import com.project.apidbtester.testapis.dtos.ColumnValue;
import com.project.apidbtester.testapis.dtos.TestCaseDetails;
import com.project.apidbtester.testapis.dtos.TestInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Service
public class PutApiService {

    @Autowired
    ClientDBInfoRepository clientDBInfoRepository;

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    @Autowired
    private ColumnValueRepository columnValueRepository;

    public String fetchTestResult(TestInput testInput) throws ClientDBConnectionException {
        try {

            URL url = new URL(testInput.getTestCaseDetails().getUrl());
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod(testInput.getTestCaseDetails().getType().toUpperCase());
            http.setRequestProperty("Content-Type", "application/json");
            http.setDoOutput(true);

            String requestBody = testInput.getTestCaseDetails().getPayload();

            OutputStream outputStream = http.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = http.getResponseCode();
            String responseMessage = http.getResponseMessage();
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Message: " + responseMessage);

//            byte[] out = payload.getBytes(StandardCharsets.UTF_8);

//            OutputStream stream = http.getOutputStream();
//            stream.write(out);

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

            for (int i = 0; i < testInput.getColumnValues().size(); i++) {
                if (i == testInput.getColumnValues().size() - 1) query.append(testInput.getColumnValues().get(i).getColumnName());
                else query.append(testInput.getColumnValues().get(i).getColumnName()).append(", ");
            }

            query.append(" from ")
                    .append(testInput.getTestCaseDetails().getTableName())
                    .append(" where ")
                    .append(testInput.getTestCaseDetails().getPrimaryKeyName())
                    .append(" = ")
                    .append(testInput.getTestCaseDetails().getPrimaryKeyValue())
                    .append(";");

            System.out.println("Query: " + query);
            ResultSet result = statement.executeQuery(String.valueOf(query));

            boolean mismatch = false;
            StringBuilder response = new StringBuilder();

            TestCaseDetails testCaseDetails = testInput.getTestCaseDetails();
            List<ColumnValue> columnValues = testInput.getColumnValues();

            while(result.next()) {
                for (int i = 0; i < testInput.getColumnValues().size(); i++) {
                    if (!testInput.getColumnValues().get(i).getExpectedValue()
                            .equals(result.getString(testInput.getColumnValues().get(i).getColumnName()))) {
                        columnValues.get(i).setPassed(false);
                        mismatch = true;
                    }
                    columnValues.get(i).setActualValue(result.getString(columnValues.get(i).getColumnName()));
//                    columnValues.get(i).setTestCaseDetails(testCaseDetails);
//                        if (!entry.getValue().equals("abc")) mismatch = true;
                    response.append("\nColumn name: ")
                            .append(columnValues.get(i).getColumnName())
                            .append("\tExpected value: ")
                            .append(columnValues.get(i).getExpectedValue())
                            .append("\tActual value: ")
                            .append(result.getString(columnValues.get(i).getColumnName()));
                }
            }
            connection.close();

            TestCaseDetails testCaseDetailsSaved = testCaseDetailsRepository.save(testCaseDetails);

            for (ColumnValue columnValue : columnValues) {
                columnValue.setTestCaseDetails(testCaseDetailsSaved);
                System.out.println(columnValue);
                columnValueRepository.save(columnValue);
            }
//            testCaseDetails.setColumnValues(columnValues);

            System.out.println(testCaseDetailsRepository.findAll());
            System.out.println(columnValueRepository.findAll());

            return mismatch ? "Test Failed: " + response.toString() : "Test Passed: " + response.toString();
        } catch (Exception e) {
            throw new ClientDBConnectionException("Database connection Failed, please check the details again");
        }
    }
}
