package com.pyg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MapTest {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("map")
    public String sendMap(){
        Map map=new HashMap<>();
        map.put("name","zhangsan");
        map.put("age",14);
        jmsMessagingTemplate.convertAndSend("test_map",map);
        return "success";
    }
}
