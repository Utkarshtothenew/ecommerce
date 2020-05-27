package com.tothenew.ecommerceapp.services;

import com.tothenew.ecommerceapp.dtos.ProductDTO;
import com.tothenew.ecommerceapp.entities.product.Product;
import com.tothenew.ecommerceapp.entities.users.Seller;
import com.tothenew.ecommerceapp.exceptions.ResourceNotFoundException;
import com.tothenew.ecommerceapp.repositories.CategoryRepo;
import com.tothenew.ecommerceapp.repositories.ProductRepo;
import com.tothenew.ecommerceapp.repositories.ProductVariationRepo;
import com.tothenew.ecommerceapp.repositories.SellerRepo;
import com.tothenew.ecommerceapp.utils.SendEmail;
import com.tothenew.ecommerceapp.utils.UserEmailFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    UserEmailFromToken userEmailFromToken;

    @Autowired
    SendEmail sendEmail;

    @Autowired
    ProductVariationRepo productVariationRepo;



    public String addProduct(HttpServletRequest request, String name, String brand, Long categoryId, Optional<String> desc, Optional<Boolean> isCancellable, Optional<Boolean> isReturnable,String sellerEmail) {
        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setActive(false);
        product.setDeleted(false);
        product.setCreatedDate(new Date());
        Seller seller = sellerRepo.findByEmail(sellerEmail);
        product.setSeller(seller);



        if (desc.isPresent()) {
            product.setDescription(desc.get());
        }
        if (isCancellable.isPresent()) {
            product.setCancellable(isCancellable.get());
        }
        if (!isCancellable.isPresent()) {
            product.setCancellable(true);
        }
        if (isReturnable.isPresent()) {
            product.setReturnable(isReturnable.get());
        }
        if (!isReturnable.isPresent()) {
            product.setReturnable(true);
        }
        seller.getProducts().add(product);

        productRepo.save(product);

        sellerRepo.save(seller);

        return "Success";
    }
    public String deleteProductById(Long id, HttpServletRequest request) {
        String userEmail = userEmailFromToken.getUserEmail(request);
        Optional<Product> product = productRepo.findById(id);
        if (!product.isPresent()) {
            //Exception
        }
        Seller seller = sellerRepo.findByEmail(userEmail);

        product.get().setDeleted(true);
        productRepo.save(product.get());
        return "Success";
    }

    public List<Product> viewAllProduct(HttpServletRequest request){
        return (List<Product>) productRepo.findAll();

    }
     public ProductDTO viewProduct(Long id){
        Product product= productRepo.findById(id).get();
            ProductDTO productDTO=new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setBrand(product.getBrand());

            return productDTO;

     }

    public String activateDeactivateProduct(Long productId,Boolean activeStatus){
        Optional<Product> product = productRepo.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(productId+" not found");
        }
        if(activeStatus){
            product.get().setActive(activeStatus);
            productRepo.save(product.get());
            return "Success";
        }
        product.get().setActive(activeStatus);
        productRepo.save(product.get());
        return "Success";

    }








//    @Scheduled(cron="0 0 1/12 * * ?")
//    public void deActivateOutOfStockProductVariation() {
//           productVariationRepo.deactiveProduct(1);
//
//        }


}


