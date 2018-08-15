package com.pyg.manager.service;

import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Created by on 2018/8/10.
 */
public interface BrandService {
    /**
     * 需求：查询所有品牌数据
     */
    public List<TbBrand> findAll();

    PageResult findAllByPage(int pageNum, int pageSize);

    public void save(TbBrand brand);

    public TbBrand findById(long id);

    public void update(TbBrand tbBrand);

    public void deleteById(long id);

    public List<Map> findBrandList();

    public PageResult findAllByPage(TbBrand tbBrand, int pageNum, int pageSize);
}
