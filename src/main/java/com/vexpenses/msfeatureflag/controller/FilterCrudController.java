package com.vexpenses.msfeatureflag.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1/featureflagfilter/crud")
@Tag(name = "API para CRUD de Filters para Feature")
public class FilterCrudController {
}
