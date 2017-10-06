package com.coolcook.repository.search;

import com.coolcook.domain.RecipeMaster;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RecipeMaster entity.
 */
public interface RecipeMasterSearchRepository extends ElasticsearchRepository<RecipeMaster, Long> {
}
