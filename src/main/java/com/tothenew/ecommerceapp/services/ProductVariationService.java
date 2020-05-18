package com.tothenew.ecommerceapp.services;

import com.tothenew.ecommerceapp.dtos.ProductVariationDTO;
import com.tothenew.ecommerceapp.entities.product.Product;
import com.tothenew.ecommerceapp.entities.product.ProductVariation;
import com.tothenew.ecommerceapp.entities.users.Seller;
import com.tothenew.ecommerceapp.exceptions.ResourceNotFoundException;
import com.tothenew.ecommerceapp.repositories.ProductRepo;
import com.tothenew.ecommerceapp.repositories.ProductVariationRepo;
import com.tothenew.ecommerceapp.repositories.SellerRepo;
import com.tothenew.ecommerceapp.utils.UserEmailFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class ProductVariationService {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductVariationRepo productVariationRepo;
    @Autowired
    UserEmailFromToken userEmailFromToken;

    @Autowired
    SellerRepo sellerRepo;

    public String add(ProductVariationDTO productVariationDTO) {
        Optional<Product> product = productRepo.findById(productVariationDTO.getProductId());
        if (productVariationDTO.getQuantityAvailable() != null && productVariationDTO.getQuantityAvailable() <= 0) {
            return "quantity should be 0 or more";
        }
        if (productVariationDTO.getPrice() != null && productVariationDTO.getPrice() <= 0) {
            return "price should be 0 or more";
        }
        if (!product.get().getActive()) {
            return "product is not active";
        }
        if (product.get().getDeleted()) {
            return "product is deleted";
        }
        ProductVariation productVariation = new ProductVariation();
        productVariation.setMetadata(productVariationDTO.getFiledIdValues());
        productVariation.setActive(true);
        productVariation.setProduct(product.get());
        product.get().getProductVariations().add(productVariation);
        productVariation.setQuantityAvailable(productVariationDTO.getQuantityAvailable().longValue());
        productVariation.setPrice(productVariationDTO.getPrice());
        productVariationRepo.save(productVariation);
        productRepo.save(product.get());
        return "Success";
    }

    public ProductVariation viewProductVariation(Long id, HttpServletRequest request) {
        String userEmail = userEmailFromToken.getUserEmail(request);
        Seller seller = sellerRepo.findByEmail(userEmail);
        Optional<ProductVariation> productVariation = productVariationRepo.findById(id);
        if (!productVariation.isPresent()) {
            throw new ResourceNotFoundException(id + " product variation not found");
        }
        if (!productVariation.get().getProduct().getSeller().getId().equals(seller.getId())) {
            throw new ResourceNotFoundException("invalid seller");
        }
        return productVariation.get();

    }
}