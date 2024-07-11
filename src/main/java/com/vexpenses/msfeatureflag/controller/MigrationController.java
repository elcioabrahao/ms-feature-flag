package com.vexpenses.msfeatureflag.controller;


import com.vexpenses.msfeatureflag.model.Filter;
import com.vexpenses.msfeatureflag.model.Migration;
import com.vexpenses.msfeatureflag.service.MigrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/v1/migration")
@Tag(name = "API para consulta de migrações")
public class MigrationController {

    @Autowired
    MigrationService migrationService;

    @GetMapping(value = "/{ulid}")
    @Operation(summary = "API para consultar um Migration por Feature ID",
            description = "Passe uma variável de path com o featureId")
    public ResponseEntity<?> getFilterById(@PathVariable String ulid) {
        Migration migration = migrationService.findById(ulid);
        if (migration==null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(migration);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{ulid}")
    @Operation(summary = "API para deletar uma migration.",
            description = "Passe uma variável de path com o featureId")
    public void deleteFilter(@PathVariable String ulid) {
        migrationService.deleteById(ulid);
    }

}
