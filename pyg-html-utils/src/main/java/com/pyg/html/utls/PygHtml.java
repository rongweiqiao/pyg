package com.pyg.html.utls;

import com.pyg.mapper.TbGoodsDescMapper;
import com.pyg.mapper.TbGoodsMapper;
import com.pyg.mapper.TbItemCatMapper;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.*;
import com.pyg.utils.FMUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PygHtml {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    public void getHtml(){
        TbGoodsExample tbGoodsExample = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = tbGoodsExample.createCriteria();
        criteria.andAuditStatusEqualTo("1");
        criteria.andCategory1IdIsNotNull();
        criteria.andCategory2IdIsNotNull();
        criteria.andCategory3IdIsNotNull();
        List<TbGoods> goodsList = goodsMapper.selectByExample(tbGoodsExample);
        for (TbGoods goods : goodsList) {
            TbItemExample tbItemExample = new TbItemExample();
            TbItemExample.Criteria criteria1 = tbItemExample.createCriteria();
            criteria1.andGoodsIdEqualTo(goods.getId());
            List<TbItem> itemList = itemMapper.selectByExample(tbItemExample);
            TbGoodsDescExample tbGoodsDescExample = new TbGoodsDescExample();
            TbGoodsDescExample.Criteria criteria2 = tbGoodsDescExample.createCriteria();
            criteria2.andGoodsIdEqualTo(goods.getId());
            criteria2.andItemImagesIsNotNull();
            criteria2.andCustomAttributeItemsIsNotNull();
            List<TbGoodsDesc> tbGoodsDescList = goodsDescMapper.selectByExample(tbGoodsDescExample);
            if(tbGoodsDescList.size()==0){
                continue;
            }
            TbGoodsDesc tbGoodsDesc=tbGoodsDescList.get(0);
            TbItemCat cat1=itemCatMapper.selectByPrimaryKey(goods.getCategory1Id());
            TbItemCat cat2=itemCatMapper.selectByPrimaryKey(goods.getCategory2Id());
            TbItemCat cat3=itemCatMapper.selectByPrimaryKey(goods.getCategory3Id());
            Map map=new HashMap();
            map.put("goods",goods);
            map.put("goodsDesc",tbGoodsDesc);
            map.put("itemList",itemList);
            map.put("cat1",cat1.getName());
            map.put("cat2",cat2.getName());
            map.put("cat3",cat3.getName());
            FMUtils fmUtils = new FMUtils();
            try {
                fmUtils.ouputFile("item.ftl",goods.getId()+".html",map);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void make(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/*.xml");
        PygHtml pygHtml = applicationContext.getBean(PygHtml.class);
        pygHtml.getHtml();
    }

}
