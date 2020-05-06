package com.tothenew.ecommerceapp.repositories;

import com.tothenew.ecommerceapp.entities.product.ProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepo extends CrudRepository<ProductVariation,Long> {
    @Query(value = "update user set is_active=false WHERE  quantity_available< :quantity",nativeQuery = true)
    void activproduct(@Param("quantity") int quantity);

}
