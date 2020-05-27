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
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;

@Service
public class Scheduler {
    @Autowired
    ProductRepo productRepo;

    private JavaMailSender javaMailSender;


    @Scheduled(cron = "0 31 1/12 * * ?")
    public void sendEmailToSeller() {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        List<Product> productList = productRepo.findAllProductsCreatedInLast24Hours();
        for (Product i : productList) {

            mailMessage.setTo(i.getSeller().getEmail());
            mailMessage.setSubject("Products Created on " + new Date());

            mailMessage.setText("http://localhost:8080/productsCreatedIn24Hours/"+new Date()+"/"+i.getSeller().getId());
            mailMessage.setFrom("ecommerceapp@gmail.com");

            javaMailSender.send(mailMessage);
        }

    }
}
