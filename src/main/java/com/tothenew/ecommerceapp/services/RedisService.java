//package com.tothenew.ecommerceapp.services;
//
//import com.tothenew.ecommerceapp.entities.product.Product;
//import com.tothenew.ecommerceapp.repositories.ProductRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class RedisService implements ProductRepo {
//
//    private static final String TABLE_NAME = "Product";
//
//    private RedisTemplate<String, Object> redisTemplate;
//
//    private HashOperations<String, Product, Product> hashOperations;
//
//    @Autowired
//    public RedisService(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public Optional<Product> findById(Long id) {
//        return Optional.of(hashOperations.get(TABLE_NAME, id));
//    }
//    @Override
//    public List<Product> findAll() {
//        return hashOperations.values("Products");
//    }
//    @Override
//    public List<Product> findByCategoryId(Long id){
//        return hashOperations.values("Products");
//    }
//    @Override
//    public void deleteAll(){
//
//    }
//}
