package com.coolcook.repository.search;

import com.coolcook.domain.IngredientAtHome;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IngredientAtHome entity.
 */
public interface IngredientAtHomeSearchRepository extends ElasticsearchRepository<IngredientAtHome, Long> {
}
