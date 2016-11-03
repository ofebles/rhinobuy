package org.ingenia.rhinobuy.repository.search;

import org.ingenia.rhinobuy.domain.WishList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the WishList entity.
 */
public interface WishListSearchRepository extends ElasticsearchRepository<WishList, Long> {
}
