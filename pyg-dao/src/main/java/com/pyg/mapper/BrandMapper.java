package com.pyg.mapper;

import com.pyg.pojo.TbBrand;

import java.util.List;

/**
 * Created by on 2018/8/10.
 */
public interface BrandMapper {

    /**
     * 需求：查询所有品牌数据
     */
    public List<TbBrand> findAll();

    /**
     * 需求：实现品牌数据添加
     */
    public void insert(TbBrand brand);

    /**
     * 需求：根据id查询品牌数据
     */
    public TbBrand findById(Long id);

    /**
     * 需求：更新品牌数据
     */
    public void update(TbBrand brand);

    /**
     * 需求：删除品牌数据
     */
    public void delete(Long id);
}
