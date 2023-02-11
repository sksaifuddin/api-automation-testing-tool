package com.project.apidbtester.service;

import com.project.apidbtester.model.ClientDBDetails;

public interface ClientDBDetailsService {
    public String testClientDBConnection(ClientDBDetails clientDBDetails);
}
