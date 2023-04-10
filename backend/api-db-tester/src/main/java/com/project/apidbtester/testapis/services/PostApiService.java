package com.project.apidbtester.testapis.services;

import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.clientdb.exceptions.ClientDBConnectionException;
import com.project.apidbtester.clientdb.exceptions.ClientDBCredentialsNotFoundException;
import com.project.apidbtester.testapis.constants.Constants;
import com.project.apidbtester.testapis.dtos.ColumnResult;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.repositories.ColumnValueRepository;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.utils.ClientDBData;
import com.project.apidbtester.utils.Query;
import com.project.apidbtester.utils.TestRequest;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.restassured.response.Response;

/**
 * PostApiService is used to test post api request
 */
@Service
public class PostApiService {

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    @Autowired
    private ColumnValueRepository columnValueRepository;

    @Autowired
    private ClientDBInfoService clientDBInfoService;

    @Autowired
    private ModelMapper modelMapper;

    private TestRequest testRequest = new TestRequest(); // used to forward the api to the client app
    private ClientDBData clientDBData = new ClientDBData();

    public TestResponse fetchTestResult(TestInput testInput) {
        TestCaseDetails testCaseDetails = testInput.getTestCaseDetails();
        List<TestColumnValue> testColumnValues = testInput.getColumnValues();
        TestResponse testResponse = new TestResponse();

        try {
            // forward the api to client app
            Response r = testRequest.sendRequest(testCaseDetails);
            if (r == null) throw new ConnectException();

            testResponse.setHttpStatusCode(r.statusCode());
            testCaseDetails.setHttpStatusCode(r.statusCode());

            // error response from client app
            if (r.statusCode() != HttpStatus.OK.value()) {
                testResponse.setHttpErrorMsg(r.statusLine());
                testCaseDetails.setPassed(false);
                testCaseDetails.setHttpErrorMsg(r.statusLine());
                testCaseDetailsRepository.save(testCaseDetails);
                return testResponse;
            }

            // success response from client app
            // make connection to client db and get primary key name and value

            Connection connection = clientDBInfoService.getClientDBCConnection();
            String tableName = testCaseDetails.getTableName();
            JSONObject jsonObject = new JSONObject(r.asString());
            String primaryKeyName = clientDBData.getPrimaryKey(tableName, connection);
            String primaryKeyValue = String.valueOf(jsonObject.get(primaryKeyName));

            // set primary key name and value
            testCaseDetails.setPrimaryKeyName(primaryKeyName);
            testCaseDetails.setPrimaryKeyValue(primaryKeyValue);

            // verify results by checking from db directly
            List<TestColumnValue> testColumnValuesWithResults = getTestColumnValuesWithResults(connection, testColumnValues, testCaseDetails);
            connection.close();

            // save test details to db and return test response
            saveTestColumnValues(testColumnValuesWithResults, testCaseDetails);

            testResponse.setColumnValues(Arrays.asList(modelMapper.map(testColumnValuesWithResults, ColumnResult[].class)));
            testResponse.setAllTestPassed(allTestPassed(testColumnValuesWithResults));
            testCaseDetails.setPassed(testResponse.getAllTestPassed());
            testCaseDetailsRepository.save(testCaseDetails);

            return testResponse;
        } catch (Exception e) {
            // handle exceptions
            if (e instanceof ConnectException) {
                testResponse.setHttpStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
                testResponse.setHttpErrorMsg(Constants.UNABLE_TO_CONNECT_CLIENT);
                return testResponse;
            } else if (e instanceof ClientDBCredentialsNotFoundException) {
                throw new ClientDBCredentialsNotFoundException();
            }
            throw new ClientDBConnectionException();
        }
    }

    /**
     * get the list of results of test performed on db
     * @param connection connection to client db
     * @param testColumnValues columns to be tested
     * @param testCaseDetails details of test to which testColumnValues is related
     * @return list of column test results
     * @throws SQLException
     */
    private List<TestColumnValue> getTestColumnValuesWithResults(Connection connection, List<TestColumnValue> testColumnValues, TestCaseDetails testCaseDetails) throws SQLException {
        Statement statement = connection.createStatement();
        String query = Query.generateSelectQueryWithWhereClause(testColumnValues, testCaseDetails);
        ResultSet result = statement.executeQuery(query);

        List<TestColumnValue> testColumnValuesWithResults = new ArrayList<>();
        boolean allTestPassed = true;

        // verify each column value one by one
        while (result.next()) {
            for (TestColumnValue testColumnValue : testColumnValues) {
                if (testColumnValue.getExpectedValue()
                        .equals(result.getString(testColumnValue.getColumnName()))) {
                    testColumnValue.setPassed(true);
                } else {
                    allTestPassed = false;
                }
                testColumnValue.setActualValue(result.getString(testColumnValue.getColumnName()));
                testColumnValuesWithResults.add(testColumnValue);
            }
        }

        if (allTestPassed) {
            testCaseDetails.setPassed(true);
        }

        return testColumnValuesWithResults;
    }

    /**
     * save the result of tests performed on columns to db
     * @param testColumnValues list of test results performed on columns
     * @param testCaseDetails the detail of the test to which testColumnValues list is linked
     */
    private void saveTestColumnValues(List<TestColumnValue> testColumnValues, TestCaseDetails testCaseDetails) {
        TestCaseDetails testCaseDetailsSaved = testCaseDetailsRepository.save(testCaseDetails);
        for (TestColumnValue testColumnValue : testColumnValues) {
            testColumnValue.setTestCaseDetails(testCaseDetailsSaved);
            columnValueRepository.save(testColumnValue);
        }
    }

    /**
     * check if all tests passed
     * @param testColumnValues list of columns values
     * @return true if all tests passed, else false
     */
    private boolean allTestPassed(List<TestColumnValue> testColumnValues) {
        for (TestColumnValue testColumnValue : testColumnValues) {
            if (!testColumnValue.getPassed()) return false;
        }
        return true;
    }
}