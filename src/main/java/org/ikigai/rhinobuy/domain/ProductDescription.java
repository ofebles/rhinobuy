package org.ikigai.rhinobuy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductDescription.
 */
@Entity
@Table(name = "product_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private ReferenceLanguage lang;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public ProductDescription content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReferenceLanguage getLang() {
        return lang;
    }

    public ProductDescription lang(ReferenceLanguage referenceLanguage) {
        this.lang = referenceLanguage;
        return this;
    }

    public void setLang(ReferenceLanguage referenceLanguage) {
        this.lang = referenceLanguage;
    }

    public Product getProduct() {
        return product;
    }

    public ProductDescription product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductDescription productDescription = (ProductDescription) o;
        if(productDescription.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productDescription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductDescription{" +
            "id=" + id +
            ", content='" + content + "'" +
            '}';
    }
}
