package com.vexpenses.msfeatureflag.service;

import com.vexpenses.msfeatureflag.model.*;
import com.vexpenses.msfeatureflag.repository.FeatureRepository;
import com.vexpenses.msfeatureflag.util.DataUtil;
import com.vexpenses.msfeatureflag.util.UlidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FeatureService {
    @Autowired
    FeatureRepository featureRepository;
    @Autowired
    FilterService filterService;
    @Autowired
    MigrationService migrationService;

    public Iterable<Feature> findByFeatureIdNotNull(boolean status) {
        return featureRepository.findByStatus(status);
    }

    public Optional<Feature> findById(String id) {
        return featureRepository.findById(id);
    }
    public Feature create(Feature feature) {
        feature.setFeatureId(UlidGenerator.generate());
        feature.setModifiedAt(DataUtil.getAgoraCompleta());
     return featureRepository.save(feature);
    }
    public Feature update(Feature feature) {
        feature.setModifiedAt(DataUtil.getAgoraCompleta());
        return featureRepository.save(feature);
    }
    public void delete(Feature feature) {
       featureRepository.delete(feature);
    }
    public void deleteById(String id) {
        featureRepository.deleteById(id);
    }

    public ResponseEntity getFeaturesByApplicationId(String applicationId) {
        ResponseEntity response = new ResponseEntity();
        List<String> allowedFeatures =  new ArrayList<>();
        List<Feature> featureList = featureRepository.findByApplicationIdAndStatus(applicationId,true);
        if (featureList.isEmpty()) {
            response.setApplicationId(applicationId);
            response.setFeatureIds(new ArrayList<>());
            response.setUserId("");
            response.setCompanyId("");
            response.setFilterAppliedIds(new ArrayList<>());
            response.setSuccess(false);
            response.setErrorMessage("Não foram localizados features para esta aplicação: "+applicationId);
            return response;
        }
        featureList.forEach(feature->{
            boolean original = true;
            if(!feature.getDateTimeBegin().isEmpty()){
                original = DataUtil.dateIsBeforeNow(feature.getDateTimeBegin());
            }
            if(!feature.getDateTimeEnd().isEmpty() && original){
                original = DataUtil.dateIsAfterNow(feature.getDateTimeEnd());
            }
            if(!feature.getFilterId().isEmpty() && original){
                original = false;
            }
            if(original){
                if(feature.isDefaultStateFlag()){
                    allowedFeatures.add(feature.getFeatureId());
                }
            }
        });
        response.setSuccess(true);
        response.setErrorMessage("");
        response.setApplicationId(applicationId);
        response.setCompanyId("");
        response.setUserId("");
        response.setFeatureIds(allowedFeatures);
        response.setFilterApplied(false);
        response.setFilterAppliedIds(new ArrayList<>());
        return response;
    }

    public ResponseEntity getFeaturesByQuery(RequestEntity requestEntity) {
        List<Feature> featureList = new ArrayList<>();
        ResponseEntity response = new ResponseEntity();
        List<String> allowedFeatures =  new ArrayList<>();
        List<String> filtersApplied = new ArrayList<>();
        String message = "";
        response.setFilterApplied(true);
        Migration migration;
        featureList.addAll(featureRepository.findByApplicationIdAndStatus(requestEntity.getApplicationId(),true));

        if(featureList.isEmpty()){
            response.setApplicationId(requestEntity.getApplicationId());
            response.setFeatureIds(new ArrayList<>());
            response.setUserId(requestEntity.getUserId());
            response.setCompanyId(requestEntity.getCompanyId());
            response.setFilterAppliedIds(new ArrayList<>());
            response.setFilterApplied(false);
            response.setSuccess(false);
            response.setErrorMessage("Não foram localizados features para esta requisição: "+requestEntity.toString());
            return response;
        }
        // Itera pela lista de features para uma determinada ApplicationId
        for(Feature feature : featureList){
            // Verifica se é uma feature do tipo MIGRATION
            if(!feature.isMigration()){
                // Se não é migration entra aqui
                boolean original = true;
                Optional<Filter> filterOptional;
                // Verifica atual é anterior a data de inicio
                if(!feature.getDateTimeBegin().isEmpty()){
                    original = DataUtil.dateIsBeforeNow(feature.getDateTimeBegin());
                    message+="Feature "+feature.getFeatureId()+": Data de início vigência não atingida: "+feature.getDateTimeBegin();
                }
                // verifica se a data atual é posterior a data de final
                if(!feature.getDateTimeEnd().isEmpty() && original){
                    original = DataUtil.dateIsAfterNow(feature.getDateTimeEnd());
                    message+="Feature "+feature.getFeatureId()+": Data de final vigência ultrapassada: "+feature.getDateTimeBegin();
                }
                // verifica se é necessário veirifcar uma regra para um usuário individual
                if(!feature.getUserId().isEmpty()
                        && feature.getCompanyId().isEmpty()
                        && original){
                    if(requestEntity.getUserId().equals(feature.getUserId()) && feature.isDefaultStateFlag()){
                        if(!feature.isTestAB()){
                            allowedFeatures.add(feature.getFeatureId());
                        }else{
                            if(sortition(feature)){
                                allowedFeatures.add(feature.getFeatureId());
                            }
                        }
                    }
                }
                // verifica se é necessário veirificar uma empresa individual
                if(!feature.getCompanyId().isEmpty()
                        && feature.getUserId().isEmpty()
                        && original){
                    if(requestEntity.getCompanyId().equals(feature.getCompanyId()) && feature.isDefaultStateFlag()){
                        if(!feature.isTestAB()){
                            allowedFeatures.add(feature.getFeatureId());
                        }else{
                            if(sortition(feature)){
                                allowedFeatures.add(feature.getFeatureId());
                            }
                        }
                    }
                }
                // verifica se é necessário veirificar uma empresa mais usuário
                if(!feature.getCompanyId().isEmpty()
                        && !feature.getUserId().isEmpty()
                        && original){
                    if(requestEntity.getCompanyId().equals(feature.getCompanyId())
                            && requestEntity.getUserId().equals(feature.getUserId())
                            && feature.isDefaultStateFlag()){
                        if(!feature.isTestAB()){
                            allowedFeatures.add(feature.getFeatureId());
                        }else{
                            if(sortition(feature)){
                                allowedFeatures.add(feature.getFeatureId());
                            }
                        }
                    }
                }
                // Existe um ID de filtro associado a Feature e deve ser verificado se contem o ID do Usuário ou Compania dependento de seu tipo de filtro
                if(!feature.getFilterId().isEmpty() && original){
                    filterOptional = filterService.getFilterById(feature.getFilterId());
                    if(filterOptional.isPresent() && feature.getFilterType().equals("USERID")){
                        if(filterOptional.get().getFilterList().contains(requestEntity.getUserId())){
                            if(filterOptional.get().isAllowAll() && original){
                                allowedFeatures.add(feature.getFeatureId());
                            }
                            if(!feature.isTestAB()){
                                allowedFeatures.add(feature.getFeatureId());
                            }else{
                                if(sortition(feature)){
                                    allowedFeatures.add(feature.getFeatureId());
                                }
                            }
                        }
                    }else if(filterOptional.isPresent() && feature.getFilterType().equals("COMPANYID")){
                        if(filterOptional.get().getFilterList().contains(requestEntity.getCompanyId())){
                            if(filterOptional.get().isAllowAll() && original){
                                allowedFeatures.add(feature.getFeatureId());
                            }
                            if(!feature.isTestAB()){
                                allowedFeatures.add(feature.getFeatureId());
                            }else{
                                if(sortition(feature)){
                                    allowedFeatures.add(feature.getFeatureId());
                                }
                            }
                        }
                    }
                }
                // Id do filtro está vazio mais existe uma feature genérica
                if(feature.getFilterId().isEmpty()
                        && original
                        && feature.isDefaultStateFlag()
                        && feature.getUserId().isEmpty()
                        && feature.getCompanyId().isEmpty()){
                    if(!feature.isTestAB()){
                        allowedFeatures.add(feature.getFeatureId());
                    }else{
                        if(sortition(feature)){
                            allowedFeatures.add(feature.getFeatureId());
                        }
                    }
                }
            }else{
                // se é uma migration entra aqui
                int amountToMigrate = (int) (feature.getTotalOfCompanies() * feature.getMigrationPercentage());
                if(!requestEntity.getCompanyId().isEmpty()){
                    migration = migrationService.findById(feature.getFeatureId());
                    if(migration==null){
                        migration = new Migration();
                        Set<String> set = new HashSet<>();
                        migration.setId(feature.getFeatureId());
                        migration.setUniqueIdentifiers(set);
                    }
                    if(migration.getUniqueIdentifiers().contains(requestEntity.getCompanyId())){
                        allowedFeatures.add(feature.getFeatureId());
                    }else{
                        if((migration.getUniqueIdentifiers().size() + 1) <= amountToMigrate){
                            Set<String> uniqueIdentifiers = migration.getUniqueIdentifiers();
                            uniqueIdentifiers.add(requestEntity.getCompanyId());
                            migration.setUniqueIdentifiers(uniqueIdentifiers);
                            migrationService.save(migration);
                            allowedFeatures.add(feature.getFeatureId());
                        }
                    }
                }
            }
        }
        response.setApplicationId(requestEntity.getApplicationId());
        response.setFeatureIds(allowedFeatures);
        response.setUserId(requestEntity.getUserId());
        response.setCompanyId(requestEntity.getCompanyId());
        response.setFilterAppliedIds(filtersApplied);
        response.setSuccess(true);
        response.setErrorMessage("");
        return response;
    }

    private boolean sortition(Feature feature){
        double p = feature.getProbabilityToAllow();
        if(p == 0.0){
            p=0.5;
        }
        boolean sorted = false;
        if(Math.random() <= p){
            sorted = true;
        }
        return sorted;
    }

    public Iterable<Feature> findByValue(String value) {
        return featureRepository.findByApplicationIdContainsIgnoreCaseOrNameContainsIgnoreCaseOrCategoryContainingIgnoreCaseOrTypeContainingIgnoreCase(value,value,value,value);
    }

}
