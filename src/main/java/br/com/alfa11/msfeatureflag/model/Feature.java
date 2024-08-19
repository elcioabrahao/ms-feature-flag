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

@RedisHash("feature")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feature implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Indexed
    @Schema(description = "Identificador único da feature")
    private String featureId;           // Identificador único da feature
    @Schema(description = "Nome da feature")
    private String name;                // Nome da feature
    @Schema(description = "Descrição da feature")
    private String description;         // Descrição da feature
    @Schema(description = "Categoria da feature (agrupador) (opicional)")
    private String category;            // Categoria da feature (agrupador) (opicional)
    @Schema(description = "Tipo da feature (agrupador) (opicional)")
    private String type;                // Tipo da feature (agrupador) (opicional)
    @Indexed
    @Schema(description = "True para feature ativa, false para feature inativa")
    private boolean status;             // True para feature ativa, false para feature inativa
    @Indexed
    @Schema(description = "Identificador da aplicação que está utilizando a feature")
    private String applicationId;       // Identificador da aplicação que está utilizando a feature
    @Schema(description = "Comportamento padrão para a feature: true permitir, false não permitir por padrão")
    private boolean defaultStateFlag;   // Comportamento padrão para a feature: true permitir, false não permitir por padrão
    @Schema(description = "Identificador do usuário (opicional)")
    private String userId;              // Identificador do usuário (opicional)
    @Schema(description = "Identificador da Empresa (opicional)")
    private String companyId;           // Identificador da Empresa (opicional)
    @Schema(description = "Identificador do filtro a ser aplicado para modificar o comportamento padrão desta feature")
    private String filterId;            // Identificador do filtro a ser aplicado para modificar o comportamento padrão desta feature
    @Indexed
    @Schema(description = "Indica se o filtro e por empresa: COMPANYID ou por usuário USERID")
    private String filterType;          // Indica se o filtro e por empresa: COMPANYID ou por usuário USERID
    @Schema(description = "Informa inicio da validade desta feature (opicional)")
    private String dateTimeBegin;       // Informa inicio da validade desta feature (opicional)
    @Schema(description = "Informa fim da validade desta feature (opicional)")
    private String dateTimeEnd;         // Informa fim da validade desta feature (opicional)
    @Schema(description = "True para feature tipo Teste AB (padrão false)")
    private boolean testAB;             //  true se é um teste AB, false se não for.
    @Schema(description = "Probalidade para que seja retornado TRUE (permitido) no teste AB, obrigatório se testeAB estiver TRUE")
    private double probabilityToAllow;  // probabilidade para retornar TRUE para permitido no teste AB (obrigadorio se testeAB for) padrão: 0.5.
    @Schema(description = "True se é uma feature para migração de empresas (padrão false)")
    private boolean migration;
    @Schema(description = "Quantidade total de empresas a serem migradas")
    private int totalOfCompanies;
    @Schema(description = "Porcentagem de empresas a serem migradas (número ente 0.0 a 1.0)")
    private double migrationPercentage;
    @Schema(description = "Data de criação ou modificação")
    private String modifiedAt;           // Data de criação ou modificação
}
