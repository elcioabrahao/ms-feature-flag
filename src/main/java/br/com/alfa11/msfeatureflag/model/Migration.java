package br.com.alfa11.msfeatureflag.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Set;

@RedisHash("migration")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Migration implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private Set<String> uniqueIdentifiers;
}
