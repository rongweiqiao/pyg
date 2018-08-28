package com.pyg.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-jms-consumer.xml")
public class SpringTopicConsumer {

    @Test
    public void getSpringTopicMessage() throws IOException {
        System.in.read();
    }
}
