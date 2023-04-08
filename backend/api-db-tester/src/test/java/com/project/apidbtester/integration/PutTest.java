package com.project.apidbtester.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.apidbtester.testapis.dtos.TestResponse;
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
public class PutTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPutRequest_WhenPassed() throws Exception {

        String apiBody = "{\n" +
                "    \"testCaseDetails\": {\n" +
                "        \"type\": \"put\",\n" +
                "        \"url\": \"http://csci5308vm16.research.cs.dal.ca:9191/updateProducer\",\n" +
                "        \"payload\": \"{\\\"id\\\": 211, \\\"first_name\\\":\\\"shubham\\\",\\\"last_name\\\":\\\"mishra\\\",\\\"gender\\\":\\\"m\\\",\\\"film_count\\\":25}\",\n" +
                "        \"tableName\": \"producers\",\n" +
                "        \"primaryKeyName\": \"id\",\n" +
                "        \"primaryKeyValue\": 211\n" +
                "    },\n" +
                "    \"columnValues\": [\n" +
                "        {\n" +
                "            \"columnName\": \"first_name\",\n" +
                "            \"expectedValue\": \"shubham\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"columnName\": \"last_name\",\n" +
                "            \"expectedValue\": \"mishra\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"columnName\": \"film_count\",\n" +
                "            \"expectedValue\": \"25\"\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(apiBody))
                .andExpect(status().isOk())
                .andReturn();

        TestResponse testResponse = objectMapper.readValue(result.getResponse().getContentAsString(), TestResponse.class);
        assertEquals(Optional.ofNullable(testResponse.getHttpStatusCode()), Optional.of(200));
        assertEquals(testResponse.getAllTestPassed(), true);

    }

    @Test
    public void testPutRequest_WhenFailed() throws Exception {

        String apiBody = "{\n" +
                "    \"testCaseDetails\": {\n" +
                "        \"type\": \"put\",\n" +
                "        \"url\": \"http://csci5308vm16.research.cs.dal.ca:9191/updateProducer\",\n" +
                "        \"payload\": \"{\\\"id\\\": 211, \\\"first_name\\\":\\\"shubham\\\",\\\"last_name\\\":\\\"mishra\\\",\\\"gender\\\":\\\"m\\\",\\\"film_count\\\":25}\",\n" +
                "        \"tableName\": \"producers\",\n" +
                "        \"primaryKeyName\": \"id\",\n" +
                "        \"primaryKeyValue\": 211\n" +
                "    },\n" +
                "    \"columnValues\": [\n" +
                "        {\n" +
                "            \"columnName\": \"first_name\",\n" +
                "            \"expectedValue\": \"shubham\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"columnName\": \"last_name\",\n" +
                "            \"expectedValue\": \"jain\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"columnName\": \"film_count\",\n" +
                "            \"expectedValue\": \"25\"\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(apiBody))
                .andExpect(status().isOk())
                .andReturn();

        TestResponse testResponse = objectMapper.readValue(result.getResponse().getContentAsString(), TestResponse.class);
        assertEquals(Optional.ofNullable(testResponse.getHttpStatusCode()), Optional.of(200));
        assertEquals(testResponse.getAllTestPassed(), false);

    }


}