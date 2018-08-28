package com.pyg.search;

import com.pyg.pojo.TbItem;
import com.pyg.utils.PageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-solr.xml")
public class SolrTest {
    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    public void test(){
        TbItem tbItem = new TbItem();
        tbItem.setId(2L);
        tbItem.setBrand("华为");
        tbItem.setCategory("手机");
        tbItem.setGoodsId(1L);
        tbItem.setSeller("华为2号专卖店");
        tbItem.setTitle("华为Mate9");
        HashMap<String, String> map = new HashMap<>();
        map.put("66","66");
        tbItem.setSpecMap(map);
        tbItem.setPrice(new BigDecimal(2000));
        solrTemplate.saveBean(tbItem);
        solrTemplate.commit();

    }

    @Test
    public void get(){
        TbItem item = solrTemplate.getById(1L, TbItem.class);
        System.out.println(item.getTitle());
    }

    @Test
    public void dele(){
        solrTemplate.deleteById("1");
        solrTemplate.commit();
    }

    @Test
    public void testAddList(){
        Query query = new SimpleQuery("*:*");
        query.setOffset(0);
        query.setRows(1);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
        System.out.println(page.getTotalElements());
        List<TbItem> itemList = page.getContent();
        for (TbItem item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    public void testCriteria(){
        SimpleQuery query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_spec_66");
        criteria.contains("6");
        query.addCriteria(criteria);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
        List<TbItem> tbItems = page.getContent();
        System.out.println(page.getTotalElements());
        for (TbItem tbItem : tbItems) {
            System.out.println(tbItem.getTitle());
        }
    }

    @Test
    public void queryHightLigint(){
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        Criteria criteria = new Criteria("item_title").contains("9");
        query.addCriteria(criteria);
        HighlightOptions options = new HighlightOptions();
        options.addField("item_title");
        options.setSimplePrefix("<font color='red'>");
        options.setSimplePostfix("</font>");

        query.setHighlightOptions(options);

        HighlightPage<TbItem> tbItems = solrTemplate.queryForHighlightPage(query, TbItem.class);
        System.out.println(tbItems.getTotalElements());
        List<TbItem> itemList = tbItems.getContent();
        List<HighlightEntry<TbItem>> highlighted = tbItems.getHighlighted();
        for (HighlightEntry<TbItem> tbItemHighlightEntry : highlighted) {
            System.out.println(tbItemHighlightEntry.getHighlights().get(0).getSnipplets().get(0));
        }


        for (TbItem item : itemList) {
            List<HighlightEntry.Highlight> highlights = tbItems.getHighlights(item);
            System.out.println(highlights.get(0).getSnipplets().get(0));
        }
    }

    @Test
    public void deleAll(){
        SimpleQuery query = new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }

}
