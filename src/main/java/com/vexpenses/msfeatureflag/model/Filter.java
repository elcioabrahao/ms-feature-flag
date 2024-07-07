package com.vexpenses.msfeatureflag.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

@RedisHash("filter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Indexed
    @Schema(description = "Identificador único do filtro")
    private String filterId;            // Identificador único do filtro
    @Schema(description = "Nome do filtro")
    private String filterName;          // Nome do filtro
    @Schema(description = "Tipo do filtro USERID ou COMPANYID")
    private String filterType;          // Tipo do filtro USERID ou COMPANYID
    @Schema(description = "True para permitir todos, False para negar todos")
    private boolean allowAll;           // True para permitir todos, False para negar todos
    @Schema(description = "Lista de identificadores de USUER ou COMPANY (CPFs ou CNPJS, ou qualquer outro ID que faça sentido)")
    private List<String> filterList;    // Lista de identificadores de USUER ou COMPANY (CPFs ou CNPJS, ou qualquer outro ID que faça sentido)
    @Schema(description = "Data de criação ou modificação")
    private String modifiedAt;          // Data de criação ou modificação
}
