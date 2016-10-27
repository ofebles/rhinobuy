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
 * A ShopingCart.
 */
@Entity
@Table(name = "shoping_cart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShopingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "shopingCart")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> productos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public ShopingCart date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ShopingCart customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Product> getProductos() {
        return productos;
    }

    public ShopingCart productos(Set<Product> products) {
        this.productos = products;
        return this;
    }

    public ShopingCart addProductos(Product product) {
        productos.add(product);
        product.setShopingCart(this);
        return this;
    }

    public ShopingCart removeProductos(Product product) {
        productos.remove(product);
        product.setShopingCart(null);
        return this;
    }

    public void setProductos(Set<Product> products) {
        this.productos = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShopingCart shopingCart = (ShopingCart) o;
        if(shopingCart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, shopingCart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ShopingCart{" +
            "id=" + id +
            ", date='" + date + "'" +
            '}';
    }
}
