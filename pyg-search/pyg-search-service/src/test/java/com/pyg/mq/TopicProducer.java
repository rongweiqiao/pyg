package com.pyg.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class TopicProducer {
    @Test
    public void makeTopicMassage() throws JMSException {
        String url="tcp://192.168.12.168:61616";
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("topicProducer");
        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = session.createTextMessage("订阅模式点对多,topic");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();

    }
}
