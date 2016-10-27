package org.ikigai.rhinobuy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A OrderItem.
 */
@Entity
@Table(name = "order_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "quantity", precision=10, scale=2)
    private BigDecimal quantity;

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @Column(name = "details")
    private String details;

    @ManyToOne
    private Orders orders;

    @ManyToOne
    private OrderItemStatusCode status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public OrderItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderItem price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public OrderItem details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Orders getOrders() {
        return orders;
    }

    public OrderItem orders(Orders orders) {
        this.orders = orders;
        return this;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public OrderItemStatusCode getStatus() {
        return status;
    }

    public OrderItem status(OrderItemStatusCode orderItemStatusCode) {
        this.status = orderItemStatusCode;
        return this;
    }

    public void setStatus(OrderItemStatusCode orderItemStatusCode) {
        this.status = orderItemStatusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        if(orderItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + id +
            ", quantity='" + quantity + "'" +
            ", price='" + price + "'" +
            ", details='" + details + "'" +
            '}';
    }
}
