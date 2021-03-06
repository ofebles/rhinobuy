package org.ingenia.rhinobuy.repository;

import org.ingenia.rhinobuy.domain.ProductDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription,Long> {

}
