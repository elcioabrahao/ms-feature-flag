package br.com.alfa11.msfeatureflag.repository;

import br.com.alfa11.msfeatureflag.model.Filter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends CrudRepository<Filter, String> {
}
