package com.project.apidbtester.testapis.services;

import com.project.apidbtester.clientdb.ClientDBInfoRepository;
import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.testapis.constants.Constants;
import com.project.apidbtester.testapis.dtos.ColumnResult;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.repositories.ColumnValueRepository;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.utils.Query;
import com.project.apidbtester.utils.TestRequest;
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

@Service
public class PutApiService {

    @Autowired
    ClientDBInfoRepository clientDBInfoRepository;

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    @Autowired
    private ColumnValueRepository columnValueRepository;

    @Autowired
    private ClientDBInfoService clientDBInfoService;

    @Autowired
    private ModelMapper modelMapper;

    public TestResponse fetchTestResult(TestInput testInput) {

        TestCaseDetails testCaseDetails = testInput.getTestCaseDetails();
        List<TestColumnValue> testColumnValues = testInput.getColumnValues();
        TestResponse testResponse = new TestResponse();

        try {
            Response r = TestRequest.sendRequest(testCaseDetails);
            if (r == null) throw new ConnectException();

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

            Connection connection = clientDBInfoService.getClientDBCConnection();

            Statement statement = connection.createStatement();

            String query = Query.generateSelectQueryWithWhereClause(testColumnValues, testCaseDetails);

            ResultSet result = statement.executeQuery(query);

            boolean allTestPassed = true;

            while (result.next()) {
                for (TestColumnValue testColumnValue : testColumnValues) {
                    if (testColumnValue.getExpectedValue()
                            .equals(result.getString(testColumnValue.getColumnName()))) {
                        testColumnValue.setPassed(true);
                    } else {
                        allTestPassed = false;
                    }
                    testColumnValue.setActualValue(result.getString(testColumnValue.getColumnName()));
                }
            }
            connection.close();

            if (allTestPassed) {
                testCaseDetails.setPassed(true);
                testResponse.setAllTestPassed(true);
            }
            TestCaseDetails testCaseDetailsSaved = testCaseDetailsRepository.save(testCaseDetails);

            for (TestColumnValue testColumnValue : testColumnValues) {
                testColumnValue.setTestCaseDetails(testCaseDetailsSaved);
                columnValueRepository.save(testColumnValue);
            }
            testResponse.setColumnValues(Arrays.asList(modelMapper.map(testColumnValues, ColumnResult[].class)));
            return testResponse;
        } catch (Exception e) {
            if (e instanceof ConnectException) {
                testResponse.setHttpStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
                testResponse.setHttpErrorMsg(Constants.UNABLE_TO_CONNECT_CLIENT);
                return testResponse;
            } else if (e instanceof ClientDBInfoService.ClientDBCredentialsNotFoundException) {
                throw new ClientDBInfoService.ClientDBCredentialsNotFoundException();
            }
            throw new ClientDBInfoService.ClientDBConnectionException();
        }
    }
}
