package com.tothenew.ecommerceapp.services;

import com.tothenew.ecommerceapp.entities.product.Product;
import com.tothenew.ecommerceapp.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public abstract class RedisService implements ProductRepo {

    private static final String TABLE_NAME = "Product";

    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, Long, Product> hashOperations;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public Product finById(Long id) {
        return hashOperations.get(TABLE_NAME, id);
    }
//    @Override
//    public Map<Long, Product> findAll() {
//        return (Map<Long, Product>) hashOperations.values("Products");
//    }
}
