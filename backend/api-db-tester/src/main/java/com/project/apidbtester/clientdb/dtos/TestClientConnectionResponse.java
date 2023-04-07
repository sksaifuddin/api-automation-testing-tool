package com.project.apidbtester.clientdb.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * TestClientConnectionResponse contains the message if the connection to client db was successful or not
 */
@Data
@Builder
public class TestClientConnectionResponse {
    String message;
}
