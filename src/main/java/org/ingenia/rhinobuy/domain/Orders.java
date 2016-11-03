package org.ingenia.rhinobuy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_placed")
    private ZonedDateTime datePlaced;

    @Column(name = "details")
    private String details;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "orders")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Shipment> shipments = new HashSet<>();

    @OneToMany(mappedBy = "orders")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    private Product productos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDatePlaced() {
        return datePlaced;
    }

    public Orders datePlaced(ZonedDateTime datePlaced) {
        this.datePlaced = datePlaced;
        return this;
    }

    public void setDatePlaced(ZonedDateTime datePlaced) {
        this.datePlaced = datePlaced;
    }

    public String getDetails() {
        return details;
    }

    public Orders details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Orders invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Orders addInvoice(Invoice invoice) {
        invoices.add(invoice);
        invoice.setOrder(this);
        return this;
    }

    public Orders removeInvoice(Invoice invoice) {
        invoices.remove(invoice);
        invoice.setOrder(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<Shipment> getShipments() {
        return shipments;
    }

    public Orders shipments(Set<Shipment> shipments) {
        this.shipments = shipments;
        return this;
    }

    public Orders addShipment(Shipment shipment) {
        shipments.add(shipment);
        shipment.setOrders(this);
        return this;
    }

    public Orders removeShipment(Shipment shipment) {
        shipments.remove(shipment);
        shipment.setOrders(null);
        return this;
    }

    public void setShipments(Set<Shipment> shipments) {
        this.shipments = shipments;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public Orders items(Set<OrderItem> orderItems) {
        this.items = orderItems;
        return this;
    }

    public Orders addItem(OrderItem orderItem) {
        items.add(orderItem);
        orderItem.setOrders(this);
        return this;
    }

    public Orders removeItem(OrderItem orderItem) {
        items.remove(orderItem);
        orderItem.setOrders(null);
        return this;
    }

    public void setItems(Set<OrderItem> orderItems) {
        this.items = orderItems;
    }

    public Product getProductos() {
        return productos;
    }

    public Orders productos(Product product) {
        this.productos = product;
        return this;
    }

    public void setProductos(Product product) {
        this.productos = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orders orders = (Orders) o;
        if(orders.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orders.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + id +
            ", datePlaced='" + datePlaced + "'" +
            ", details='" + details + "'" +
            '}';
    }
}
