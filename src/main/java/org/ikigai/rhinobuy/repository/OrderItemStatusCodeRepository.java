package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.OrderItemStatusCode;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderItemStatusCode entity.
 */
@SuppressWarnings("unused")
public interface OrderItemStatusCodeRepository extends JpaRepository<OrderItemStatusCode,Long> {

}
