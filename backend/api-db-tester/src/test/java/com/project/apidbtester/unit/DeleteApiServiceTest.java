package com.project.apidbtester.unit;

import com.project.apidbtester.clientdb.ClientDBInfoRepository;
import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.clientdb.exceptions.ClientDBConnectionException;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.ColumnValueRepository;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.services.DeleteApiService;
import com.project.apidbtester.utils.TestRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteApiServiceTest {

    @InjectMocks
    private DeleteApiService deleteApiService;

    @Mock
    ClientDBInfoRepository clientDBInfoRepository;

    @Mock
    private TestCaseDetailsRepository testCaseDetailsRepository;

    @Mock
    private ColumnValueRepository columnValueRepository;

    @Mock
    private ClientDBInfoService clientDBInfoService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Response response;

    @Mock
    TestCaseDetails testCaseDetails;

    @Mock
    TestRequest testRequest;

    @Test
    void testFetchTestResult_TestPassed() throws Exception {
        // Arrange
        TestInput testInput = new TestInput();
        testInput.setTestCaseDetails(testCaseDetails);

        doReturn(HttpStatus.OK.value()).when(response).statusCode();
        doReturn(response).when(testRequest).sendRequest(testCaseDetails);
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(clientDBInfoService.getClientDBCConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("count(*)")).thenReturn(0);

        // Act
        TestResponse testResponse = deleteApiService.fetchTestResult(testInput);

        // Assert
        assertTrue(testResponse.getAllTestPassed());
        verify(testCaseDetailsRepository, times(1)).save(any());
    }

    @Test
    void testFetchTestResult_TestFailed() throws Exception {
        // Arrange
        TestInput testInput = new TestInput();
        testInput.setTestCaseDetails(testCaseDetails);

        doReturn(HttpStatus.OK.value()).when(response).statusCode();
        doReturn(response).when(testRequest).sendRequest(testCaseDetails);
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(clientDBInfoService.getClientDBCConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("count(*)")).thenReturn(1);

        // Act
        TestResponse testResponse = deleteApiService.fetchTestResult(testInput);

        // Assert
        assertFalse(testResponse.getAllTestPassed());
        verify(testCaseDetailsRepository, times(1)).save(any());
    }

    @Test
    void testFetchTestResult_ConnectException() {
        // Arrange
        TestInput testInput = new TestInput();
        testInput.setTestCaseDetails(testCaseDetails);
        doReturn(null).when(testRequest).sendRequest(testCaseDetails);

        // Act
        TestResponse testResponse = deleteApiService.fetchTestResult(testInput);

        // Assert
        assertEquals(testResponse.getHttpStatusCode(), HttpStatus.SERVICE_UNAVAILABLE.value());
//        assertFalse(testResponse.getAllTestPassed());
    }

    @Test
    void testFetchTestResult_ClientAPIErrorResponse() throws Exception {
        // Arrange
        TestInput testInput = new TestInput();
        testInput.setTestCaseDetails(testCaseDetails);

        doReturn(HttpStatus.NOT_FOUND.value()).when(response).statusCode();
        doReturn(HttpStatus.NOT_FOUND.toString()).when(response).statusLine();
        doReturn(response).when(testRequest).sendRequest(testCaseDetails);

        // Act
        TestResponse testResponse = deleteApiService.fetchTestResult(testInput);

        // Assert
        assertEquals(testResponse.getHttpErrorMsg(), HttpStatus.NOT_FOUND.toString());
//        assertFalse(testResponse.getAllTestPassed());
    }

    @Test
    void testFetchTestResult_ClientDBConnectionException() throws Exception {
        // Arrange
        TestInput testInput = new TestInput();
        testInput.setTestCaseDetails(testCaseDetails);

        doReturn(HttpStatus.OK.value()).when(response).statusCode();
        doReturn(response).when(testRequest).sendRequest(testCaseDetails);
        Connection connection = mock(Connection.class);
        when(clientDBInfoService.getClientDBCConnection()).thenReturn(null);

        // Act and Assert
        assertThrows(ClientDBConnectionException.class, () -> deleteApiService.fetchTestResult(testInput));
    }

}


