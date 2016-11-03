package org.ingenia.rhinobuy.repository;

import org.ingenia.rhinobuy.domain.ReferencePaymentMethod;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ReferencePaymentMethod entity.
 */
@SuppressWarnings("unused")
public interface ReferencePaymentMethodRepository extends JpaRepository<ReferencePaymentMethod,Long> {

}
