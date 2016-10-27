package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.Promotion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Promotion entity.
 */
@SuppressWarnings("unused")
public interface PromotionRepository extends JpaRepository<Promotion,Long> {

}
