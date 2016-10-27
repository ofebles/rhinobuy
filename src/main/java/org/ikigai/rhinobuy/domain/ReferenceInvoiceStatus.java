package org.ikigai.rhinobuy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ReferenceInvoiceStatus.
 */
@Entity
@Table(name = "reference_invoice_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReferenceInvoiceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "invoice_details")
    private String invoiceDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public ReferenceInvoiceStatus number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public ReferenceInvoiceStatus invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceDetails() {
        return invoiceDetails;
    }

    public ReferenceInvoiceStatus invoiceDetails(String invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
        return this;
    }

    public void setInvoiceDetails(String invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReferenceInvoiceStatus referenceInvoiceStatus = (ReferenceInvoiceStatus) o;
        if(referenceInvoiceStatus.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, referenceInvoiceStatus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReferenceInvoiceStatus{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", invoiceDate='" + invoiceDate + "'" +
            ", invoiceDetails='" + invoiceDetails + "'" +
            '}';
    }
}
