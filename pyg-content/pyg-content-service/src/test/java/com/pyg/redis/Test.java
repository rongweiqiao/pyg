package com.pyg.redis;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-redis.xml")
public class Test {

    @Autowired
    private RedisTemplate redisTemplate;

    @org.junit.Test
    public void setValue(){
        redisTemplate.boundValueOps("name").set("itcast");
    }

    @org.junit.Test
    public void getValue(){
        redisTemplate.boundValueOps("name").get();
    }

    @org.junit.Test
    public void deleteValue(){
        redisTemplate.delete("name");
    }

    @org.junit.Test
    public void setSetValue(){
        redisTemplate.boundSetOps("nameset").add("曹操");
        redisTemplate.boundSetOps("nameset").add("刘备");
        redisTemplate.boundSetOps("nameset").add("孙权");
    }
    @org.junit.Test
    public void getSetValue(){
        Set set = redisTemplate.boundSetOps("nameset").members();
        System.out.println(set);
    }

    @org.junit.Test
    public void deleSetValue(){
        redisTemplate.boundSetOps("nameset").remove("孙权");
    }

    @org.junit.Test
    public void setListValue(){
        redisTemplate.boundListOps("namelist").rightPush("张三");
        redisTemplate.boundListOps("namelist").rightPush("李四");
        redisTemplate.boundListOps("namelist").rightPush("王五");
    }

    @org.junit.Test
    public void getListValue(){
        String s = (String) redisTemplate.boundListOps("namelist").leftPop();
        System.out.println(s);

        System.out.println(redisTemplate.boundListOps("namelist").index(1));
    }

    @org.junit.Test
    public void deleteList(){
        redisTemplate.delete("namelist");
    }





}
