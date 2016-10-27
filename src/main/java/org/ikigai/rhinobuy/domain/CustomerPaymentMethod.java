package org.ikigai.rhinobuy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomerPaymentMethod.
 */
@Entity
@Table(name = "customer_payment_method")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerPaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @Column(name = "details")
    private String details;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private ReferencePaymentMethod reference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CustomerPaymentMethod code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public CustomerPaymentMethod creditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getDetails() {
        return details;
    }

    public CustomerPaymentMethod details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerPaymentMethod customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ReferencePaymentMethod getReference() {
        return reference;
    }

    public CustomerPaymentMethod reference(ReferencePaymentMethod referencePaymentMethod) {
        this.reference = referencePaymentMethod;
        return this;
    }

    public void setReference(ReferencePaymentMethod referencePaymentMethod) {
        this.reference = referencePaymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerPaymentMethod customerPaymentMethod = (CustomerPaymentMethod) o;
        if(customerPaymentMethod.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerPaymentMethod.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerPaymentMethod{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", creditCardNumber='" + creditCardNumber + "'" +
            ", details='" + details + "'" +
            '}';
    }
}
