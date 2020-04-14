package com.tothenew.ecommerceapp.bootstrapLoader;

import com.tothenew.ecommerceapp.entities.users.Admin;
import com.tothenew.ecommerceapp.entities.users.Role;
import com.tothenew.ecommerceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepo userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(userRepository.count()<1){

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            Admin utkarsh = new Admin();
            utkarsh.setFirstName("Utkarsh");
            utkarsh.setLastName("Singh");
            utkarsh.setEmail("utkarsh@admin.com");
            utkarsh.setCreatedBy("Utkarsh");
            utkarsh.setDateCreated(new Date());
            utkarsh.setLastUpdated(new Date());
            utkarsh.setUpdatedBy("Utkarsh");
            utkarsh.setActive(true);
            utkarsh.setDeleted(false);
            utkarsh.setPassword(passwordEncoder.encode("pass"));

            Role role = new Role();
            role.setAuthority("ROLE_ADMIN");
            Role role1 = new Role();
            role1.setAuthority("ROLE_CUSTOMER");
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            roleSet.add(role1);
            utkarsh.setRoles(roleSet);

            userRepository.save(utkarsh);
            System.out.println("Total users saved::"+userRepository.count());
        }
    }
}
