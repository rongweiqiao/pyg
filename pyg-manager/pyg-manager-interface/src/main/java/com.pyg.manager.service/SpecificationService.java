package com.pyg.manager.service;

import com.pyg.pojo.TbSpecification;
import com.pyg.utils.PageResult;
import com.pyg.vo.Specification;

import java.util.List;

/**
 * Created by on 2018/8/10.
 */
public interface SpecificationService {
    /**
     * 需求：查询所有品牌数据
     */
    public List<TbSpecification> findAll();

    PageResult findAllByPage(int pageNum, int pageSize);

    public void save(Specification specification);

    public Specification findById(long id);

    public void update(Specification specification);

    public void deleteById(long id);
}
