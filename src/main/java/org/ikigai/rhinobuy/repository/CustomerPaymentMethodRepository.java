package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.CustomerPaymentMethod;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerPaymentMethod entity.
 */
@SuppressWarnings("unused")
public interface CustomerPaymentMethodRepository extends JpaRepository<CustomerPaymentMethod,Long> {

}
