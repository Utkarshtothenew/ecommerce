package com.tothenew.ecommerceapp.services;

import com.tothenew.ecommerceapp.entities.category.Category;
import com.tothenew.ecommerceapp.exceptions.FieldAlreadyExistException;
import com.tothenew.ecommerceapp.exceptions.ResourceNotFoundException;
import com.tothenew.ecommerceapp.repositories.CategoryMetadataFieldValuesRepo;
import com.tothenew.ecommerceapp.repositories.CategoryRepo;
import com.tothenew.ecommerceapp.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ProductRepo productRepo;



    public String addCategory(String name, Optional<Long> parentId) {
        Category category = new Category();
        if (parentId.isPresent()) {
            if (!productRepo.findByCategoryId(parentId.get()).isEmpty()) {
                return "parent id is already associated with some product";
            }
            List<Category> rootCategories = categoryRepo.findRootCategories();
            rootCategories.forEach(root->{
                if (root.getName().equals(name)) {
                    throw new FieldAlreadyExistException(name + " already a root category");
                }
            });
            List<Optional<Category>> immediateChildren = categoryRepo.findByParentId(parentId.get());

            if (!immediateChildren.isEmpty()) {
                immediateChildren.forEach(ic->{
                    if (ic.get().getName().equals(name)) {
                        throw new FieldAlreadyExistException(name + " already in breadth");
                    }
                });
            }
            Optional<Category> parentCategory = categoryRepo.findById(parentId.get());
            if (getCategoryNameTillRoot(parentCategory.get()).contains(name)) {
                throw new FieldAlreadyExistException(name + " already in depth");
            }
            category.setName(name);
            category.setParentId(categoryRepo.findById(parentId.get()).get());
            categoryRepo.save(category);
            return "Success " + categoryRepo.findByNameAndParentId(name,parentId.get()).getId();

        }
        if (!parentId.isPresent()) {
            if (categoryRepo.findByName(name) != null) {
                throw new FieldAlreadyExistException(name + " category already exist");
            }
            category.setName(name);
            categoryRepo.save(category);
            return "Success " + categoryRepo.findByName(name).getId();
        }
        return "Success" + categoryRepo.findByNameAndParentId(name,parentId.get()).getId();
    }


    public String deleteCategory(Long id) {
        if (!categoryRepo.findById(id).isPresent()) {
            throw new ResourceNotFoundException(id + " category does not exist");
        }
        if (!productRepo.findByCategoryId(id).isEmpty()) {
            return "id is associated with some product, cannot delete";
        }
        if (!categoryRepo.findByParentId(id).isEmpty()) {
            return "id is a parent category, cannot delete";
        }
        categoryRepo.deleteById(id);
        return "Success";
    }

    private List<String> getCategoryNameTillRoot(Category category){
        List<String> categoryNameTillRoot = new ArrayList<>();
        categoryNameTillRoot.add(category.getName());
        while (category.getParentId() != null) {
            category = category.getParentId();
            categoryNameTillRoot.add(category.getName());
        }
        return categoryNameTillRoot;
    }
}
