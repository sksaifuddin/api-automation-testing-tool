package com.project.apidbtester.clientdb.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestClientConnectionResponse {
    String message;
}
