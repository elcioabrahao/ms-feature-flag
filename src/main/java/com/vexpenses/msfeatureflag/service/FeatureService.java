package com.vexpenses.msfeatureflag.service;

import com.vexpenses.msfeatureflag.model.Feature;
import com.vexpenses.msfeatureflag.model.ResponseEntity;
import com.vexpenses.msfeatureflag.repository.FeatureRepository;
import com.vexpenses.msfeatureflag.util.DataUtil;
import com.vexpenses.msfeatureflag.util.UlidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
        List<Feature> featureList = featureRepository.getFeatureByApplicationId(applicationId);
        if (featureList.isEmpty()) {
            response.setSuccess(false);
            response.setErrorMessage("Não foram localizados features para esta aplicação: "+applicationId);
            return response;
        }
        featureList.forEach(feature->{
            boolean original = true;
            if(!feature.getDateTimeBegin().isEmpty()){
                original = DataUtil.dateIsBeforeNow(feature.getDateTimeBegin());
            }
            if(!feature.getDateTimeEnd().isEmpty()){
                original = DataUtil.dateIsAfterNow(feature.getDateTimeBegin());
            }
            if(!feature.getFilterId().isEmpty()){
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


}
