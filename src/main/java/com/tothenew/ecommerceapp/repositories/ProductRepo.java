package com.tothenew.ecommerceapp.repositories;

import com.tothenew.ecommerceapp.entities.product.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product,Long> {

    List<Product> findAll();

}
