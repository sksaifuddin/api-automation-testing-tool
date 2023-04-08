package com.project.apidbtester.utils;

import lombok.Builder;
import lombok.Data;

/**
 * ErrorResponse is used to return an object containing error message
 */
@Data
@Builder
public class ErrorResponse {
    private String message;
}
