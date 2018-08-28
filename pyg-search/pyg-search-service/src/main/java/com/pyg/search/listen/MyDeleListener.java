package com.pyg.search.listen;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Arrays;
import java.util.List;

public class MyDeleListener implements MessageListener {
    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage=(TextMessage)message;
            try {
                String idList = textMessage.getText();
                List<String> list = JSON.parseArray(idList, String.class);
                solrTemplate.deleteById(list);
                solrTemplate.commit();

            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
