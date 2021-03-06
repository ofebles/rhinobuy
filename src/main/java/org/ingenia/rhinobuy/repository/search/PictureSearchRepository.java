package org.ingenia.rhinobuy.repository.search;

import org.ingenia.rhinobuy.domain.Picture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Picture entity.
 */
public interface PictureSearchRepository extends ElasticsearchRepository<Picture, Long> {
}
