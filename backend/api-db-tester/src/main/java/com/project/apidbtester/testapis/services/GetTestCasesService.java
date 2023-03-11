package com.project.apidbtester.testapis.services;

import com.project.apidbtester.clientdbinfo.ClientDBCredentialsEntity;
import com.project.apidbtester.clientdbinfo.ClientDBInfoRepository;
import com.project.apidbtester.constants.GlobalConstants;
import com.project.apidbtester.testapis.dtos.TestCaseData;
import com.project.apidbtester.testapis.dtos.TestInput;
import com.project.apidbtester.testapis.dtos.TestResponse;
import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.repositories.TestCaseDetailsRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Service
public class GetTestCasesService {

    @Autowired
    ClientDBInfoRepository clientDBInfoRepository;

    @Autowired
    private TestCaseDetailsRepository testCaseDetailsRepository;

    public List<TestCaseDetails> fetchAllTestCases() {
        return testCaseDetailsRepository.findAll();
    }
}
