package com.pyg.sms.utils;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SmsUtil {
    @Autowired
    private Environment evn;

    public void sendMessage(String[] phoneNumbers,String[] params){
        try {
            SmsMultiSender msender = new SmsMultiSender(Integer.parseInt(evn.getProperty("appid")), evn.getProperty("appkey"));
            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
                    Integer.parseInt(evn.getProperty("templateId")), params, null, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.print(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }
}
