package org.ingenia.rhinobuy.repository;

import org.ingenia.rhinobuy.domain.ReferenceLanguage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ReferenceLanguage entity.
 */
@SuppressWarnings("unused")
public interface ReferenceLanguageRepository extends JpaRepository<ReferenceLanguage,Long> {

}
