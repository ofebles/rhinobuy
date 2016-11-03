package org.ingenia.rhinobuy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "return_authorization")
    private Boolean returnAuthorization;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;

    @Column(name = "size")
    private String size;

    @NotNull
    @Column(name = "color", nullable = false)
    private String color;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "other_details")
    private String otherDetails;

    @ManyToOne
    private Product product;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductDescription> descriptions = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Picture> images = new HashSet<>();

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Supplier> suppliers = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WishList> wlists = new HashSet<>();

    @ManyToOne
    private ShopingCart shopingCart;

    @ManyToOne
    private Promotion promotion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isReturnAuthorization() {
        return returnAuthorization;
    }

    public Product returnAuthorization(Boolean returnAuthorization) {
        this.returnAuthorization = returnAuthorization;
        return this;
    }

    public void setReturnAuthorization(Boolean returnAuthorization) {
        this.returnAuthorization = returnAuthorization;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public Product size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public Product color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public Product otherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
        return this;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public Product getProduct() {
        return product;
    }

    public Product product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Product products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Product addProduct(Product product) {
        products.add(product);
        product.setProduct(this);
        return this;
    }

    public Product removeProduct(Product product) {
        products.remove(product);
        product.setProduct(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<ProductDescription> getDescriptions() {
        return descriptions;
    }

    public Product descriptions(Set<ProductDescription> productDescriptions) {
        this.descriptions = productDescriptions;
        return this;
    }

    public Product addDescription(ProductDescription productDescription) {
        descriptions.add(productDescription);
        productDescription.setProduct(this);
        return this;
    }

    public Product removeDescription(ProductDescription productDescription) {
        descriptions.remove(productDescription);
        productDescription.setProduct(null);
        return this;
    }

    public void setDescriptions(Set<ProductDescription> productDescriptions) {
        this.descriptions = productDescriptions;
    }

    public Set<Picture> getImages() {
        return images;
    }

    public Product images(Set<Picture> pictures) {
        this.images = pictures;
        return this;
    }

    public Product addImage(Picture picture) {
        images.add(picture);
        picture.setProduct(this);
        return this;
    }

    public Product removeImage(Picture picture) {
        images.remove(picture);
        picture.setProduct(null);
        return this;
    }

    public void setImages(Set<Picture> pictures) {
        this.images = pictures;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Supplier> getSuppliers() {
        return suppliers;
    }

    public Product suppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
        return this;
    }

    public Product addSupplier(Supplier supplier) {
        suppliers.add(supplier);
        supplier.getProducts().add(this);
        return this;
    }

    public Product removeSupplier(Supplier supplier) {
        suppliers.remove(supplier);
        supplier.getProducts().remove(this);
        return this;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public Set<WishList> getWlists() {
        return wlists;
    }

    public Product wlists(Set<WishList> wishLists) {
        this.wlists = wishLists;
        return this;
    }

    public Product addWlist(WishList wishList) {
        wlists.add(wishList);
        wishList.getProducts().add(this);
        return this;
    }

    public Product removeWlist(WishList wishList) {
        wlists.remove(wishList);
        wishList.getProducts().remove(this);
        return this;
    }

    public void setWlists(Set<WishList> wishLists) {
        this.wlists = wishLists;
    }

    public ShopingCart getShopingCart() {
        return shopingCart;
    }

    public Product shopingCart(ShopingCart shopingCart) {
        this.shopingCart = shopingCart;
        return this;
    }

    public void setShopingCart(ShopingCart shopingCart) {
        this.shopingCart = shopingCart;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Product promotion(Promotion promotion) {
        this.promotion = promotion;
        return this;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if(product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", returnAuthorization='" + returnAuthorization + "'" +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", size='" + size + "'" +
            ", color='" + color + "'" +
            ", description='" + description + "'" +
            ", otherDetails='" + otherDetails + "'" +
            '}';
    }
}
