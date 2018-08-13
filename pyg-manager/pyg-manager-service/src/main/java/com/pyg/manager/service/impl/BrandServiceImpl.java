package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyg.manager.service.BrandService;
import com.pyg.mapper.BrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by on 2018/8/10.
 */
@Service
public class BrandServiceImpl implements BrandService{

    //注入mapper接口代理对象
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 需求：查询所有品牌数据
     */
    public List<TbBrand> findAll() {
        return brandMapper.findAll();
    }

    @Override
    public PageResult findAllByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
//        List<TbBrand> brandList = brandMapper.findAll();
//        PageInfo<TbBrand> pageInfo = new PageInfo<>(brandList);
//        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        Page<TbBrand> pageInfo= (Page<TbBrand>) brandMapper.findAll();
        return new PageResult(pageInfo.getTotal(),pageInfo.getResult());
    }

    @Override
    public void save(TbBrand brand) {
        brandMapper.save(brand);
    }

    @Override
    public TbBrand findById(int id) {
        return brandMapper.findById(id);
    }

    @Override
    public void update(TbBrand tbBrand) {
        brandMapper.update(tbBrand);
    }

    @Override
    public void deleteById(long id) {
        brandMapper.deleteById(id);
    }
}
