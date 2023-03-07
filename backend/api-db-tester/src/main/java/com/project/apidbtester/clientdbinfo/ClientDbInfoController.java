package com.project.apidbtester.clientdbinfo;

import com.project.apidbtester.clientdbinfo.dtos.ClientDBMetaData;
import com.project.apidbtester.responses.ClientDBConnectionException;
import com.project.apidbtester.responses.dtos.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class ClientDbInfoController {
    @Autowired
    private ClientDBInfoService clientDBInfoService;

    @PostMapping("/test-client-db-connection")
    public ApiResponse testClientDBConnection(@RequestBody ClientDBCredentialsEntity clientDBCredentialsEntity) throws ClientDBConnectionException {
        return clientDBInfoService.testClientDBConnection(clientDBCredentialsEntity);
    }

    @PostMapping("/fetch-client-db-metadata")
    public Map<String, ClientDBMetaData> fetchClientDBMetaData(@RequestBody ClientDBCredentialsEntity clientDBCredentialsEntity) throws ClientDBConnectionException {
        return clientDBInfoService.fetchClientDBMetaData(clientDBCredentialsEntity);
    }
}
