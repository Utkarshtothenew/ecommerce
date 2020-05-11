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
    Category findByName(String name);

    @Query(value = "select * from category where parent_id=:id",nativeQuery = true)
    List<Optional<Category>> findByParentId(@Param("id") Long id);

    @Query(value = "select * from category where parent_id is null",nativeQuery = true)
    List<Category> findRootCategories();

    @Query(value = "select * from category where name=:name AND parent_id=:parentId",nativeQuery = true)
    Category findByNameAndParentId(@Param("name") String name,Long parentId);

    @Query(value = "delete from category where id=:id",nativeQuery = true)
    void deleteById(@Param("id") Long id);


}
