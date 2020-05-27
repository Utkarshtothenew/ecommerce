package com.tothenew.ecommerceapp.repositories;

import com.tothenew.ecommerceapp.entities.product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends CrudRepository<Product, Long> {


    //@Query(value = "select * from product where id=:id",nativeQuery = true)
    Optional<Product> findById(Long id);


    List<Product> findAll();
    @Query(value = "select * from product WHERE   created_date >= NOW() - INTERVAL 1 DAY",nativeQuery = true)
    List<Product> findProductsCreatedInLast24Hours();

    @Query(value = "select * from product where category_id=:id",nativeQuery = true)
    List<Product> findByCategoryId(@Param("id") Long id);

}
