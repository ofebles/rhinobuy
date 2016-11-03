package org.ingenia.rhinobuy.repository;

import org.ingenia.rhinobuy.domain.Orders;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Orders entity.
 */
@SuppressWarnings("unused")
public interface OrdersRepository extends JpaRepository<Orders,Long> {

}
