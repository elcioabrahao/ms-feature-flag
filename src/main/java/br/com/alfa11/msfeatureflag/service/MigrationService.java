package br.com.alfa11.msfeatureflag.service;

import br.com.alfa11.msfeatureflag.model.Migration;
import br.com.alfa11.msfeatureflag.repository.MigrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MigrationService {

    @Autowired
    MigrationRepository migrationRepository;

    public Migration save(Migration migration) {
        return migrationRepository.save(migration);
    }

    public Migration findById(String id) {
        return migrationRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        migrationRepository.deleteById(id);
    }
}
