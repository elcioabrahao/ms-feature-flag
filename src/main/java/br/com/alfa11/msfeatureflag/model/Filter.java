package br.com.alfa11.msfeatureflag.model;

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
    @Schema(name="filterId", example="DKGFJRUFJFKFKDE", description = "Identificador único do filtro")
    private String filterId;
    @Schema(name="filterName", example="Dia das Mães 2024", description = "Nome do filtro")
    private String filterName;
    @Schema(name="filterType", example="COMPANYID", description = "Tipo do filtro USERID ou COMPANYID")
    private String filterType;
    @Schema(name="allowAll", example="false", description = "True para permitir todos, False para negar todos")
    private boolean allowAll;
    @Schema(name="filterList", example="CNPJ01, CNPJ02, CNPJ03...", description = "Lista de identificadores de USUER ou COMPANY (CPFs ou CNPJS, ou qualquer outro ID que faça sentido)")
    private List<String> filterList;
    @Schema(name="modifiedAt", example="18/08/2024 07:00:00", description = "Data de criação ou modificação")
    private String modifiedAt;
}
