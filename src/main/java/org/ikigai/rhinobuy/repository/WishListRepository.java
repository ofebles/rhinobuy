package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.WishList;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the WishList entity.
 */
@SuppressWarnings("unused")
public interface WishListRepository extends JpaRepository<WishList,Long> {

    @Query("select distinct wishList from WishList wishList left join fetch wishList.products")
    List<WishList> findAllWithEagerRelationships();

    @Query("select wishList from WishList wishList left join fetch wishList.products where wishList.id =:id")
    WishList findOneWithEagerRelationships(@Param("id") Long id);

}
