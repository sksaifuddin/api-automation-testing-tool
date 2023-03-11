package com.project.apidbtester.testapis.controllers;

import com.project.apidbtester.responses.ClientDBConnectionException;
import com.project.apidbtester.testapis.dtos.TestCaseData;
import com.project.apidbtester.testapis.services.GetTestCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestcasesApiController {

    @Autowired
    private GetTestCasesService getTestCasesService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/testcases/get")
    public List<TestCaseData> fetchAllTestCases() throws ClientDBConnectionException {
        return getTestCasesService.fetchAllTestCases().stream().map(post -> modelMapper.map(post, TestCaseData.class))
                .collect(Collectors.toList());
    }
}
