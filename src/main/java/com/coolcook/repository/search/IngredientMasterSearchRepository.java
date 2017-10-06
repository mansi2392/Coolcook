package com.coolcook.repository.search;

import com.coolcook.domain.IngredientMaster;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IngredientMaster entity.
 */
public interface IngredientMasterSearchRepository extends ElasticsearchRepository<IngredientMaster, Long> {
}
