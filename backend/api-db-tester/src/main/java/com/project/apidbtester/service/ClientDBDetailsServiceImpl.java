package com.project.apidbtester.service;

import com.project.apidbtester.model.ClientDBDetails;
import com.project.apidbtester.repository.ClientDBDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientDBDetailsServiceImpl implements ClientDBDetailsService {

    @Autowired
    private ClientDBDetailsRepository clientDBDetailsRepository;

    @Override
    public String testClientDBConnection(ClientDBDetails clientDBDetails) {
        clientDBDetailsRepository.save(clientDBDetails);
        return "Connection successful";
    }
}
