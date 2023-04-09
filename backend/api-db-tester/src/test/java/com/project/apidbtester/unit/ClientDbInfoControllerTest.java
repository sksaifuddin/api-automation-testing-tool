package com.project.apidbtester.unit;

import com.project.apidbtester.clientdb.ClientDBCredentialsEntity;
import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.clientdb.ClientDbInfoController;
import com.project.apidbtester.clientdb.dtos.ClientDBMetaData;
import com.project.apidbtester.clientdb.dtos.TestClientConnectionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientDbInfoControllerTest {

    @Mock
    private ClientDBInfoService clientDBInfoService;

    @InjectMocks
    private ClientDbInfoController clientDbInfoController;

    @Test
    public void testClientDBConnection() {
        // Arrange
        ClientDBCredentialsEntity clientDBCredentialsEntity = new ClientDBCredentialsEntity();
        String expectedMessage = "Test connection successful";
        when(clientDBInfoService.testClientDBConnection(clientDBCredentialsEntity)).thenReturn(expectedMessage);

        // Act
        ResponseEntity<TestClientConnectionResponse> responseEntity = clientDbInfoController.testClientDBConnection(clientDBCredentialsEntity);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(expectedMessage, responseEntity.getBody().getMessage());
        verify(clientDBInfoService, times(1)).testClientDBConnection(clientDBCredentialsEntity);
    }

    @Test
    public void testFetchClientDBMetaData() {
        // Arrange
        Map<String, ClientDBMetaData> expectedMetadata = new HashMap<>();
        when(clientDBInfoService.fetchClientDBMetaData()).thenReturn(expectedMetadata);

        // Act
        ResponseEntity<Map<String, ClientDBMetaData>> responseEntity = clientDbInfoController.fetchClientDBMetaData();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(expectedMetadata, responseEntity.getBody());
        verify(clientDBInfoService, times(1)).fetchClientDBMetaData();
    }

    @Test
    public void testGetClientDBCredentials() {
        // Arrange
        ClientDBCredentialsEntity expectedCredentials = new ClientDBCredentialsEntity();
        when(clientDBInfoService.getClientDBCredentials()).thenReturn(expectedCredentials);

        // Act
        ResponseEntity<ClientDBCredentialsEntity> responseEntity = clientDbInfoController.getClientDBCredentials();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(expectedCredentials, responseEntity.getBody());
        verify(clientDBInfoService, times(1)).getClientDBCredentials();
    }

}
