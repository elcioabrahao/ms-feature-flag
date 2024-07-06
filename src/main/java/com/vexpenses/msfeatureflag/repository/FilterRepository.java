package com.vexpenses.msfeatureflag.repository;

import com.vexpenses.msfeatureflag.model.Filter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends CrudRepository<Filter, String> {
}
