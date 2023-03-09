package com.project.apidbtester.repository;

import com.project.apidbtester.testapis.dtos.ColumnValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnValueRepository extends JpaRepository<ColumnValue, Integer> {
}
