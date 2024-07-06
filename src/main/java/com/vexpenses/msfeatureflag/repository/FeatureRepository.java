package com.vexpenses.msfeatureflag.repository;

import com.vexpenses.msfeatureflag.model.Feature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends CrudRepository<Feature, String> {

    List<Feature> getFeatureByApplicationId(String applicationId);

}
