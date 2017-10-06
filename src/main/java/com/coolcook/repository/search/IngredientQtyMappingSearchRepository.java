package com.coolcook.repository.search;

import com.coolcook.domain.IngredientQtyMapping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IngredientQtyMapping entity.
 */
public interface IngredientQtyMappingSearchRepository extends ElasticsearchRepository<IngredientQtyMapping, Long> {
}
