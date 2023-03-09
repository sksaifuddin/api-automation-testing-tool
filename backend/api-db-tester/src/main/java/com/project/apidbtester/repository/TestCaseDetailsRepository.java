package com.project.apidbtester.repository;

import com.project.apidbtester.testapis.dtos.TestCaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseDetailsRepository extends JpaRepository<TestCaseDetails, Integer> {
}
