package com.project.apidbtester.testapis.repositories;

import com.project.apidbtester.testapis.entities.TestColumnValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnValueRepository extends JpaRepository<TestColumnValue, Integer> {
}
