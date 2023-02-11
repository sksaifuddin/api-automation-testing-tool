package com.project.apidbtester.repository;

import com.project.apidbtester.model.ClientDBDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDBDetailsRepository extends JpaRepository<ClientDBDetails, Long> { }
