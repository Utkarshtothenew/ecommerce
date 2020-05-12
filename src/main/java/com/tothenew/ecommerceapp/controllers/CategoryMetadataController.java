package com.tothenew.ecommerceapp.controllers;

import com.tothenew.ecommerceapp.dtos.CategoryMetadataDTO;
import com.tothenew.ecommerceapp.services.CategoryMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categoryMetadataValues")
public class CategoryMetadataController {
    @Autowired
    CategoryMetadataService categoryMetadataService;

    @PostMapping("/add")
    public String addCategoryMetadataFieldValues(@RequestBody CategoryMetadataDTO categoryMetadataDTO, HttpServletResponse response) {
        String getMessage = categoryMetadataService.addCategoryMetadataValues(categoryMetadataDTO);
        if ("Success".contentEquals(getMessage)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return getMessage;
    }

}
