package com.vexpenses.msfeatureflag.model;

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
    private String featureId;           // Identificador único da feature
    private String name;                // Nome da feature
    private String description;         // Descrição da feature
    private String category;            // Categoria da feature (agrupador) (opicional)
    private String type;                // Tipo da feature (agrupador) (opicional)
    @Indexed
    private boolean status;             // True para feature ativa, false para feature inativa (opicional)
    @Indexed
    private String applicationId;       // Identificador da aplicação que está utilizando a feature
    private boolean defaultStateFlag;   // Comportamento padrão para a feature: true permitir, false não permitir por padrão
    private String userId;              // Identificador do usuário (opicional)
    private String companyId;           // Identificador da Empresa (opicional)
    private String filterId;            // Identificador do filtro a ser aplicado para modificar o comportamento padrão desta feature
    @Indexed
    private String filterType;          // Indica se o filtro e por empresa: COMPANYID ou por usuário USERID
    private String dateTimeBegin;       // Informa inicio da validade desta feature (opicional)
    private String dateTimeEnd;         // informa fim da validade desta feature (opicional)
    private String modifiedAt;           // data de criação ou modificação
}
