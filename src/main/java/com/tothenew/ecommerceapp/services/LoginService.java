package com.tothenew.ecommerceapp.services;

import com.tothenew.ecommerceapp.entities.users.*;
import com.tothenew.ecommerceapp.repositories.UserLoginFailCounterRepo;
import com.tothenew.ecommerceapp.repositories.UserRepo;
import com.tothenew.ecommerceapp.utils.ValidPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LoginService {
    @Value("${spring.useractivation.maxwindow.hours}")
    private int hours;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserLoginFailCounterRepo userLoginFailCounterRepo;

    @Scheduled(cron="0 0 12 * * ?")
    public void activateUser() {
        userRepo.activate(hours);


//        List<User> userList= (List<User>) userRepo.findByIsActive(false);
//        for(User i : userList){
//
//            Optional<UserLoginFailCounter> userLoginFailCounterOptional = userLoginFailCounterRepo.findById(i.getId());
//            UserLoginFailCounter userLoginFailCounter=userLoginFailCounterOptional.get();
//            if(userLoginFailCounter==null) {
//                continue;
//            }
//            Date date = new Date();
//            long diff = date.getTime() - i.getLastUpdated().getTime();
//            long diffHours = diff / (60 * 60 * 1000);
//
//            if (diffHours > 24 && userLoginFailCounter.getAttempts() > 2)
//            {
//                i.setActive(true);
//                i.setLocked(false);
//                i.setLastUpdated(new Date());
//                userRepo.save(i);
//            }
//        }



    }
}
