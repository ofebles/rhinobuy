package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.ReferencePaymentMethod;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ReferencePaymentMethod entity.
 */
@SuppressWarnings("unused")
public interface ReferencePaymentMethodRepository extends JpaRepository<ReferencePaymentMethod,Long> {

}
