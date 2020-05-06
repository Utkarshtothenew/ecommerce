package com.tothenew.ecommerceapp.controllers;

import com.tothenew.ecommerceapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    }


