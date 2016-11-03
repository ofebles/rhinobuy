package org.ingenia.rhinobuy.repository.search;

import org.ingenia.rhinobuy.domain.ReferenceInvoiceStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ReferenceInvoiceStatus entity.
 */
public interface ReferenceInvoiceStatusSearchRepository extends ElasticsearchRepository<ReferenceInvoiceStatus, Long> {
}
