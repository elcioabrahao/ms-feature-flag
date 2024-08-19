package br.com.alfa11.msfeatureflag.controller;

import br.com.alfa11.msfeatureflag.model.RequestEntity;
import br.com.alfa11.msfeatureflag.service.FeatureService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "API Consulta de Feature por ApplicationId",
            description = "Utilize este end point caso utilize features filtradas somente pelo ID da aplicação desejada.")
    public ResponseEntity<?> getFeature(@PathVariable("applicationId") String applicationId) {
        return ResponseEntity.ok(featureService.getFeaturesByApplicationId(applicationId));
    }

    @GetMapping(value = "/query")
    @Operation(summary = "API Consulta por ApplicationId, User ou Company",
            description = "Utilize este end ponint para fazer a consulta pelo applicationId e pelas allow lists ou deny lists.")
    public ResponseEntity<?> getFeature(@RequestBody RequestEntity requestEntity) {
        return ResponseEntity.ok(featureService.getFeaturesByQuery(requestEntity));
    }

}
