package com.project.apidbtester.testapis.controllers;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.responses.TestCasesApiException;
import com.project.apidbtester.testapis.services.TestCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestcasesApiController {

    @Autowired
    private TestCasesService testCasesService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/testcases/get")
    public List<TestCaseDetails> fetchAllTestCases() throws TestCasesApiException {
        return testCasesService.fetchAllTestCases();
    }

    @GetMapping("/testcases/get/{id}")
    public TestCaseDetails fetchTestCaseByID(@PathVariable int id) throws TestCasesApiException {
        return testCasesService.fetchTestCaseById(id);
    }
}
