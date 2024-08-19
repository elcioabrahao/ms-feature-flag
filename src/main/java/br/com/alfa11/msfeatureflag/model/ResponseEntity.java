package br.com.alfa11.msfeatureflag.model;

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
    private String applicationId;
    private String userId;
    private String companyId;
    private boolean filterApplied;
    private List<String> filterAppliedIds;
    private List<String> featureIds;
    private boolean success;
    private String errorMessage;
}
