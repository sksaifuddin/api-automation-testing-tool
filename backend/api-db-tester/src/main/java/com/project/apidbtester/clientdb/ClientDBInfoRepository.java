package com.project.apidbtester.clientdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDBInfoRepository extends JpaRepository<ClientDBCredentialsEntity, Long> {

}
