package br.com.alfa11.msfeatureflag.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseEntity {
    @Schema(name="applicationId", example="web01", description = "Identificado da aplicação corrente (conforme criado no registro de feature)")
    private String applicationId;
    @Schema(name="userId", example="KSFSKFHKSFSFHKSFH", description = "Identificador do usuário (mesmo formato da lista de allow all/denny all)")
    private String userId;
    @Schema(name="companyId", example="43098758000179", description = "Identificador de empresa (mesmo formato da lista de allow all/denny all)")
    private String companyId;
    @Schema(name="filterApplied", example="Lista de Permitidos", description = "Nome do filtro que vai ser aplicado a respectiva funcionalidade")
    private boolean filterApplied;
    @Schema(name="filterAppliedIds", example="", description = "Lista dos IDs dos filtros aplicados")
    private List<String> filterAppliedIds;
    @Schema(name="featureIds", example="", description = "Lista dos IDs das funcionlidades que podem ser executadas/exibidas. Somente as permitidas aparecem nessa lista, se a aplicação cliente não encontrar o ID aqui então deve negar a execussão/exibição da funcionalidade.")
    private List<String> featureIds;
    @Schema(name="success", example="true", description = "True se a solicitação foi bem scessidade false em contrário")
    private boolean success;
    @Schema(name="errorMessage", example="Erro no serviço", description = "Mensagem de errro é exibida em caso de sucess FALSE.")
    private String errorMessage;
}
