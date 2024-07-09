package com.vexpenses.msfeatureflag.controller;

import com.vexpenses.msfeatureflag.model.Feature;
import com.vexpenses.msfeatureflag.service.FeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/v1/featureflag/crud")
@Tag(name = "API para CRUD de Feature")
public class FeatureFlagCrudController {

    @Autowired
    FeatureService featureService;

    @PostMapping
    @Operation(summary = "API Cadastro de Feature",
            description = "Utilize esta API para cadastrar uma feature.")
    public ResponseEntity<?> createFeatureFlag(@RequestBody Feature feature) {
        return ResponseEntity.ok(featureService.create(feature));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    @Operation(summary = "API para atualização de feature flag",
            description = "Utilize passando o json da feature mantendo o mesmo FEATUREID")
    public Feature updateFeature(@RequestBody Feature feature) {
        return featureService.update(feature);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{ulid}")
    @Operation(summary = "API para deletar um feature flag.",
            description = "Passe uma variável depath com o fetureId")
    public void deleteFeatureById(@PathVariable String ulid) {
        featureService.deleteById(ulid);
    }

    @GetMapping(value = "/{ulid}")
    @Operation(summary = "API para consultar uma FeatureFlagPor ID",
            description = "Passe uma variável depath com o fetureId")
    public ResponseEntity<?> getFeatureById(@PathVariable String ulid) {
        Optional<Feature> featureOptional = featureService.findById(ulid);
        if (!featureOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(featureOptional.get());
        }

    }

    @GetMapping(value = "/getallfeatures")
    @Operation(summary = "API para consultar uma FeatureFlag por ID",
            description = "Passe uma variável depath com o fetureId")
    public ResponseEntity<?> getAllFeatures() {
        Iterable<Feature> features = featureService.findByFeatureIdNotNull(true);
        List<Feature> result = new ArrayList<>();
        features.forEach(result::add);
        Iterable<Feature> features2 = featureService.findByFeatureIdNotNull(false);
        features2.forEach(result::add);
        log.info("Resposta: "+result.toString());
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/getfeatureby/{value}")
    @Operation(summary = "API para consultar uma FeatureFlag por uma string",
            description = "Passe uma variável depath com o fetureId")
    public ResponseEntity<?> getFeatureByValue(@PathVariable String value) {
        Iterable<Feature> features = featureService.findByValue(value);
        List<Feature> result = new ArrayList<>();
        features.forEach(result::add);
        return ResponseEntity.ok(result);

    }

}
