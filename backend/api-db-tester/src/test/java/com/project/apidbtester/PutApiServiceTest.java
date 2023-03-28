package com.project.apidbtester;

import com.project.apidbtester.clientdb.ClientDBInfoRepository;
import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.testapis.dtos.ColumnResult;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.entities.TestColumnValue;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.ColumnValueRepository;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import com.project.apidbtester.testapis.services.PutApiService;
import com.project.apidbtester.utils.TestRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.net.ConnectException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PutApiServiceTest {

	@InjectMocks
	private PutApiService putApiService;

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
	void fetchTestResultAllTestPassed() throws Exception {
		TestInput testInput = new TestInput();
//		TestCaseDetails testCaseDetails = new TestCaseDetails();
		String url = "http://localhost:9191/updateProducer";
//		testCaseDetails.setUrl(url);
//		testCaseDetails.setType("PUT");
//		testCaseDetails.set("{\"Content-type\": \"application/json\"}");
//		String payload = "{\"id\": 358, \"first_name\":\"shubham\",\"last_name\":\"mishra\",\"gender\":\"m\",\"film_count\":25}";
//		testCaseDetails.setPayload(payload);
		testInput.setTestCaseDetails(testCaseDetails);
		TestColumnValue testColumnValue = new TestColumnValue();
		testColumnValue.setColumnName("first_name");
		testColumnValue.setExpectedValue("shubham");
		testInput.setColumnValues(Arrays.asList(testColumnValue));
//		Response response = RestAssured.given()
//				.contentType("application/json")
//				.body(payload)
//				.put(url);
//		when(TestRequest.sendRequest(testCaseDetails)).thenReturn(response);
//		TestRequest
//		Response r =
		doReturn(HttpStatus.OK.value()).when(response).statusCode();
		doReturn(response).when(testRequest).sendRequest(testCaseDetails);

		when(modelMapper.map(eq(Arrays.asList(testColumnValue)), eq(ColumnResult[].class))).thenReturn(new ColumnResult[]{new ColumnResult("first_name", "shubham", "shubham", true)});
		Connection connection = mock(Connection.class);
		Statement statement = mock(Statement.class);
		ResultSet resultSet = mock(ResultSet.class);
		when(clientDBInfoService.getClientDBCConnection()).thenReturn(connection);
		when(connection.createStatement()).thenReturn(statement);
		when(statement.executeQuery(any())).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true, false);
		when(resultSet.getString("first_name")).thenReturn("shubham");
		TestResponse testResponse = putApiService.fetchTestResult(testInput);
		assertTrue(testResponse.getAllTestPassed());
		verify(testCaseDetailsRepository, times(1)).save(any());
		verify(columnValueRepository, times(1)).save(any());
	}

}

