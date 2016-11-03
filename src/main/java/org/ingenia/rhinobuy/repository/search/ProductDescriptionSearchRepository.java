package org.ingenia.rhinobuy.repository.search;

import org.ingenia.rhinobuy.domain.ProductDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProductDescription entity.
 */
public interface ProductDescriptionSearchRepository extends ElasticsearchRepository<ProductDescription, Long> {
}
