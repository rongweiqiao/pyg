package com.pyg.sms.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SmsQueneController {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("send")
    public void sendSms(){
        Map map=new HashMap();
        List<String> phoneList=new ArrayList<>();
        phoneList.add("18819470259");
        map.put("phoneNumbers",JSON.toJSONString(phoneList));
        List<String> paramList=new ArrayList<>();
        paramList.add("123456");
        paramList.add("2");
        map.put("params", JSON.toJSONString(paramList));
        jmsMessagingTemplate.convertAndSend("pyg_sms",map);
    }
}
