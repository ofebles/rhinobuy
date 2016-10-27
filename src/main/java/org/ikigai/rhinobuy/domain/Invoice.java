package org.ikigai.rhinobuy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invoice implements Serializable {

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

    @ManyToOne
    private Shipment shipment;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne
    private ReferenceInvoiceStatus status;

    @ManyToOne
    private Orders order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Invoice number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Invoice invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceDetails() {
        return invoiceDetails;
    }

    public Invoice invoiceDetails(String invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
        return this;
    }

    public void setInvoiceDetails(String invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Invoice shipment(Shipment shipment) {
        this.shipment = shipment;
        return this;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Invoice payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Invoice addPayment(Payment payment) {
        payments.add(payment);
        payment.setInvoice(this);
        return this;
    }

    public Invoice removePayment(Payment payment) {
        payments.remove(payment);
        payment.setInvoice(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public ReferenceInvoiceStatus getStatus() {
        return status;
    }

    public Invoice status(ReferenceInvoiceStatus referenceInvoiceStatus) {
        this.status = referenceInvoiceStatus;
        return this;
    }

    public void setStatus(ReferenceInvoiceStatus referenceInvoiceStatus) {
        this.status = referenceInvoiceStatus;
    }

    public Orders getOrder() {
        return order;
    }

    public Invoice order(Orders orders) {
        this.order = orders;
        return this;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) o;
        if(invoice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", invoiceDate='" + invoiceDate + "'" +
            ", invoiceDetails='" + invoiceDetails + "'" +
            '}';
    }
}
