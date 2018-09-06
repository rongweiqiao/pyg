package com.pyg.sms.listener;

import com.alibaba.fastjson.JSON;
import com.pyg.sms.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @JmsListener(destination = "pyg_sms")
    public void sendMessage(Map map){
        String phoneJson= (String) map.get("phoneNumbers");
        List<String> phoneList = JSON.parseArray(phoneJson, String.class);
        String paramJson = (String) map.get("params");
        List<String> paramList = JSON.parseArray(paramJson, String.class);
        smsUtil.sendMessage(phoneList.toArray(new String[phoneList.size()]),paramList.toArray(new String[paramList.size()]));
    }
}
