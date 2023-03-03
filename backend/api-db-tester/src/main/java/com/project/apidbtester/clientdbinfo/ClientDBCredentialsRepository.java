package com.project.apidbtester.clientdbinfo;

import com.project.apidbtester.response.ClientDBConnectionException;
import com.project.apidbtester.response.CustomApiResponseBody;
import com.project.apidbtester.testapis.dtos.TestDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ClientDBCredentialsRepository extends JpaRepository<ClientDBCredentialsEntity, Long> {

//    public CustomApiResponseBody testClientDBConnection(ClientDBCredentialsEntity clientDBCredentialsEntity) throws ClientDBConnectionException;
//    public Map<String, ClientDBSchema> fetchClientDBSchema(ClientDBCredentialsEntity clientDBCredentialsEntity) throws ClientDBConnectionException;
//    public String fetchTestResult(TestDetails testDetails) throws ClientDBConnectionException;
}
