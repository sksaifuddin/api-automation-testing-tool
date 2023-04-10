package com.project.apidbtester.testapis.services;

import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.clientdb.exceptions.ClientDBConnectionException;
import com.project.apidbtester.clientdb.exceptions.ClientDBCredentialsNotFoundException;
import com.project.apidbtester.testapis.constants.Constants;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.utils.Query;
import com.project.apidbtester.utils.TestRequest;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * DeleteApiService is used to test delete api request
 */
@Service
public class DeleteApiService {

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    @Autowired
    private ClientDBInfoService clientDBInfoService;

    private TestRequest testRequest = new TestRequest(); // used to forward the api to the client app


    public TestResponse fetchTestResult(TestInput testInput) {

        TestCaseDetails testCaseDetails = testInput.getTestCaseDetails();
        testCaseDetails.setPayload("");
        TestResponse testResponse = new TestResponse();

        // try forwarding the api to client app
        try {
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
            // verify results by checking from db directly

            Connection connection = clientDBInfoService.getClientDBCConnection();

            Statement statement = connection.createStatement();

            String query = Query.generateCountQueryWithWhereClause(testCaseDetails);

            ResultSet result = statement.executeQuery(query);
            result.next();
            boolean testPassed = false;

            // check the row that has been deleted using delete api
            if (result.getInt("count(*)")==0) {
                // deleted row does not exist
                testPassed = true;
            }

            // save test details and send back the response to the tester

            testResponse.setAllTestPassed(testPassed);
            testCaseDetails.setPassed(testPassed);

            connection.close();
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
}
