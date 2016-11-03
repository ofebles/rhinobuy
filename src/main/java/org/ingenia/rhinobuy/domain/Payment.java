package org.ingenia.rhinobuy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "ammount", precision=10, scale=2)
    private BigDecimal ammount;

    @ManyToOne
    private CustomerPaymentMethod method;

    @ManyToOne
    private Invoice invoice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public Payment paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public Payment ammount(BigDecimal ammount) {
        this.ammount = ammount;
        return this;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public CustomerPaymentMethod getMethod() {
        return method;
    }

    public Payment method(CustomerPaymentMethod customerPaymentMethod) {
        this.method = customerPaymentMethod;
        return this;
    }

    public void setMethod(CustomerPaymentMethod customerPaymentMethod) {
        this.method = customerPaymentMethod;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Payment invoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment payment = (Payment) o;
        if(payment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + id +
            ", paymentDate='" + paymentDate + "'" +
            ", ammount='" + ammount + "'" +
            '}';
    }
}
