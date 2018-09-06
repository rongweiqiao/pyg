package com.pyg.controller;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConsumerMap {

    @JmsListener(destination = "test_map")
    public void getMap(Map map){
        System.out.println(map.get("name"));
        System.out.println(map.get("age"));
    }
}
