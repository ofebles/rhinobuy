package org.ingenia.rhinobuy.repository;

import org.ingenia.rhinobuy.domain.ReferenceInvoiceStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ReferenceInvoiceStatus entity.
 */
@SuppressWarnings("unused")
public interface ReferenceInvoiceStatusRepository extends JpaRepository<ReferenceInvoiceStatus,Long> {

}
