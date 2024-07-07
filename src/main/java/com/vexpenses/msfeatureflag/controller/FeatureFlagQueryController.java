package com.vexpenses.msfeatureflag.controller;

import com.vexpenses.msfeatureflag.model.RequestEntity;
import com.vexpenses.msfeatureflag.service.FeatureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/v1/featureflag")
@Tag(name = "API para consulta de Feature")
public class FeatureFlagQueryController {

    @Autowired
    FeatureService featureService;

    @GetMapping(value = "/{applicationId}")
    public ResponseEntity<?> getFeature(@PathVariable("applicationId") String applicationId) {
        return ResponseEntity.ok(featureService.getFeaturesByApplicationId(applicationId));
    }

    @GetMapping(value = "/query")
    public ResponseEntity<?> getFeature(@RequestBody RequestEntity requestEntity) {
        return ResponseEntity.ok(featureService.getFeaturesByQuery(requestEntity));
    }

}
