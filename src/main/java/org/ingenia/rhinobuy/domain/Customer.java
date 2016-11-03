package org.ingenia.rhinobuy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.ingenia.rhinobuy.domain.enumeration.CustomerType;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CustomerType type;

    @Column(name = "organization_name")
    private String organizationName;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private WishList wishlist;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShopingCart> shopingcarts = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CustomerPaymentMethod> paymentmethods = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerType getType() {
        return type;
    }

    public Customer type(CustomerType type) {
        this.type = type;
        return this;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public Customer organizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Customer middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Customer phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public Customer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public Customer country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public WishList getWishlist() {
        return wishlist;
    }

    public Customer wishlist(WishList wishList) {
        this.wishlist = wishList;
        return this;
    }

    public void setWishlist(WishList wishList) {
        this.wishlist = wishList;
    }

    public User getUser() {
        return user;
    }

    public Customer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ShopingCart> getShopingcarts() {
        return shopingcarts;
    }

    public Customer shopingcarts(Set<ShopingCart> shopingCarts) {
        this.shopingcarts = shopingCarts;
        return this;
    }

    public Customer addShopingcart(ShopingCart shopingCart) {
        shopingcarts.add(shopingCart);
        shopingCart.setCustomer(this);
        return this;
    }

    public Customer removeShopingcart(ShopingCart shopingCart) {
        shopingcarts.remove(shopingCart);
        shopingCart.setCustomer(null);
        return this;
    }

    public void setShopingcarts(Set<ShopingCart> shopingCarts) {
        this.shopingcarts = shopingCarts;
    }

    public Set<CustomerPaymentMethod> getPaymentmethods() {
        return paymentmethods;
    }

    public Customer paymentmethods(Set<CustomerPaymentMethod> customerPaymentMethods) {
        this.paymentmethods = customerPaymentMethods;
        return this;
    }

    public Customer addPaymentmethod(CustomerPaymentMethod customerPaymentMethod) {
        paymentmethods.add(customerPaymentMethod);
        customerPaymentMethod.setCustomer(this);
        return this;
    }

    public Customer removePaymentmethod(CustomerPaymentMethod customerPaymentMethod) {
        paymentmethods.remove(customerPaymentMethod);
        customerPaymentMethod.setCustomer(null);
        return this;
    }

    public void setPaymentmethods(Set<CustomerPaymentMethod> customerPaymentMethods) {
        this.paymentmethods = customerPaymentMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        if(customer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", organizationName='" + organizationName + "'" +
            ", firstName='" + firstName + "'" +
            ", middleName='" + middleName + "'" +
            ", lastName='" + lastName + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            ", city='" + city + "'" +
            ", country='" + country + "'" +
            ", address='" + address + "'" +
            '}';
    }
}
