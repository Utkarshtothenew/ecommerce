package com.tothenew.ecommerceapp.repositories;

import com.tothenew.ecommerceapp.entities.category.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends CrudRepository<Category,Long> {

}
