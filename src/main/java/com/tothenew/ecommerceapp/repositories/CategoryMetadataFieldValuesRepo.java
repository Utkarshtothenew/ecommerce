package com.tothenew.ecommerceapp.repositories;

import com.tothenew.ecommerceapp.entities.category.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CategoryMetadataFieldValuesRepo extends CrudRepository<CategoryMetadataFieldValues,Long> {


}
