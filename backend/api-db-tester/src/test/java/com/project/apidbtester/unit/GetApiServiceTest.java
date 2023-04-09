package com.project.apidbtester.unit;

import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.testapis.dtos.ColumnResult;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.ColumnValueRepository;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.services.GetApiService;
import com.project.apidbtester.utils.TestRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetApiServiceTest {

    @InjectMocks
    private GetApiService getApiService;

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

        JSONObject mockJsonObject = mock(JSONObject.class);
//        String jsonString = mockJsonObject.toString();
        when(response.asString()).thenReturn("{\"first_name\":\"shubham\"}");
//        when(mockJsonObject.get(anyString())).thenReturn("shubham");

        // Act
        TestResponse testResponse = getApiService.fetchTestResult(testInput);

        // Assert
        assertTrue(testResponse.getAllTestPassed());
        verify(testCaseDetailsRepository, times(1)).save(any());
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

        when(response.asString()).thenReturn("{\"first_name\":\"saif\"}");

        // Act
        TestResponse testResponse = getApiService.fetchTestResult(testInput);

        // Assert
        assertFalse(testResponse.getAllTestPassed());
        verify(testCaseDetailsRepository, times(1)).save(any());
        verify(columnValueRepository, times(1)).save(any());
    }

    @Test
    void testFetchTestResult_ConnectException() {
        // Arrange
        TestInput testInput = new TestInput();
        testInput.setTestCaseDetails(testCaseDetails);
        doReturn(null).when(testRequest).sendRequest(testCaseDetails);

        // Act
        TestResponse testResponse = getApiService.fetchTestResult(testInput);

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
        TestResponse testResponse = getApiService.fetchTestResult(testInput);

        // Assert
        assertEquals(testResponse.getHttpErrorMsg(), HttpStatus.NOT_FOUND.toString());
//        assertFalse(testResponse.getAllTestPassed());
    }
}


