package com.project.apidbtester.clientdb;

import com.project.apidbtester.clientdb.dtos.ClientDBMetaData;
import com.project.apidbtester.clientdb.dtos.TestClientConnectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClientDbInfoController is used to handle APIs related to client db info
 */
@RestController
@RequestMapping("/api")
public class ClientDbInfoController {
    @Autowired
    private ClientDBInfoService clientDBInfoService;

    /**
     * Check if able to connect to client db
     * @param clientDBCredentialsEntity
     * @return response if successful or not
     */
    @PostMapping("/test-client-db-connection")
    public ResponseEntity<TestClientConnectionResponse> testClientDBConnection(@RequestBody ClientDBCredentialsEntity clientDBCredentialsEntity) {
        String connectionMessage = clientDBInfoService.testClientDBConnection(clientDBCredentialsEntity);
        TestClientConnectionResponse testClientConnectionResponse = TestClientConnectionResponse
                .builder()
                .message(connectionMessage)
                .build();
        return ResponseEntity.ok(testClientConnectionResponse);
    }

    /**
     * Get client db metadata which contains table fields of all tables, primary keys and their types
     * @return client db metadata
     */
    @GetMapping("/get-client-db-metadata")
    public ResponseEntity<Map<String, ClientDBMetaData>> fetchClientDBMetaData() {
        return ResponseEntity.ok(clientDBInfoService.fetchClientDBMetaData());
    }

    /**
     * Get client db credentials from the db if available
     * @return client db credentials
     */
    @GetMapping("/get-client-db-credentials")
    public ResponseEntity<ClientDBCredentialsEntity> getClientDBCredentials() {
        return ResponseEntity.ok(clientDBInfoService.getClientDBCredentials());
    }
}
