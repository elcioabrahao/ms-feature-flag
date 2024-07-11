package com.vexpenses.msfeatureflag.repository;

import com.vexpenses.msfeatureflag.model.Filter;
import com.vexpenses.msfeatureflag.model.Migration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigrationRepository extends CrudRepository<Migration, String> {
}
