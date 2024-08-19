package br.com.alfa11.msfeatureflag.repository;

import br.com.alfa11.msfeatureflag.model.Feature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends CrudRepository<Feature, String> {

    List<Feature> findAllByApplicationIdAndStatusAndFilterType(String applicationId, boolean status, String filterType);
//    List<Feature> findAll();
//    List<Feature> findByApplicationId(String applicationId);
    List<Feature> findByApplicationIdAndStatus(String applicationId, boolean status);
//    List<Feature> findByApplicationIdAndStatusAndFilterType(String applicationId, boolean status, String filterType);
    List<Feature> findByApplicationIdContainsIgnoreCaseOrNameContainsIgnoreCaseOrCategoryContainingIgnoreCaseOrTypeContainingIgnoreCase(String applicationId, String name, String category, String type);

    List<Feature> findByStatus(boolean status);
}
