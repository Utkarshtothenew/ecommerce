package com.tothenew.ecommerceapp.services;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.tothenew.ecommerceapp.config.EmailCfg;
import com.tothenew.ecommerceapp.entities.product.Product;
import com.tothenew.ecommerceapp.repositories.ProductRepo;
import com.tothenew.ecommerceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    JavaMailSender javaMailSender;


 //   @Scheduled(cron = "0 0 0 * * ?")

    @Scheduled(fixedRateString = "${scheduler.timeInterval.mail}")
    public void sendEmailToSeller() {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        List<Product> productList = productRepo.findAllProductsCreatedInLast24Hours();
        for (Product i : productList) {

            mailMessage.setTo(i.getSeller().getEmail());
            mailMessage.setSubject("Products Created on " + new Date());

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(date);
            mailMessage.setText("http://localhost:8080/product/productsCreatedIn24Hours/"+i.getSeller().getId()+"/"+strDate);
            mailMessage.setFrom("ecommerceapp@gmail.com");

            javaMailSender.send(mailMessage);
        }

    }
}
