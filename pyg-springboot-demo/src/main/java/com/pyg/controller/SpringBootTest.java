package com.pyg.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootTest {

    @Autowired
    private Environment evn;
    @RequestMapping("test")
    public String test01(){
        return "hello springBoot";
    }

    @RequestMapping("test02")
    public String test02(){
        return evn.getProperty("name");
    }
}
