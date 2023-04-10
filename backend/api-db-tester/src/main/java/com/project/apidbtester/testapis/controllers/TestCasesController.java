package com.project.apidbtester.testapis.controllers;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import com.project.apidbtester.testapis.services.TestCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TestCasesController is used to process request related to the fetching/deletion of
 * the history of test cases saved in the db
 */
@RestController
@RequestMapping("/api")
public class TestCasesController {

    @Autowired
    private TestCasesService testCasesService;

    /**
     * fetch all the test cases performed earlier
     * @return list of test cases
     */
    @GetMapping("/testcases/get")
    public ResponseEntity<List<TestCaseDetails>> fetchAllTestCases() {
        return ResponseEntity.ok(testCasesService.fetchAllTestCases());
    }

    /**
     * fetch test case by id
     * @param id the id of the test case
     * @return test case
     */
    @GetMapping("/testcases/get/{id}")
    public ResponseEntity<TestCaseDetails> fetchTestCaseById(@PathVariable int id) {
        return ResponseEntity.ok(testCasesService.fetchTestCaseById(id));
    }

    /**
     * delete test case by id
     * @param id the id of test case
     * @return success/failure
     */
    @DeleteMapping("/testcases/delete/{id}")
    public ResponseEntity<String> deleteTestCaseById(@PathVariable int id) {
        return ResponseEntity.ok(testCasesService.deleteTestCaseById(id));
    }
}
