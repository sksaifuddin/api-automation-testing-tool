package com.project.apidbtester.testapis.services;

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

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Service
public class GetApiService {

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

            JSONObject jsonObject = new JSONObject(r.asString());

            boolean allTestPassed = true;

            for (TestColumnValue columnValue : testColumnValues) {
                if (columnValue.getExpectedValue().toString()
                        .equals(jsonObject.get(columnValue.getColumnName()).toString())) {
                    columnValue.setPassed(true);
                } else {
                    allTestPassed = false;
                }
                columnValue.setActualValue(jsonObject.get(columnValue.getColumnName()).toString());
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

            return testResponse;
        } catch (Exception e) {
            testResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            testResponse.setHttpErrorMsg("Database connection Failed, please check the details again");
//                testResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                testCaseDetails.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                testCaseDetailsRepository.save(testCaseDetails);
//                return testResponse;
            return testResponse;
        }
    }
}
