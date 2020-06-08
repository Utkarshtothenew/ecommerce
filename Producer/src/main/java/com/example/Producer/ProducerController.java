package com.example.Producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @RequestMapping("/send")
    public String sendMsg(@RequestParam("msg")String msg){
        producerService.send(msg);
        return "Done";
    }


}
