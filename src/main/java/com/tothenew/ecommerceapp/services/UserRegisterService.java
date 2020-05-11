package com.tothenew.ecommerceapp.services;

import com.tothenew.ecommerceapp.entities.users.*;
import com.tothenew.ecommerceapp.repositories.CustomerActivateRepo;
import com.tothenew.ecommerceapp.repositories.SellerRepo;
import com.tothenew.ecommerceapp.repositories.UserRepo;
import com.tothenew.ecommerceapp.utils.SendEmail;
import com.tothenew.ecommerceapp.utils.ValidEmail;
import com.tothenew.ecommerceapp.utils.ValidGst;
import com.tothenew.ecommerceapp.utils.ValidPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserRegisterService {

    @Autowired
    SellerRepo sellerRepo;
    @Autowired
    ValidGst validGst;
    @Autowired
    CustomerActivateRepo customerActivateRepo;
    @Autowired
    SendEmail sendEmail;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepo userRepo;

    public String registerCustomer(Customer customer) {
//        boolean isValidEmail = validEmail.checkEmailValid(customer.getEmail());
//        if (!isValidEmail) {
//            return "email is not valid";
//        }
        User localCustomer = userRepo.findByEmail(customer.getEmail());
        try {
            if (localCustomer.getEmail().equals(customer.getEmail())) {
                return "Email already exists";
            }
        } catch (NullPointerException ex) {
//                ex.printStackTrace(); // use logger here
        }

        boolean isValidPassword = ValidPassword.isValidPassword(customer.getPassword());
        if (!isValidPassword) {
            return "password is invalid";
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        if (customer.getContact().length() != 10) {
            return "invalid contact";
        }
        Role role = new Role();
        role.setAuthority("ROLE_CUSTOMER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        customer.setRoles(roleSet);
        customer.setCreatedBy(customer.getFirstName());
        customer.setDateCreated(new Date());
        customer.setLocked(false);
        customer.setPasswordExpired(false);

        userRepo.save(customer);

        String token = UUID.randomUUID().toString();

        CustomerActivate customerActivate = new CustomerActivate();
        customerActivate.setToken(token);
        customerActivate.setUserEmail(customer.getEmail());
        customerActivate.setExpiryDate(new Date());

        customerActivateRepo.save(customerActivate);

        sendEmail.sendEmail("ACCOUNT ACTIVATE TOKEN", "http://localhost:8080/customer/activate/" + token, customer.getEmail());

        return "Success";
    }

    public String registerSeller(Seller seller) {
        boolean isValidGst = validGst.checkGstValid(seller.getGst());
        if (!isValidGst) {
            return "gst is not valid";
        }
//        boolean isValidEmail = validEmail.checkEmailValid(seller.getEmail());
//        if (!isValidEmail) {
//            return "email is not valid";
//        }
        User localSeller = userRepo.findByEmail(seller.getEmail());
        try {
            if (localSeller.getEmail().equals(seller.getEmail())) {
                return "Email already exists";
            }
        } catch (NullPointerException ex) {
//            ex.printStackTrace();
        }
        Seller anotherLocalSeller = sellerRepo.findByCompanyName(seller.getCompanyName());
        try {
            if (anotherLocalSeller.getCompanyName().equalsIgnoreCase(seller.getCompanyName())) {
                return "company name should be unique";
            }
        } catch (NullPointerException ex) {
//            ex.printStackTrace();
        }
        List<Seller> anotherLocalSeller1 = sellerRepo.findByGst(seller.getGst());
        boolean flag = false;
        for (Seller seller1 : anotherLocalSeller1) {
            if (seller1.getGst().equals(seller.getGst())) {
                flag = true;
                break;
            }
        }
        try {
            if (flag == true) {
                return "gst should be unique";
            }
        } catch (NullPointerException ex) {
//            ex.printStackTrace();
        }
        boolean isValidPassword = ValidPassword.isValidPassword(seller.getPassword());
        if (!isValidPassword) {
            return "password is invalid";
        }
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        if (seller.getCompanyContact().length() != 10) {
            return "invalid contact";
        }
        Role role = new Role();
        role.setAuthority("ROLE_SELLER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        seller.setRoles(roleSet);
        seller.setCreatedBy(seller.getFirstName());
        seller.setDateCreated(new Date());
        seller.setLocked(false);
        seller.setPasswordExpired(false);


        Set<Address> addresses = seller.getAddresses();
        addresses.forEach(address -> {
            Address addressSave = address;
            addressSave.setUser(seller);
        });

        userRepo.save(seller);

        return "Success";
    }
    public String activeDeactive(Long id, boolean isActivateRequest) throws Exception{
        Optional user = userRepo.findById(id);
        validateActivateDeactivateRequest(isActivateRequest, (User) user.get());
        ((User) user.get()).setActive(isActivateRequest);
        userRepo.save(user.get());
        String activationDeactivationMessage = isActivateRequest ? "ACTIVATED" : "DEACTIVATED";
        sendEmail.sendEmail(activationDeactivationMessage, "HEY CUSTOMER YOUR ACCOUNT HAS BEEN "+activationDeactivationMessage, ((User) user.get()).getEmail());
        return "Success";
/* if (value) {
if (!user.get().isActive()) {
user.get().setActive(true);
userRepo.save(user.get());
// trigger mail
sendEmail.sendEmail("ACTIVATED", "HEY CUSTOMER YOUR ACCOUNT HAS BEEN ACTIVATED", user.get().getEmail());
return "Success";
}
userRepo.save(user.get());
System.out.println("already activated");
return "Success";

    }
    else {
        if (user.get().isActive()) {
            user.get().setActive(false);
            userRepo.save(user.get());
            // trigger mail
            sendEmail.sendEmail("DEACTIVATED", "HEY CUSTOMER YOUR ACCOUNT HAS BEEN DEACTIVATED", user.get().getEmail());
            return "Success";
        }
        userRepo.save(user.get());
        System.out.println("already deactivated");
        return "Success";

    }*/



    }
    public void validateActivateDeactivateRequest(boolean isActivateRequest, User user) throws Exception {
        if (isActivateRequest && user.isActive()) {
            throw new Exception("User is already active");
        }  else if (!isActivateRequest && !user.isActive()) {
            throw new Exception("User is already deactive");
        }
    }


}
