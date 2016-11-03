package org.ingenia.rhinobuy.repository.search;

import org.ingenia.rhinobuy.domain.ReferencePaymentMethod;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ReferencePaymentMethod entity.
 */
public interface ReferencePaymentMethodSearchRepository extends ElasticsearchRepository<ReferencePaymentMethod, Long> {
}
