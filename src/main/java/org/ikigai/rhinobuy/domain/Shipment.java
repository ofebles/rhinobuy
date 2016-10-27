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
 * A Shipment.
 */
@Entity
@Table(name = "shipment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    @Column(name = "other_details")
    private String otherDetails;

    @ManyToOne
    private Orders orders;

    @OneToMany(mappedBy = "shipment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public Shipment trackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public Shipment shipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
        return this;
    }

    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public Shipment otherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
        return this;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public Orders getOrders() {
        return orders;
    }

    public Shipment orders(Orders orders) {
        this.orders = orders;
        return this;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Shipment invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Shipment addInvoice(Invoice invoice) {
        invoices.add(invoice);
        invoice.setShipment(this);
        return this;
    }

    public Shipment removeInvoice(Invoice invoice) {
        invoices.remove(invoice);
        invoice.setShipment(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shipment shipment = (Shipment) o;
        if(shipment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, shipment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Shipment{" +
            "id=" + id +
            ", trackingNumber='" + trackingNumber + "'" +
            ", shipmentDate='" + shipmentDate + "'" +
            ", otherDetails='" + otherDetails + "'" +
            '}';
    }
}
