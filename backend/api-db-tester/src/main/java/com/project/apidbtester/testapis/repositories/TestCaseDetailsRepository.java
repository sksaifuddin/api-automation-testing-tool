package com.project.apidbtester.testapis.repositories;

import com.project.apidbtester.testapis.entities.TestCaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseDetailsRepository extends JpaRepository<TestCaseDetails, Integer> {
}
