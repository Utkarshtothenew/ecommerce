package com.tothenew.ecommerceapp.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.tothenew.ecommerceapp.dtos.ProductDTO;
import com.tothenew.ecommerceapp.entities.product.Product;
//import com.tothenew.ecommerceapp.services.RedisService;
import com.tothenew.ecommerceapp.repositories.ProductRepo;
import com.tothenew.ecommerceapp.services.ProductService;
import com.tothenew.ecommerceapp.services.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

//    @Autowired
//    RedisService redisService;

    @Autowired
    ProductRepo productRepo;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

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

    @Cacheable(value= "allProducts",key = "#id")
    @GetMapping("/view/{id}")
    public ProductDTO viewById(@PathVariable Long id, HttpServletRequest request){

        LOG.info("Getting user with ID {}.", id);

        return productService.viewProduct(id);

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

    @GetMapping("/productsCreatedIn24Hours/{id}/{date}")
    public void exportCSV(@PathVariable Long id, @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date, HttpServletResponse response) throws Exception {

        String filename = "products.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<Product> writer = new StatefulBeanToCsvBuilder<Product>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();


//        //Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        String strDate = sdfDate.format(date);
        LOG.info("date "+strDate);
        writer.write(productRepo.findProductsCreatedInLast24Hours(strDate,id));
        LOG.info("Success");


    }


    }


