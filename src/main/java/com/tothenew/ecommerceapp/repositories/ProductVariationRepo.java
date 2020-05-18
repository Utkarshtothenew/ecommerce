package com.tothenew.ecommerceapp.repositories;

import com.tothenew.ecommerceapp.entities.product.ProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepo extends CrudRepository<ProductVariation,Long> {
    @Query(value = "update product_variation set is_active=false WHERE  quantity_available < :quantity",nativeQuery = true)
    void deactiveProduct(@Param("quantity") int quantity);


}
