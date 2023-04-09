package com.project.apidbtester.clientdb;

import com.project.apidbtester.clientdb.dtos.ClientDBMetaData;
import com.project.apidbtester.clientdb.dtos.TestClientConnectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class ClientDbInfoController {
    @Autowired
    private ClientDBInfoService clientDBInfoService;

    @PostMapping("/test-client-db-connection")
    public ResponseEntity<TestClientConnectionResponse> testClientDBConnection(@RequestBody ClientDBCredentialsEntity clientDBCredentialsEntity) {
        String connectionMessage = clientDBInfoService.testClientDBConnection(clientDBCredentialsEntity);
        TestClientConnectionResponse testClientConnectionResponse = TestClientConnectionResponse
                .builder()
                .message(connectionMessage)
                .build();
        return ResponseEntity.ok(testClientConnectionResponse);
    }

    @GetMapping("/get-client-db-metadata")
    public ResponseEntity<Map<String, ClientDBMetaData>> fetchClientDBMetaData() {
        return ResponseEntity.ok(clientDBInfoService.fetchClientDBMetaData());
    }

    @GetMapping("/get-client-db-credentials")
    public ResponseEntity<ClientDBCredentialsEntity> getClientDBCredentials() {
        return ResponseEntity.ok(clientDBInfoService.getClientDBCredentials());
    }
}
