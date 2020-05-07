package com.tothenew.ecommerceapp.dtos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ProductVariationDTO {
    private Long productId;
    private Map<String, HashSet<String>> filedIdValues;
    private String primaryImage;
    private Integer quantityAvailable;
    private Long price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Map<String, Object> getFiledIdValues() {
        return filedIdValues;
    }

    public void setFiledIdValues(HashMap<String, HashSet<String>> filedIdValues) {
        this.filedIdValues = filedIdValues;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }


    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductVariationDTO{" +
                "productId=" + productId +
                ", filedIdValues=" + filedIdValues +
                ", primaryImage='" + primaryImage + '\'' +
                ", quantityAvailable=" + quantityAvailable +
                ", price=" + price +
                '}';
    }
}
