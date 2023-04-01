package com.project.apidbtester.exceptions;

import com.project.apidbtester.clientdb.ClientDBInfoService;
import com.project.apidbtester.testapis.controllers.TestApiController;
import com.project.apidbtester.testapis.services.TestCasesService;
import com.project.apidbtester.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            ClientDBInfoService.ClientDBConnectionException.class,
            ClientDBInfoService.ClientDBCredentialsNotFoundException.class,
            TestCasesService.TestCaseNotFoundException.class,
            TestCasesService.TestCasesNotFoundException.class,
            TestApiController.InvalidRequestTypeException.class
    })
    public ResponseEntity<ErrorResponse> handleExceptions(RuntimeException e) {
        ErrorResponse message = ErrorResponse.builder().message(e.getMessage()).build();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof ClientDBInfoService.ClientDBCredentialsNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof ClientDBInfoService.ClientDBConnectionException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (e instanceof TestCasesService.TestCaseNotFoundException || e instanceof TestCasesService.TestCasesNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof TestApiController.InvalidRequestTypeException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(message);
    }
}
