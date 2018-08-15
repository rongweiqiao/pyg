package com.pyg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.BrandService;
import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by on 2018/8/10.
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference(timeout = 1000000)
    private BrandService brandService;

    /**
     * 需求：查询所有品牌
     */
    @RequestMapping("findAll")
    public List<TbBrand> findAll() {
        //调用远程服务对象方法
        List<TbBrand> brandList = brandService.findAll();
        return brandList;

    }

    @RequestMapping("findAllByPage")
    public PageResult findByPage(int pageNum, int pageSize) {
        PageResult pageResult = brandService.findAllByPage(pageNum, pageSize);
        return pageResult;
    }

    @RequestMapping("save")
    public PygResult save(@RequestBody TbBrand tbBrand) {
        try {
            brandService.save(tbBrand);
            return new PygResult(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "添加失败");
        }
    }

    @RequestMapping("findById")
    public TbBrand update(int id) {
        return brandService.findById(id);
    }

    @RequestMapping("update")
    public PygResult update(@RequestBody TbBrand tbBrand) {
        try {
            brandService.update(tbBrand);
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
                brandService.deleteById(id);
            }
            return new PygResult(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "删除失败");
        }
    }

    @RequestMapping("findBrandList")
    public List<Map> findBrandList(){
       return brandService.findBrandList();
    }

    @RequestMapping("search")
    public PageResult search(@RequestBody TbBrand tbBrand,int pageNum, int pageSize){
        return brandService.findAllByPage(tbBrand,pageNum,pageSize);
    }
}
