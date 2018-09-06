package com.pyg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Consumer {
    @JmsListener(destination = "test_springBoot_mq")
    public void getMessage(String text){
        System.out.println(text);
    }
}
