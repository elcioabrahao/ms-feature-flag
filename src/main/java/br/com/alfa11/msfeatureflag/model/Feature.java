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
    @Schema(name="featureId", example="SKFBFSJWNFSKKCNSNWFSKF", description = "Identificador único da feature")
    private String featureId;
    @Schema(name="name", example="PoupUp da Promoção do Dia das Mães", description = "Nome da feature")
    private String name;
    @Schema(name="description", example="Está é a promoção que vai ser exibida no site para o dia das mães", description = "Descrição da feature")
    private String description;
    @Schema(name="category", example="Marketing", description = "Categoria da feature (agrupador) (opicional)")
    private String category;
    @Schema(name="type", example="Campanhas Website", description = "Tipo da feature (agrupador) (opicional)")
    private String type;
    @Indexed
    @Schema(name="status", example="true", description = "True para feature ativa, false para feature inativa")
    private boolean status;
    @Indexed
    @Schema(name="applicationId", example="website01", description = "Identificador da aplicação que está utilizando a feature")
    private String applicationId;
    @Schema(name="defaultStateFlag", example="true", description = "Comportamento padrão para a feature: true permitir, false não permitir por padrão")
    private boolean defaultStateFlag;
    @Schema(name="userId", example="1268684739294", description = "Identificador do usuário (opicional)")
    private String userId;
    @Schema(name="companyId", example="43565675000170", description = "Identificador da Empresa (opicional)")
    private String companyId;
    @Schema(name="filterId", example="FBNOEQRGNOERNOFBNQOERNB", description = "Identificador do filtro a ser aplicado para modificar o comportamento padrão desta feature")
    private String filterId;
    @Indexed
    @Schema(name="filterType", example="COMPANYID", description = "Indica se o filtro e por empresa: COMPANYID ou por usuário USERID")
    private String filterType;
    @Schema(name="dateTimeBegin", example="01/05/2024", description = "Informa inicio da validade desta feature (opicional)")
    private String dateTimeBegin;
    @Schema(name="dateTimeEnd", example="15/05/2024", description = "Informa fim da validade desta feature (opicional)")
    private String dateTimeEnd;
    @Schema(name="testAB", example="false", description = "True para feature tipo Teste AB (padrão false)")
    private boolean testAB;
    @Schema(name="probabilityToAllow", example="0.5", description = "Probalidade para que seja retornado TRUE (permitido) no teste AB, obrigatório se testeAB estiver TRUE")
    private double probabilityToAllow;
    @Schema(name="migration", example="false", description = "True se é uma feature para migração de empresas (padrão false)")
    private boolean migration;
    @Schema(name="totalOfCompanies", example="1000", description = "Quantidade total de empresas a serem migradas")
    private int totalOfCompanies;
    @Schema(name="migrationPercentage", example="0.5", description = "Porcentagem de empresas a serem migradas (número ente 0.0 a 1.0)")
    private double migrationPercentage;
    @Schema(name="modifiedAt", example="18/08/2024 07:00:00", description = "Data de criação ou modificação")
    private String modifiedAt;
}
