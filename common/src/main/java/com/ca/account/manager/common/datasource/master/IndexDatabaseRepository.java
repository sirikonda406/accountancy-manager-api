package com.ca.account.manager.common.datasource.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexDatabaseRepository extends JpaRepository<IndexDatabase, String> {
}
