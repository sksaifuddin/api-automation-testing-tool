package com.project.apidbtester.service;

import com.project.apidbtester.error.ClientDBConnectionException;
import com.project.apidbtester.model.ClientDBDetails;
import com.project.apidbtester.model.CustomApiResponseBody;

import java.util.List;
import java.util.Map;

public interface ClientDBDetailsService {
    public CustomApiResponseBody testClientDBConnection(ClientDBDetails clientDBDetails) throws ClientDBConnectionException;
    public Map<String, List<String>> fetchClientDBSchema(ClientDBDetails clientDBDetails) throws ClientDBConnectionException;
}
