package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.Supplier;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Supplier entity.
 */
@SuppressWarnings("unused")
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    @Query("select distinct supplier from Supplier supplier left join fetch supplier.products")
    List<Supplier> findAllWithEagerRelationships();

    @Query("select supplier from Supplier supplier left join fetch supplier.products where supplier.id =:id")
    Supplier findOneWithEagerRelationships(@Param("id") Long id);

}
