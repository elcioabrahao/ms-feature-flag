package br.com.alfa11.msfeatureflag.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestEntity implements Serializable {

    @Schema(name="applicationId", example="web01", description = "Identificado da aplicação corrente (conforme criado no registro de feature)")
    private String applicationId;
    @Schema(name="userId", example="KSFSKFHKSFSFHKSFH", description = "Identificador do usuário (mesmo formato da lista de allow all/denny all)")
    private String userId;
    @Schema(name="companyId", example="43098758000179", description = "Identificador de empresa (mesmo formato da lista de allow all/denny all)")
    private String companyId;

}
