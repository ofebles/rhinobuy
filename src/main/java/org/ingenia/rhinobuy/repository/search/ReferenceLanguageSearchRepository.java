package org.ingenia.rhinobuy.repository.search;

import org.ingenia.rhinobuy.domain.ReferenceLanguage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ReferenceLanguage entity.
 */
public interface ReferenceLanguageSearchRepository extends ElasticsearchRepository<ReferenceLanguage, Long> {
}
