package com.project.apidbtester.unit;

import com.project.apidbtester.clientdb.ClientDBInfoRepository;
import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.clientdb.exceptions.ClientDBConnectionException;
import com.project.apidbtester.testapis.dtos.ColumnResult;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.ColumnValueRepository;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.services.PostApiService;
import com.project.apidbtester.utils.ClientDBData;
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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostApiServiceTest {

    @InjectMocks
    private PostApiService postApiService;

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

    @Mock
    ClientDBData clientDBData;

    @Test
    void testFetchTestResult_AllTestPassed() throws Exception {
        // Arrange
        TestInput testInput = new TestInput();
        testInput.setTestCaseDetails(testCaseDetails);
        TestColumnValue testColumnValue = new TestColumnValue();
        testColumnValue.setColumnName("first_name");
        testColumnValue.setExpectedValue("shubham");
        testInput.setColumnValues(Arrays.asList(testColumnValue));

        doReturn(HttpStatus.OK.value()).when(response).statusCode();
        doReturn(response).when(testRequest).sendRequest(testCaseDetails);
        when(modelMapper.map(eq(Arrays.asList(testColumnValue)), eq(ColumnResult[].class))).thenReturn(new ColumnResult[]{new ColumnResult("first_name", "shubham", "shubham", true)});
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(clientDBInfoService.getClientDBCConnection()).thenReturn(connection);
        when(clientDBData.getPrimaryKey(any(), any())).thenReturn("first_name");
        when(response.asString()).thenReturn("{\"first_name\":\"shubham\"}");
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("first_name")).thenReturn("shubham");

        // Act
        TestResponse testResponse = postApiService.fetchTestResult(testInput);

        // Assert
        assertTrue(testResponse.getAllTestPassed());
        verify(testCaseDetailsRepository, times(2)).save(any());
        verify(columnValueRepository, times(1)).save(any());
    }

    @Test
    void testFetchTestResult_SomeTestsFailed() throws Exception {
        // Arrange
        TestInput testInput = new TestInput();
        testInput.setTestCaseDetails(testCaseDetails);
        TestColumnValue testColumnValue = new TestColumnValue();
        testColumnValue.setColumnName("first_name");
        testColumnValue.setExpectedValue("shubham");
        testInput.setColumnValues(Arrays.asList(testColumnValue));

        doReturn(HttpStatus.OK.value()).when(response).statusCode();
        doReturn(response).when(testRequest).sendRequest(testCaseDetails);
        when(modelMapper.map(eq(Arrays.asList(testColumnValue)), eq(ColumnResult[].class))).thenReturn(new ColumnResult[]{new ColumnResult("first_name", "shubham", "saif", false)});
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(clientDBInfoService.getClientDBCConnection()).thenReturn(connection);
        when(clientDBData.getPrimaryKey(any(), any())).thenReturn("first_name");
        when(response.asString()).thenReturn("{\"first_name\":\"shubham\"}");
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("first_name")).thenReturn("saif");

        // Act
        TestResponse testResponse = postApiService.fetchTestResult(testInput);

        // Assert
        assertFalse(testResponse.getAllTestPassed());
        verify(testCaseDetailsRepository, times(2)).save(any());
        verify(columnValueRepository, times(1)).save(any());
    }

    @Test
    void testFetchTestResult_ConnectException() {
        // Arrange
        TestInput testInput = new TestInput();
        testInput.setTestCaseDetails(testCaseDetails);
        doReturn(null).when(testRequest).sendRequest(testCaseDetails);

        // Act
        TestResponse testResponse = postApiService.fetchTestResult(testInput);

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
        TestResponse testResponse = postApiService.fetchTestResult(testInput);

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
        assertThrows(ClientDBConnectionException.class, () -> postApiService.fetchTestResult(testInput));
    }

}


