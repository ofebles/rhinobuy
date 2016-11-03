package org.ingenia.rhinobuy.repository.search;

import org.ingenia.rhinobuy.domain.Shipment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Shipment entity.
 */
public interface ShipmentSearchRepository extends ElasticsearchRepository<Shipment, Long> {
}
