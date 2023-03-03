package com.project.apidbtester.responses;

import com.project.apidbtester.responses.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ClientDBConnectionException.class)
    public ResponseEntity<ApiResponse> clientDBConnectionException(ClientDBConnectionException clientDBConnectionException, WebRequest request) {
        ApiResponse message = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, clientDBConnectionException.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
