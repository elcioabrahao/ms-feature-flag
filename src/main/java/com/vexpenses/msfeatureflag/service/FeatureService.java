package com.vexpenses.msfeatureflag.service;

import com.vexpenses.msfeatureflag.config.FilterType;
import com.vexpenses.msfeatureflag.model.Feature;
import com.vexpenses.msfeatureflag.model.Filter;
import com.vexpenses.msfeatureflag.model.RequestEntity;
import com.vexpenses.msfeatureflag.model.ResponseEntity;
import com.vexpenses.msfeatureflag.repository.FeatureRepository;
import com.vexpenses.msfeatureflag.util.DataUtil;
import com.vexpenses.msfeatureflag.util.UlidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FeatureService {
    @Autowired
    FeatureRepository featureRepository;
    @Autowired
    FilterService filterService;

    public Iterable<Feature> findAll() {
        return featureRepository.findAll();
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
            }else
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
            } else{
                if(!feature.isDefaultStateFlag()){
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
        List<Feature> featureList;
        ResponseEntity response = new ResponseEntity();
        List<String> allowedFeatures =  new ArrayList<>();
        List<String> filtersApplied = new ArrayList<>();
        String message = "";
        if(!requestEntity.getUserId().isEmpty() && requestEntity.getCompanyId().isEmpty()){
            featureList = featureRepository.findByApplicationIdAndStatusAndFilterType(requestEntity.getApplicationId(), true, FilterType.USERID.name());
        } else if (requestEntity.getUserId().isEmpty() && !requestEntity.getCompanyId().isEmpty()) {
            featureList = featureRepository.findByApplicationIdAndStatusAndFilterType(requestEntity.getApplicationId(), true, FilterType.COMPANYID.name());
        } else {
            featureList = new ArrayList<>();
        }
        if(featureList.isEmpty()){
            response.setApplicationId(requestEntity.getApplicationId());
            response.setFeatureIds(new ArrayList<>());
            response.setUserId(requestEntity.getUserId());
            response.setCompanyId(requestEntity.getCompanyId());
            response.setFilterAppliedIds(new ArrayList<>());
            response.setSuccess(false);
            response.setErrorMessage("Não foram localizados features para esta requisição: "+requestEntity.toString());
            return response;
        }
        for(Feature feature : featureList){
            boolean original = true;
            Optional<Filter> filterOptional;
            if(!feature.getDateTimeBegin().isEmpty()){
                original = DataUtil.dateIsBeforeNow(feature.getDateTimeBegin());
                message+="Feature "+feature.getFeatureId()+": Data de início vigência não atingida: "+feature.getDateTimeBegin();
            }
            if(!feature.getDateTimeEnd().isEmpty() && original){
                original = DataUtil.dateIsAfterNow(feature.getDateTimeEnd());
                message+="Feature "+feature.getFeatureId()+": Data de final vigência ultrapassada: "+feature.getDateTimeBegin();
            }
            if(!feature.getFilterId().isEmpty() && original){
                filterOptional = filterService.getFilterById(feature.getFeatureId());
                if(filterOptional.isPresent() && feature.getFilterType()==FilterType.USERID){
                    if(filterOptional.get().getFilterList().contains(feature.getUserId())){
                        if(filterOptional.get().isAllowAll() && original){
                            allowedFeatures.add(feature.getFeatureId());
                        }
                        filtersApplied.add(filterOptional.get().getFilterId());
                    }
                }else if(filterOptional.isPresent() && feature.getFilterType()==FilterType.COMPANYID){
                    if(filterOptional.get().getFilterList().contains(feature.getCompanyId())){
                        if(filterOptional.get().isAllowAll() && original){
                            allowedFeatures.add(feature.getFeatureId());
                        }
                        filtersApplied.add(filterOptional.get().getFilterId());
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


}
