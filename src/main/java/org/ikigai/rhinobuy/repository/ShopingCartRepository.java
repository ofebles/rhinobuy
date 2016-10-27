package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.ShopingCart;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShopingCart entity.
 */
@SuppressWarnings("unused")
public interface ShopingCartRepository extends JpaRepository<ShopingCart,Long> {

}
