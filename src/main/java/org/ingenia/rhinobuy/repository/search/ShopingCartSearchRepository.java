package org.ingenia.rhinobuy.repository.search;

import org.ingenia.rhinobuy.domain.ShopingCart;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ShopingCart entity.
 */
public interface ShopingCartSearchRepository extends ElasticsearchRepository<ShopingCart, Long> {
}
