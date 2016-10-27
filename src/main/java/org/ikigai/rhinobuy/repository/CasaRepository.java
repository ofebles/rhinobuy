package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.Casa;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Casa entity.
 */
@SuppressWarnings("unused")
public interface CasaRepository extends JpaRepository<Casa,Long> {

}
