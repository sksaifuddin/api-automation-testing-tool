package com.project.apidbtester.testapis.services;

import com.project.apidbtester.testapis.constants.Constants;
import com.project.apidbtester.testapis.dtos.ColumnResult;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.repositories.ColumnValueRepository;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.utils.TestRequest;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;

import io.restassured.response.Response;

/**
 * GetApiService is used to test get api request
 */
@Service
public class GetApiService {

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    @Autowired
    private ColumnValueRepository columnValueRepository;

    @Autowired
    private ModelMapper modelMapper;

    private TestRequest testRequest = new TestRequest(); // used to forward the api to the client app

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
            // parse response received from client app
            JSONObject jsonObject = new JSONObject(r.asString());

            boolean allTestPassed = true;

            // verify each column value
            for (TestColumnValue columnValue : testColumnValues) {
                if (columnValue.getExpectedValue().toString()
                        .equals(jsonObject.get(columnValue.getColumnName()).toString())) {
                    columnValue.setPassed(true);
                } else {
                    allTestPassed = false;
                }
                columnValue.setActualValue(jsonObject.get(columnValue.getColumnName()).toString());
            }

            // set if all tests passed
            if (allTestPassed) {
                testCaseDetails.setPassed(true);
                testResponse.setAllTestPassed(true);
            }

            // save test details to db and return test response
            TestCaseDetails testCaseDetailsSaved = testCaseDetailsRepository.save(testCaseDetails);

            for (TestColumnValue testColumnValue : testColumnValues) {
                testColumnValue.setTestCaseDetails(testCaseDetailsSaved);
                columnValueRepository.save(testColumnValue);
            }
            testResponse.setColumnValues(Arrays.asList(modelMapper.map(testColumnValues, ColumnResult[].class)));

            return testResponse;
        } catch (Exception e) {
            // handle exceptions
            testResponse.setHttpStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
            testResponse.setHttpErrorMsg(Constants.UNABLE_TO_CONNECT_CLIENT);
            return testResponse;
        }
    }
}
