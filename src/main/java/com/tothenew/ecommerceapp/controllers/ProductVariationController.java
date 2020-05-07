package com.tothenew.ecommerceapp.controllers;

import com.tothenew.ecommerceapp.dtos.ProductVariationDTO;
import com.tothenew.ecommerceapp.services.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("productVariation")
public class ProductVariationController {

    @Autowired
    ProductVariationService productVariationService;

    @PostMapping("/add")
    public String addProductVariation(@RequestBody ProductVariationDTO productVariationDTO, HttpServletResponse response) {
        String getMessage = productVariationService.add(productVariationDTO);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

}
