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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    private final Logger LOG = LoggerFactory.getLogger(getClass());

 //   @Scheduled(cron = "0 0 0 * * ?")

//    @Scheduled(fixedRateString = "${scheduler.timeInterval.mail}")
//    public void sendEmailToSeller() {
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        List<Product> productList = productRepo.findAllProductsCreatedInLast24Hours();
//        for (Product i : productList) {
//
//            mailMessage.setTo(i.getSeller().getEmail());
//            mailMessage.setSubject("Products Created on " + new Date());
//
//            Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            String strDate = formatter.format(date);
//            mailMessage.setText("http://localhost:8080/product/productsCreatedIn24Hours/"+i.getSeller().getId()+"/"+strDate);
//            mailMessage.setFrom("ecommerceapp@gmail.com");
//
//            javaMailSender.send(mailMessage);
//        }

        @Scheduled(fixedRateString = "${scheduler.timeInterval.mail}")
        public void sendProductsListMailToSeller() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException, MessagingException {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            List<Product> productList = productRepo.findAllProductsCreatedInLast24Hours();
            for (Product i : productList) {

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = formatter.format(date);
                String filename = "/home/utkarsh/Documents/productList_"+i.getSeller().getId()+"_"+strDate+".csv";


                FileWriter writecsv = new FileWriter(filename);
                StatefulBeanToCsv<Product> writer = new StatefulBeanToCsvBuilder<Product>(writecsv)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(false)
                        .build();

                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String stringDate = sdfDate.format(date);
                Long id=i.getSeller().getId();
                writer.write(productRepo.findProductsCreatedInLast24Hours(stringDate,id));
                LOG.info("Success");


                MimeMessage mimeMailMessage=javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
                helper.setFrom("ecommerceapp@gmail.com");
                helper.setTo(i.getSeller().getEmail());
                helper.setSubject("Products Created on " + new Date());
                String path="/home/utkarsh/Documents/productList_"+i.getSeller().getId()+"_"+strDate+".csv";

                FileSystemResource file = new FileSystemResource(path);
                helper.addAttachment(file.getFilename(),file);
                javaMailSender.send(mimeMailMessage);



            }
        }

    }

