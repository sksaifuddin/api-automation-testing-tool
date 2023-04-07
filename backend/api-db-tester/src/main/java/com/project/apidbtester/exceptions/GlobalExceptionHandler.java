package com.project.apidbtester.exceptions;

import com.project.apidbtester.clientdb.exceptions.ClientDBConnectionException;
import com.project.apidbtester.clientdb.exceptions.ClientDBCredentialsNotFoundException;
import com.project.apidbtester.testapis.controllers.TestApiController;
import com.project.apidbtester.testapis.exceptions.TestCaseNotFoundException;
import com.project.apidbtester.testapis.exceptions.TestCasesNotFoundException;
import com.project.apidbtester.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            ClientDBConnectionException.class,
            ClientDBCredentialsNotFoundException.class,
            TestCaseNotFoundException.class,
            TestCasesNotFoundException.class,
            TestApiController.InvalidRequestTypeException.class
    })
    public ResponseEntity<ErrorResponse> handleExceptions(RuntimeException e) {
        ErrorResponse message = ErrorResponse.builder().message(e.getMessage()).build();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof ClientDBCredentialsNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof ClientDBConnectionException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (e instanceof TestCaseNotFoundException || e instanceof TestCasesNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof TestApiController.InvalidRequestTypeException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(message);
    }
}
