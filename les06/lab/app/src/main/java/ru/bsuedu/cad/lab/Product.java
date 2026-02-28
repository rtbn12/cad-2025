package ru.bsuedu.cad.lab;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component("product")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Product {

    public long productId;
    public String name;
    public String description;
    public int categoryId;
    public BigDecimal price;
    public int stockQuantity;
    public String imageUrl;
    public Date createdAt;
    public Date updatedAt;

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Product(long productId, String name, String description,
                   int categoryId, BigDecimal price, int stockQuantity,
                   String imageUrl, Date createdAt, Date updatedAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
