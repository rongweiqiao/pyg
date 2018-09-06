package com.pyg.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pyg.cart.service.CartService;
import com.pyg.utils.CookieUtil;
import com.pyg.utils.PygResult;
import com.pyg.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Reference
    private CartService cartService;

    @RequestMapping("/addGoodsToCartList")
    @CrossOrigin(origins = "http://localhost")//设置跨域请求的地址(想要访问本系统的其他系统的地址)
    public PygResult addGoodsToCartList(Long itemId,
                                        Integer num,
                                        HttpServletRequest request,
                                        HttpServletResponse response){
        try {
            String loginName = request.getRemoteUser();
            //无论有没有登录,都要查询购物车
            List<Cart> cartList=findCartList(request,response);
            //然后将商品添加到购物车中
            cartList=cartService.addItemsToCartList(cartList,itemId,num);
            if(loginName!=null&&!"".equals(loginName)){
                //用户登录,把购物车添加到redis缓存
                cartService.addRedisCart(cartList,loginName);
            }else {
                //没有登录,把购物车添加到cookie中返回给浏览器进行存储
                CookieUtil.setCookie(request,response,"cookie_cart",JSON.toJSONString(cartList),172800,true);
            }
            return new PygResult(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"添加失败");
        }


    }

    /**
     * 查询购物车列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findCartList")
    private List<Cart> findCartList(HttpServletRequest request, HttpServletResponse response) {
        String loginName = request.getRemoteUser();
        String cartJSON = CookieUtil.getCookieValue(request, "cookie_cart", true);
        //要先查询cookie购物车,如cookie购物车null要进行初始化,因为后面要将cookie购物车和redis购物车进行合并
        if(cartJSON==null||"".equals(cartJSON)){
           //cookie购物车有数据
           cartJSON="[]";
        }
        List<Cart> cookieCartList = JSON.parseArray(cartJSON, Cart.class);
        if(loginName!=null&&!"".equals(loginName)){
            //已经登录,需要返回redis购物车
            //查询redis数据库
            List<Cart> redisCatList=cartService.findRedisCartList(loginName);
            if(cookieCartList!=null&&cookieCartList.size()!=0){
                //cookie购物车有数据,合并cookie购物车和redis购物车
                redisCatList = cartService.addRedisAndCookie(redisCatList, cookieCartList);
                //之前先查询的结果要进行覆盖,需重新添加到redis购物车
                cartService.addRedisCart(redisCatList,loginName);
                //然后清空cookie购物车
                CookieUtil.setCookie(request,response,"cookie_cart","",0,true);
            }
            return redisCatList;
        }else {
            //没有登录,直接返回cookie购物车
            return cookieCartList;
        }
    }
}
