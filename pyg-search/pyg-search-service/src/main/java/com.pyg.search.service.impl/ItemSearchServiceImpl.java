package com.pyg.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import javax.xml.crypto.dsig.SignatureMethod;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map queryList(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        String keywords = (String) searchMap.get("keywords");
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        Criteria criteria = null;
        if (keywords != null && !"".equals(keywords)) {
            criteria = new Criteria("item_keywords").is(keywords);
        } else {
            criteria = new Criteria().expression("*:*");
        }
        HighlightOptions options = new HighlightOptions();
        options.addField("item_title");
        options.setSimplePrefix("<font color='red'>");
        options.setSimplePostfix("</font>");
        query.setHighlightOptions(options);
        query.addCriteria(criteria);
        String category = (String) searchMap.get("category");
        if(category!=null&&!"".equals(category)){
            Criteria criteria1 = new Criteria("item_category").is(category);
            FilterQuery filterQuery = new SimpleQuery(criteria1);
            query.addFilterQuery(filterQuery);
        }
        String brand=(String)searchMap.get("brand");
        if(brand!=null&&!"".equals(brand)){
            Criteria criteria1 = new Criteria("item_brand").is(brand);
            FilterQuery filterQuery = new SimpleFacetQuery(criteria1);
            query.addFilterQuery(filterQuery);
        }
        Map<String,String> specMap = (Map<String, String>) searchMap.get("spec");
        for (String key : specMap.keySet()) {
            String value= (String) specMap.get(key);
            Criteria criteria1 = new Criteria("item_spec_" + key).is(value);
            FilterQuery filterQuery=new SimpleQuery(criteria1);
            query.addFilterQuery(filterQuery);
        }
        String price = (String) searchMap.get("price");
        if(price!=null&&!"".equals(price)){
            String[] split = price.split("-");
            if(!"0".equals(split[0])){
                Criteria criteria1 = new Criteria("item_price").greaterThanEqual(split[0]);
                FilterQuery filterQuery = new SimpleFacetQuery(criteria1);
                query.addFilterQuery(filterQuery);
            }
            if(!"*".equals(split[1])){
                Criteria criteria1 = new Criteria("item_price").lessThan(split[1]);
                FilterQuery filterQuery = new SimpleFacetQuery(criteria1);
                query.addFilterQuery(filterQuery);
            }
        }
        String sort = (String) searchMap.get("sort");
        String sortField= (String) searchMap.get("sortField");
        if(sort!=null&&!"".equals(sort)&&sortField!=null&&!"".equals(sortField)){
            if("ASC".equals(sort)){
                Sort sort1 = new Sort(Sort.Direction.ASC, "item_"+sortField);
                query.addSort(sort1);
            }else {
                Sort sort1 = new Sort(Sort.Direction.DESC,"item_"+sortField);
                query.addSort(sort1);
            }
        }
        Integer pageNum = (Integer)searchMap.get("page");
        Integer pageSize = (Integer) searchMap.get("pageSize");
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=30;
        }
        query.setOffset((pageNum-1)*pageSize);
        query.setRows(pageSize);

        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
        List<TbItem> itemList = page.getContent();
        for (TbItem item : itemList) {
            if(page.getHighlights(item).size()>0&&page.getHighlights(item).get(0).getSnipplets().size()>0) {
                String hightTitle = page.getHighlights(item).get(0).getSnipplets().get(0);
                item.setTitle(hightTitle);
            }
        }
        map.put("totalPages",page.getTotalPages());
        map.put("total", page.getTotalElements());
        map.put("rows", itemList);
        return map;
    }
}
