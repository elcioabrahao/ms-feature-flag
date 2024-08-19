package br.com.alfa11.msfeatureflag.controller;

import br.com.alfa11.msfeatureflag.model.Filter;
import br.com.alfa11.msfeatureflag.service.FilterService;
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
@RequestMapping("/v1/featureflagfilter/crud")
@Tag(name = "API para CRUD de Filters para Feature")
public class FilterCrudController {

    @Autowired
    private FilterService filterService;

    @PostMapping
    @Operation(summary = "API Cadastro de Filtro de Feature",
            description = "Utilize esta API para cadastrar um filtro de feature.")
    public ResponseEntity<Filter> createFilter(@RequestBody Filter filter) {
        return ResponseEntity.ok(filterService.save(filter));
    }

    @PutMapping
    @Operation(summary = "API para atualização de filtro de feature flag",
            description = "Utilize passando o json de filter mantendo o mesmo FILTERID")
    public ResponseEntity<Filter> updateFilter(@RequestBody Filter filter) {
        return ResponseEntity.ok(filterService.update(filter));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{ulid}")
    @Operation(summary = "API para deletar um filtro de feature flag.",
            description = "Passe uma variável de path com o filterId")
    public void deleteFilter(@PathVariable String ulid) {
        filterService.deleteFilterById(ulid);
    }

    @GetMapping(value = "/{ulid}")
    @Operation(summary = "API para consultar uma Filtro de FeatureFlag ID",
            description = "Passe uma variável de path com o filterId")
    public ResponseEntity<?> getFilterById(@PathVariable String ulid) {
        Optional<Filter> filterOptional = filterService.getFilterById(ulid);
        if (!filterOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(filterOptional.get());
        }

    }

    @GetMapping(value = "/getallfilters")
    @Operation(summary = "API para consultar todos os filtros cadastrados",
            description = "Retorna todos os filtros cadastrados para todas as aplicações")
    public ResponseEntity<?> getAllFilters() {
        Iterable<Filter> filters = filterService.getFilters();
        List<Filter> result = new ArrayList<>();
        filters.forEach(result::add);
        return ResponseEntity.ok(result);
    }
}
