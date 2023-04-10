package com.project.apidbtester.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.apidbtester.clientdb.constants.Constants;
import com.project.apidbtester.clientdb.dtos.TestClientConnectionResponse;
import com.project.apidbtester.testapis.dtos.TestResponse;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientDBTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testClientDBConnection() throws Exception {

        // Arrange
        String apiBody = "{\n" +
                "    \"databaseUrl\": \"jdbc:mysql://db-5308.cs.dal.ca/CSCI5308_16_TEST\",\n" +
                "    \"userName\": \"CSCI5308_16_TEST_USER\",\n" +
                "    \"password\": \"phohKaiv2b\"\n" +
                "}";

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/test-client-db-connection")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(apiBody))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsObject = new JSONObject(result.getResponse().getContentAsString());
        // Assert
        assertEquals(Optional.ofNullable(jsObject.get("message")), Optional.of(Constants.CLIENT_DB_CONNECTION_SUCCESSFUL));
    }

}
