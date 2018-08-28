package com.pyg.search.listen;

import com.alibaba.fastjson.JSON;
import com.pyg.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Map;

public class MyMessageListener implements MessageListener {

    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public void onMessage(Message message) {
       if(message instanceof TextMessage){
           TextMessage textMessage=(TextMessage)message;
           try {
               String itemListJson = textMessage.getText();
               List<TbItem> itemList = JSON.parseArray(itemListJson, TbItem.class);
               for (TbItem item : itemList) {
                   String spec = item.getSpec();
                   Map<String,String> map= (Map<String, String>)JSON.parse(spec);
                   item.setSpecMap(map);
               }
               solrTemplate.saveBeans(itemList);
               solrTemplate.commit();
           } catch (JMSException e) {
               e.printStackTrace();
           }
       }
    }
}
