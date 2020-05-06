package com.tothenew.ecommerceapp.repositories;

import antlr.collections.List;
import com.tothenew.ecommerceapp.entities.users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User,Long> {
    User findByEmail(String email);
    Optional<User> findById(Long id);
    //List findByIsActive(boolean value);
    @Query(value = "update user set is_active=true WHERE last_updated > (NOW() - INTERVAL :hours HOUR)",nativeQuery = true)
    void activate(@Param("hours") int hours);
}
