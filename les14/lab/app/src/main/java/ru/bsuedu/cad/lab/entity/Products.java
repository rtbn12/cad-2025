package ru.bsuedu.cad.lab.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", unique = true, nullable = false, length = 150)
    private String name;

    @Column(name = "description", unique = false, nullable = true, length = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;

    @Column(name = "price", unique = false, nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity", unique = false, nullable = false)
    private Long stockQuantity;

    @Column(name = "image_url", unique = false, nullable = true, length = 250)
    private String imageUrl;

    @Column(name = "created_at", unique = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", unique = false, nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore  // <-- ДОБАВИТЬ ЭТУ СТРОКУ
    private List<Order_Details> orderDetails = new ArrayList<>();

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Order_Details> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<Order_Details> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
