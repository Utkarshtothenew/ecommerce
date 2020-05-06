package com.tothenew.ecommerceapp.repositories;

import com.tothenew.ecommerceapp.entities.product.Product;
import org.hibernate.annotations.Parent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends CrudRepository<Product,Long> {



}
