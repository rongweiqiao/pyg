package com.pyg.html.listener;


import com.alibaba.fastjson.JSON;
import com.pyg.mapper.TbGoodsDescMapper;
import com.pyg.mapper.TbGoodsMapper;
import com.pyg.mapper.TbItemCatMapper;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.*;
import com.pyg.utils.FMUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.*;

public class MyListenHtml implements MessageListener {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private TbItemCatMapper ItemCatMapper;

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage){
                Set<Long> goodIds=new HashSet<>();
                TextMessage textMessage=(TextMessage)message;
                String itemListJson = textMessage.getText();
                List<TbItem> itemList = JSON.parseArray(itemListJson, TbItem.class);
                for (TbItem item : itemList) {
                    goodIds.add(item.getGoodsId());
                }
                for (Long goodId : goodIds) {
                    TbGoods tbGoods = goodsMapper.selectByPrimaryKey(goodId);
                    TbItemExample tbItemExample = new TbItemExample();
                    TbItemExample.Criteria criteria = tbItemExample.createCriteria();
                    criteria.andGoodsIdEqualTo(goodId);
                    List<TbItem> items = itemMapper.selectByExample(tbItemExample);
                    TbItemCat cat1=ItemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id());
                    TbItemCat cat2=ItemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id());
                    TbItemCat cat3=ItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
                    TbGoodsDescExample tbGoodsDescExample = new TbGoodsDescExample();
                    TbGoodsDescExample.Criteria criteria1 = tbGoodsDescExample.createCriteria();
                    criteria1.andGoodsIdEqualTo(goodId);
                    TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByExample(tbGoodsDescExample).get(0);
                    Map map = new HashMap();
                    map.put("goods",tbGoods);
                    map.put("goodsDesc",tbGoodsDesc);
                    map.put("cat1",cat1.getName());
                    map.put("cat2",cat2.getName());
                    map.put("cat3",cat3.getName());
                    map.put("itemList",itemList);
                    FMUtils fmUtils = new FMUtils();
                    fmUtils.ouputFile("item.ftl",goodId+".html",map);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
