package com.project.apidbtester.testapis.services;

import com.project.apidbtester.clientdbinfo.ClientDBCredentialsEntity;
import com.project.apidbtester.clientdbinfo.ClientDBInfoRepository;
import com.project.apidbtester.constants.GlobalConstants;
import com.project.apidbtester.testapis.dtos.ColumnResult;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.repositories.ColumnValueRepository;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.utils.ClientDBData;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.sql.rowset.CachedRowSet;

@Service
public class PostApiService {

    @Autowired
    ClientDBInfoRepository clientDBInfoRepository;

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    @Autowired
    private ColumnValueRepository columnValueRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TestResponse fetchTestResult(TestInput testInput) {

        TestCaseDetails testCaseDetails = testInput.getTestCaseDetails();
        List<TestColumnValue> testColumnValues = testInput.getColumnValues();
        TestResponse testResponse = new TestResponse();
//        List<ColumnResult> columnResults = modelMapper.map(testColumnValues, ColumnResult.class);

        try {
            RequestSpecification request = RestAssured.given();
            request.contentType(ContentType.JSON);
            request.baseUri(testCaseDetails.getUrl());
            request.body(testCaseDetails.getPayload());
            Response r = request.post();
            testResponse.setHttpStatusCode(r.statusCode());
            testCaseDetails.setHttpStatusCode(r.statusCode());

            if (r.statusCode() != HttpStatus.OK.value()) {
                testResponse.setHttpErrorMsg(r.statusLine());
                testResponse.setHttpErrorMsg(r.body().print());
                testCaseDetails.setPassed(false);
                testCaseDetails.setHttpErrorMsg(r.getBody().print());
                testCaseDetailsRepository.save(testCaseDetails);
                return testResponse;
            }

            ClientDBCredentialsEntity clientDBCredentials = clientDBInfoRepository.findById(GlobalConstants.DB_CREDENTIALS_ID).orElseThrow();

            String tableName = testCaseDetails.getTableName();
            JSONObject jsonObject = new JSONObject(r.asString());

            String primaryKeyName = ClientDBData.getPrimaryKey(tableName, clientDBCredentials);
            String primaryKeyValue = String.valueOf(jsonObject.get(primaryKeyName));

            testCaseDetails.setPrimaryKeyName(primaryKeyName);
            testCaseDetails.setPrimaryKeyValue(primaryKeyValue);

//            Class.forName(GlobalConstants.JDBC_DRIVER);
//            Connection connection = DriverManager
//                    .getConnection(clientDBCredentials.getDatabaseUrl(), clientDBCredentials.getUserName(), clientDBCredentials.getPassword());
//
//            Statement statement = connection.createStatement();
//
//            StringBuilder query = new StringBuilder("select ");
//
//            for (int i = 0; i < testColumnValues.size(); i++) {
//                if (i == testColumnValues.size() - 1) query.append(testColumnValues.get(i).getColumnName());
//                else query.append(testColumnValues.get(i).getColumnName()).append(", ");
//            }
//
//            query.append(" from ")
//                    .append(testCaseDetails.getTableName())
//                    .append(" where ")
//                    .append(testCaseDetails.getPrimaryKeyName())
//                    .append(" = ")
//                    .append(testCaseDetails.getPrimaryKeyValue())
//                    .append(";");
//
//            ResultSet result = statement.executeQuery(String.valueOf(query));

            Class.forName(GlobalConstants.JDBC_DRIVER);

            Connection connection = null;
            connection = DriverManager.getConnection(clientDBCredentials.getDatabaseUrl(), clientDBCredentials.getUserName(), clientDBCredentials.getPassword());

            Statement statement = null;

            statement = connection.createStatement();

            StringBuilder query = new StringBuilder("select ");

            for (int i = 0; i < testColumnValues.size(); i++) {
                if (i == testColumnValues.size() - 1) query.append(testColumnValues.get(i).getColumnName());
                else query.append(testColumnValues.get(i).getColumnName()).append(", ");
            }

            query.append(" from ")
                    .append(testCaseDetails.getTableName())
                    .append(" where ")
                    .append(testCaseDetails.getPrimaryKeyName())
                    .append(" = ")
                    .append(testCaseDetails.getPrimaryKeyValue())
                    .append(";");

            System.out.println(query);
            ResultSet result = statement.executeQuery(query.toString());

//            connection.close();

            boolean allTestPassed = true;

            while (result.next()) {
                for (int i = 0; i < testColumnValues.size(); i++) {
                    if (testColumnValues.get(i).getExpectedValue()
                            .equals(result.getString(testColumnValues.get(i).getColumnName()))) {
                        testColumnValues.get(i).setPassed(true);
                    } else {
                        allTestPassed = false;
                    }
                    testColumnValues.get(i).setActualValue(result.getString(testColumnValues.get(i).getColumnName()));
                }
            }

            System.out.println(testColumnValues);

            if (allTestPassed) {
                testCaseDetails.setPassed(true);
                testResponse.setAllTestPassed(true);
            }
            TestCaseDetails testCaseDetailsSaved = testCaseDetailsRepository.save(testCaseDetails);

            for (TestColumnValue testColumnValue : testColumnValues) {
                testColumnValue.setTestCaseDetails(testCaseDetailsSaved);
                columnValueRepository.save(testColumnValue);
            }
            List<ColumnResult> columnResults = Arrays.asList(modelMapper.map(testColumnValues, ColumnResult[].class));
            testResponse.setColumnValues(Arrays.asList(modelMapper.map(testColumnValues, ColumnResult[].class)));
            connection.close();
            return testResponse;
        } catch (Exception e) {
            if (e instanceof ConnectException) {
                testResponse.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
                testResponse.setHttpErrorMsg("Unable to call api");
//                testCaseDetails.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
//                testCaseDetailsRepository.save(testCaseDetails);
                return testResponse;
            } else {
                testResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                testResponse.setHttpErrorMsg("Database connection Failed, please check the details again");
                return testResponse;
//                testResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                testCaseDetails.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                testCaseDetailsRepository.save(testCaseDetails);
//                return testResponse;
            }
        }
    }
}
