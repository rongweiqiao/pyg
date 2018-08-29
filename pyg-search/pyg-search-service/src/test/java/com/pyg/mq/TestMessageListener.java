package com.pyg.mq;

import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TestMessageListener implements MessageListener {


    @Override
    public void onMessage(Message message) {
       if(message instanceof TextMessage){
           TextMessage textMessage=(TextMessage)message;
           try {
               System.out.println(textMessage.getText());
           } catch (JMSException e) {
               e.printStackTrace();
           }
       }
    }
}
