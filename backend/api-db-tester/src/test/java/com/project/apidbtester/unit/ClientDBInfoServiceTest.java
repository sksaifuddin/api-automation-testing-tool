package com.project.apidbtester.unit;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.*;

import com.project.apidbtester.clientdb.ClientDBCredentialsEntity;
import com.project.apidbtester.clientdb.ClientDBInfoRepository;
import com.project.apidbtester.clientdb.ClientDBInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.project.apidbtester.clientdb.constants.Constants;
import com.project.apidbtester.clientdb.exceptions.ClientDBConnectionException;
import com.project.apidbtester.clientdb.exceptions.ClientDBCredentialsNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ClientDBInfoServiceTest {

    @Mock
    private ClientDBInfoRepository clientDBInfoRepository;

    @InjectMocks
    private ClientDBInfoService clientDBInfoService;

    @Before
    public void setUp() {
        ClientDBCredentialsEntity credentials = new ClientDBCredentialsEntity();
        credentials.setDatabaseId(Constants.DB_CREDENTIALS_ID);
        credentials.setDatabaseUrl("jdbc:mysql://localhost:3306/mydb");
        credentials.setUserName("user");
        credentials.setPassword("password");
        when(clientDBInfoRepository.findById(Constants.DB_CREDENTIALS_ID)).thenReturn(java.util.Optional.of(credentials));
    }

    @Test(expected = ClientDBCredentialsNotFoundException.class)
    public void testGetClientDBCredentialsNotFound() {
        // Arrange
        when(clientDBInfoRepository.findById(Constants.DB_CREDENTIALS_ID)).thenReturn(java.util.Optional.empty());

        // Act
        clientDBInfoService.getClientDBCredentials();
    }

    @Test
    public void testGetClientDBCredentials() {
        // Act
        ClientDBCredentialsEntity credentials = clientDBInfoService.getClientDBCredentials();
//        assertEquals(Constants.DB_CREDENTIALS_ID, credentials.getDatabaseId());

        // Assert
        assertEquals("jdbc:mysql://localhost:3306/mydb", credentials.getDatabaseUrl());
//        assertEquals("user", credentials.getUserName());
//        assertEquals("password", credentials.getPassword());
    }

    @Test(expected = ClientDBConnectionException.class)
    public void testFetchClientDBMetaDataConnectionException() throws Exception {
        // Arrange
        when(clientDBInfoRepository.findById(Constants.DB_CREDENTIALS_ID)).thenReturn(java.util.Optional.empty());

        // Act
        clientDBInfoService.fetchClientDBMetaData();
    }
}
