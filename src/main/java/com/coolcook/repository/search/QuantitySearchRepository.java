package com.coolcook.repository.search;

import com.coolcook.domain.Quantity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Quantity entity.
 */
public interface QuantitySearchRepository extends ElasticsearchRepository<Quantity, Long> {
}
