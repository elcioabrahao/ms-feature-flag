package com.vexpenses.msfeatureflag.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

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
    private String filterId;            // Identificador único do filtro
    private String filterName;          // Nome do filtro
    private String filterType;          // Tipo do filtro USERID ou COMPANYID
    private boolean allowAll;           // True para permitir todos, False para negar todos
    private List<String> filterList;    // Lista de identificadores de USUER ou COMPANY (CPFs ou CNPJS, ou qualquer outro ID que faça sentido)
    private String modifiedAt;          // data de criação ou modificação
}
