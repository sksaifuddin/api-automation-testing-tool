package com.project.apidbtester.service;

import com.project.apidbtester.error.ClientDBConnectionException;
import com.project.apidbtester.model.ClientDBDetails;

import com.project.apidbtester.model.ClientDBSchema;
import com.project.apidbtester.model.CustomApiResponseBody;

import java.util.Map;

public interface ClientDBDetailsService {
    public CustomApiResponseBody testClientDBConnection(ClientDBDetails clientDBDetails) throws ClientDBConnectionException;
    public Map<String, ClientDBSchema> fetchClientDBSchema(ClientDBDetails clientDBDetails) throws ClientDBConnectionException;
}
