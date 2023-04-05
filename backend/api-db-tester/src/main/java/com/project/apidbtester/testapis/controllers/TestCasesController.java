package com.project.apidbtester.testapis.controllers;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.services.TestCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestCasesController {

    @Autowired
    private TestCasesService testCasesService;

    @GetMapping("/testcases/get")
    public ResponseEntity<List<TestCaseDetails>> fetchAllTestCases() {
        return ResponseEntity.ok(testCasesService.fetchAllTestCases());
    }

    @GetMapping("/testcases/get/{id}")
    public ResponseEntity<TestCaseDetails> fetchTestCaseById(@PathVariable int id) {
        return ResponseEntity.ok(testCasesService.fetchTestCaseById(id));
    }

    @DeleteMapping("/testcases/delete/{id}")
    public ResponseEntity<String> deleteTestCaseById(@PathVariable int id) {
        return ResponseEntity.ok(testCasesService.deleteTestCaseById(id));
    }
}
