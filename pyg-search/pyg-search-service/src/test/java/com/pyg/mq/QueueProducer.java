package com.pyg.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class QueueProducer {
    @Test
   public void makeMessage() throws JMSException {
        String url="tcp://192.168.12.168:61616";
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("testProducer");
        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage("你好，欢迎来到activeMq8888");
        producer.send(message);
        producer.close();
        session.close();
        connection.close();

    }
}
