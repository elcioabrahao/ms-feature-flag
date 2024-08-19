package br.com.alfa11.msfeatureflag.repository;

import br.com.alfa11.msfeatureflag.model.Migration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigrationRepository extends CrudRepository<Migration, String> {
}
