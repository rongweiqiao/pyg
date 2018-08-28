package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.GoodsService;
import com.pyg.mapper.*;
import com.pyg.pojo.*;
import com.pyg.pojo.TbGoodsExample.Criteria;
import com.pyg.utils.PageResult;
import com.pyg.vo.Goods;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;

    //注入商品描述mapper接口代理对象
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    //注入sku商品mapper接口代理对象
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbBrandMapper brandMapper;

    @Autowired
    private TbSellerMapper sellerMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Goods goods) {
        TbGoods tbGoods = goods.getTbGoods();
        tbGoods.setAuditStatus("0");
        goodsMapper.insertSelective(tbGoods);
        TbGoodsDesc tbGoodsDesc = goods.getTbGoodsDesc();
        tbGoodsDesc.setGoodsId(goods.getTbGoods().getId());
        goodsDescMapper.insertSelective(tbGoodsDesc);
        List<TbItem> itemList = goods.getItemList();
        if ("1".equals(tbGoods.getIsEnableSpec())) {
            for (TbItem item : itemList) {
                String title = tbGoods.getGoodsName();
                Map<String, String> map = (Map<String, String>) JSON.parse(item.getSpec());
                for (String key : map.keySet()) {
                    title += " " + map.get(key);
                }
                item.setTitle(title);//主题
                setItemValues(tbGoods, item, tbGoodsDesc);
                itemMapper.insert(item);

            }
        } else {
            TbItem item = new TbItem();
            item.setTitle(tbGoods.getGoodsName());
            item.setPrice(tbGoods.getPrice());
            item.setStatus("1");
            item.setNum(99999);
            item.setSpec("{}");
            setItemValues(tbGoods, item, tbGoodsDesc);
            itemMapper.insert(item);


        }
    }

    private void setItemValues(TbGoods tbGoods, TbItem item, TbGoodsDesc tbGoodsDesc) {
        item.setGoodsId(tbGoods.getId());//商品SPU编号
        item.setSellerId(tbGoods.getSellerId());//商家编号
        item.setCategoryid(tbGoods.getCategory3Id());//商家分类编号
        item.setCreateTime(new Date());//创建时间
        item.setUpdateTime(new Date());//更新时间

        //分类名称,数据库只存取了对应的ID
        TbBrand tbBrand = brandMapper.selectByPrimaryKey(tbGoods.getBrandId());
        item.setBrand(tbBrand.getName());

        //设置商家名称
        TbSeller tbSeller = sellerMapper.selectByPrimaryKey(tbGoods.getSellerId());
        item.setSellerId(tbSeller.getNickName());

        //图片地址
        List<Map> imagelist = JSON.parseArray(tbGoodsDesc.getItemImages(), Map.class);
        if (imagelist.size() > 0) {
            item.setImage((String) imagelist.get(0).get("url"));
        }

    }


    /**
     * 修改
     */
    @Override
    public void update(Goods goods) {
        goodsMapper.updateByPrimaryKey(goods.getTbGoods());
    }

    /**
     * @return
     */
    @Override
    public TbGoods findOne(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setIsDelete("0");
            goodsMapper.updateByPrimaryKeySelective(tbGoods);
        }
    }


    public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        Criteria criteria = example.createCriteria();

        if (goods != null) {
            if (goods.getSellerId() != null && !"".equals(goods.getSellerId())) {
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            if (!"".equals(goods.getAuditStatus()) && goods.getAuditStatus() != null) {
                if ("0".equals(goods.getAuditStatus())) {
                    criteria.andAuditStatusEqualTo("0");
                } else if ("1".equals(goods.getAuditStatus())) {
                    criteria.andAuditStatusEqualTo("1");
                } else if ("2".equals(goods.getAuditStatus())) {
                    criteria.andAuditStatusEqualTo("2");
                } else {
                    criteria.andAuditStatusEqualTo("3");
                }
            }
            if (!"".equals(goods.getGoodsName()) && goods.getGoodsName() != null) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
                criteria.andIsDeleteNotEqualTo("0");
        }
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateStatus(String status, Long[] ids) {
        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setAuditStatus(status);
            goodsMapper.updateByPrimaryKey(tbGoods);
            System.out.println(tbGoods);
        }
    }

    @Override
    public boolean updateMarke(String marke, Long[] ids) {
        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
            if(!"1".equals(tbGoods.getAuditStatus())){
                return false;
            }
            tbGoods.setIsMarketable(marke);
            goodsMapper.updateByPrimaryKeySelective(tbGoods);
        }
        return true;
    }

    @Override
    public List<TbItem> findSkuItemList(Long[] ids) {
        List<TbItem> list=new ArrayList<>();
        for (Long id : ids) {
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(id);
            List<TbItem> itemList = itemMapper.selectByExample(example);
            list.addAll(itemList);
        }
        return list;
    }

}
