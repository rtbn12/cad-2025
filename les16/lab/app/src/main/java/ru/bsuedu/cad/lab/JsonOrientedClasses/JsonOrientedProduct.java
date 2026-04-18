package ru.bsuedu.cad.lab.JsonOrientedClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonOrientedProduct {

    @JsonProperty("name")
    private String name;

    @JsonProperty("categoryName")
    private String categoryName;

    @JsonProperty("stockQuantity")
    private Long stockQuantity;

    public JsonOrientedProduct(){

    }

    public JsonOrientedProduct(String name, String categoryName, Long stockQuantity) {
        this.name = name;
        this.categoryName = categoryName;
        this.stockQuantity = stockQuantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
