package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.Shipment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Shipment entity.
 */
@SuppressWarnings("unused")
public interface ShipmentRepository extends JpaRepository<Shipment,Long> {

}
