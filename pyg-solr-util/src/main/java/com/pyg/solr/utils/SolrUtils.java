package com.pyg.solr.utils;

import com.alibaba.fastjson.JSON;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.TbItem;
import com.pyg.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtils {
    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private TbItemMapper itemMapper;

    public void importDatabaseToSolrIndex(){
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        List<TbItem> tbItemList = itemMapper.selectByExample(example);
        for (TbItem item : tbItemList) {
            Map<String,String> map = (Map<String, String>) JSON.parse(item.getSpec());
            item.setSpecMap(map);
        }
        solrTemplate.saveBeans(tbItemList);
        solrTemplate.commit();

    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/*.xml");
        SolrUtils solrUtils = context.getBean(SolrUtils.class);
        solrUtils.importDatabaseToSolrIndex();
    }

}
