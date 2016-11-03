package org.ingenia.rhinobuy.repository.search;

import org.ingenia.rhinobuy.domain.CustomerPaymentMethod;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CustomerPaymentMethod entity.
 */
public interface CustomerPaymentMethodSearchRepository extends ElasticsearchRepository<CustomerPaymentMethod, Long> {
}
