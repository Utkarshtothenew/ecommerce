package com.tothenew.ecommerceapp.dtos;

import com.tothenew.ecommerceapp.entities.category.Category;
import com.tothenew.ecommerceapp.entities.users.Seller;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Boolean isCancellable;
    private Boolean isReturnable;
    private String brand;
    private Boolean isActive;
    private CategoryDTO categoryDTO;
    private SellerProfileDTO sellerProfileDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean getReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public SellerProfileDTO getSellerProfileDTO() {
        return sellerProfileDTO;
    }

    public void setSellerProfileDTO(SellerProfileDTO sellerProfileDTO) {
        this.sellerProfileDTO = sellerProfileDTO;
    }
}
