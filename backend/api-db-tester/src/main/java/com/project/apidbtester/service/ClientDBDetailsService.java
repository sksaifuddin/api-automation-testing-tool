package com.project.apidbtester.service;

import com.project.apidbtester.error.ClientDBConnectionException;
import com.project.apidbtester.model.ClientDBDetails;
import com.project.apidbtester.model.CustomApiResponseBody;

public interface ClientDBDetailsService {
    public CustomApiResponseBody testClientDBConnection(ClientDBDetails clientDBDetails) throws ClientDBConnectionException;
}
