package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.BrandService;
import com.pyg.manager.service.SpecificationService;
import com.pyg.mapper.TbSpecificationMapper;
import com.pyg.mapper.TbSpecificationOptionMapper;
import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationExample;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.pojo.TbSpecificationOptionExample;
import com.pyg.utils.PageResult;
import com.pyg.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by on 2018/8/10.
 */
@Service
public class SpecificationImpl implements SpecificationService{

    //注入mapper接口代理对象
    @Autowired
    private TbSpecificationMapper specificationMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * 需求：查询所有品牌数据
     */
    public List<TbSpecification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    @Override
    public PageResult findAllByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<TbSpecification> pageInfo= (Page<TbSpecification>) specificationMapper.selectByExample(null);
        return new PageResult(pageInfo.getTotal(),pageInfo.getResult());
    }

    @Override
    public void save(Specification specification) {
        TbSpecification tbSpecification = specification.getTbSpecification();
        specificationMapper.insertSelective(tbSpecification);
        List<TbSpecificationOption> tbSpecificationOptionList = specification.getTbSpecificationOptionList();
        for (TbSpecificationOption tbSpecificationOption : tbSpecificationOptionList) {
            tbSpecificationOption.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insertSelective(tbSpecificationOption);
        }
    }

    @Override
    public Specification findById(long id) {
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptionList = specificationOptionMapper.selectByExample(tbSpecificationOptionExample);
        Specification specification = new Specification();
        specification.setTbSpecification(tbSpecification);
        specification.setTbSpecificationOptionList(tbSpecificationOptionList);
        return specification;
    }

    @Override
    public void update(Specification specification) {
        TbSpecification tbSpecification = specification.getTbSpecification();
        specificationMapper.updateByPrimaryKeySelective(tbSpecification);
        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(tbSpecification.getId());
        specificationOptionMapper.deleteByExample(tbSpecificationOptionExample);
        List<TbSpecificationOption> tbSpecificationOptionList = specification.getTbSpecificationOptionList();
        for (TbSpecificationOption tbSpecificationOption : tbSpecificationOptionList) {
            tbSpecificationOption.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insertSelective(tbSpecificationOption);
        }
    }

    @Override
    public void deleteById(long id) {
        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(id);
        specificationOptionMapper.deleteByExample(tbSpecificationOptionExample);
        specificationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Map> findSpecificationList() {
        return specificationMapper.findSpecificationList();
    }


}
