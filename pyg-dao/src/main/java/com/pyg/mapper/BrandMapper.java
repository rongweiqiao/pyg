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

    public int save(TbBrand brand);

    public TbBrand findById(int id);

    public int update(TbBrand tbBrand);

    public int deleteById(long id);
}
