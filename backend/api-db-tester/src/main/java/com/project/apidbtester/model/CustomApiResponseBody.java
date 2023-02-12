package com.project.apidbtester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomApiResponseBody {
    private HttpStatus status;
    private String message;
    private int statusCode;
}
