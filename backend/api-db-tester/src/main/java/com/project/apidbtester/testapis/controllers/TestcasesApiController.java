package com.project.apidbtester.testapis.controllers;

import com.project.apidbtester.responses.ClientDBConnectionException;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.services.TestCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import java.util.List;

@RestController
public class TestcasesApiController {

    @Autowired
    private TestCasesService testCasesService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/testcases/get")
    public List<TestCaseDetails> fetchAllTestCases() throws ClientDBConnectionException {
        return testCasesService.fetchAllTestCases();
    }
}
