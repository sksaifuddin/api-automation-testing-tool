package com.project.apidbtester.testapis.services;

import com.project.apidbtester.clientdbinfo.ClientDBInfoRepository;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCasesService {

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    public List<TestCaseDetails> fetchAllTestCases() {
        return testCaseDetailsRepository.findAll();
    }
}
