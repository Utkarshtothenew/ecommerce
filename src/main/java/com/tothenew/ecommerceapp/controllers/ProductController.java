package com.tothenew.ecommerceapp.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.ecommerceapp.entities.product.Product;
import com.tothenew.ecommerceapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public String addProduct(@RequestParam("name") String name, @RequestParam("brand") String brand, @RequestParam("categoryId") Long categoryId, @RequestParam("desc") Optional<String> desc, @RequestParam(name = "isCancellable") Optional<Boolean> isCancellable, @RequestParam(name = "isReturnable") Optional<Boolean> isReturnable, String sellerEmail, HttpServletResponse response, HttpServletRequest request) {
        String getMessage = productService.addProduct(request, name, brand, categoryId, desc, isCancellable, isReturnable, sellerEmail);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }
        @DeleteMapping("/delete/{id}")
        public String deleteProductById(@PathVariable Long id,HttpServletRequest request,HttpServletResponse response) {
            String getMessage = productService.deleteProductById(id,request);
            if ("Success".contentEquals(getMessage)) {
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            return getMessage;
        }
    @GetMapping("/view/all")
    public MappingJacksonValue  viewAll(HttpServletRequest request) {

        List<Product> products= productService.viewAllProduct(request);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","brand","desc");
        FilterProvider filters = new SimpleFilterProvider().addFilter("detailsAboutProduct", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(products);
        mapping.setFilters(filters);

        return mapping;


    }
    @PutMapping("/admin/activate/{productId}")
    public String activateProduct(@PathVariable Long productId,HttpServletResponse response) {
        String getMessage = productService.activateDeactivateProduct(productId,true);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

    @PutMapping("/admin/deactivate/{productId}")
    public String deactivateProduct(@PathVariable Long productId,HttpServletResponse response) {
        String getMessage = productService.activateDeactivateProduct(productId, false);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }


    }


