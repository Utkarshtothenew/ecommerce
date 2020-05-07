package com.tothenew.ecommerceapp.services;

import com.tothenew.ecommerceapp.dtos.ProductVariationDTO;
import com.tothenew.ecommerceapp.entities.product.Product;
import com.tothenew.ecommerceapp.entities.product.ProductVariation;
import com.tothenew.ecommerceapp.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductVariationService {
    @Autowired
    ProductRepo productRepo;

    public String add(ProductVariationDTO productVariationDTO){
        Optional<Product> product = productRepo.findById(productVariationDTO.getProductId());
        ProductVariation productVariation= new ProductVariation();
        productVariation.setMetadata(productVariationDTO.getFiledIdValues());
        productVariation.setActive(true);
        productVariation.setProduct(product.get());
        productVariation.setQuantityAvailable(productVariationDTO.getQuantityAvailable().longValue());
        productVariation.setPrice(productVariationDTO.getPrice());
        return "Success";
    }
}