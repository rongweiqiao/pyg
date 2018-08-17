package com.pyg.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.SpecificationService;
import com.pyg.pojo.TbSpecification;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import com.pyg.vo.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by on 2018/8/10.
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    /**
     * 需求：查询所有品牌
     */
    @RequestMapping("findAll")
    public List<TbSpecification> findAll() {
        //调用远程服务对象方法
        List<TbSpecification> specificationList = specificationService.findAll();
        return specificationList;

    }

    @RequestMapping("findAllByPage")
    public PageResult findByPage(int pageNum, int pageSize) {
        PageResult pageResult = specificationService.findAllByPage(pageNum, pageSize);
        return pageResult;
    }

    @RequestMapping("save")
    public PygResult save(@RequestBody Specification specification) {
        try {
            specificationService.save(specification);
            return new PygResult(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "添加失败");
        }
    }

    @RequestMapping("findById")
    public Specification update(long id) {
        return specificationService.findById(id);
    }

    @RequestMapping("update")
    public PygResult update(@RequestBody Specification specification) {
        try {
            specificationService.update(specification);
            return new PygResult(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "修改失败");
        }
    }

    @RequestMapping("delete")
    public PygResult delete(long[] ids) {
        try {
            for (long id : ids) {
                specificationService.deleteById(id);
            }
            return new PygResult(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "删除失败");
        }
    }
    @RequestMapping("findSpecificationList")
    public List<Map> findSpecificationList(){
        return specificationService.findSpecificationList();
    }

    @RequestMapping("search")
    public PageResult search(@RequestBody TbSpecification tbSpecification, int pageNum, int pageSize) {
        PageResult pageResult = specificationService.findAllByPage(tbSpecification, pageNum, pageSize);
        return pageResult;
    }
}
