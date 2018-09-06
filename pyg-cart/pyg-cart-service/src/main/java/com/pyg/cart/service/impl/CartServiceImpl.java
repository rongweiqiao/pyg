package com.pyg.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.cart.service.CartService;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.TbItem;
import com.pyg.pojo.TbOrderItem;
import com.pyg.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询redis购物车
     * @param loginName
     * @return
     */
    @Override
    public List<Cart> findRedisCartList(String loginName) {
        List<Cart> redisCartList= (List<Cart>) redisTemplate.boundHashOps("redis_cart").get(loginName);
        if(redisCartList==null||redisCartList.size()==0){
            return new ArrayList<Cart>();
        }else {
            return redisCartList;
        }
    }

    /**
     * 合拼redis和cookie两个购物车
     * @param redisCatList
     * @param cookieCartList
     * @return
     */
    @Override
    public List<Cart> addRedisAndCookie(List<Cart> redisCatList, List<Cart> cookieCartList) {
        List<Cart> cartList = null;
        for (Cart cart : cookieCartList) {
            List<TbOrderItem> orderItemList = cart.getOrderItemList();
            for (TbOrderItem orderItem : orderItemList) {
                //将商品加入购物车中,返回该购物车
                cartList=addItemsToCartList(redisCatList,orderItem.getItemId(),orderItem.getNum());
            }
        }
        return cartList;
    }

    @Override
    public void addRedisCart(List<Cart> redisCatList, String loginName) {
        redisTemplate.boundHashOps("redis_cart").put(loginName,redisCatList);
    }

    /**
     * 添加商品进入购物车
     * @param catList
     * @param itemId
     * @param num
     * @return  该购物车
     */
    public List<Cart> addItemsToCartList(List<Cart> catList, Long itemId, Integer num) {
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        if(item==null){
            throw new RuntimeException("没有该商品");
        }
        if(!"1".equals(item.getStatus())){
            throw new RuntimeException("商品不可用");
        }
        String sellerId = item.getSellerId();
        //定义一个方法判断该购物车中是否同个商家的货品,需要返回Cart对象
        Cart cart=isSameCart(catList,sellerId);
        if(cart==null){
            //没有该商家
             cart = new Cart();
             cart.setSellerId(sellerId);
             cart.setSellerName(item.getSeller());
             List<TbOrderItem> orderItemList =new ArrayList<>();
             //定义一个方法,在购物中一个商家内新增一个商品数据
             TbOrderItem tbOrderItem=creatOrderItem(item,num);
             orderItemList.add(tbOrderItem);
             cart.setOrderItemList(orderItemList);
             catList.add(cart);
        }else {
            //有该商家
            List<TbOrderItem> orderItemList = cart.getOrderItemList();
            //判断该商家有没有该商品,
            TbOrderItem orderItem = isSameItem(orderItemList,itemId);
            if(orderItem!=null){
                //该商家有该商品,该商品返回的是orderItemList里面的商品
                //需要修改该商品的数量和价格,直接进行修改即可
                orderItem.setNum(orderItem.getNum()+num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getNum()*orderItem.getPrice().doubleValue()));
                //因为商品数据前台的(-)点击功和删除功能会传负数,所以要进行判断
                //如果商品数量小于1,移除商品
                if(orderItem.getNum()<1){
                    orderItemList.remove(orderItem);
                }
                //移除的过程中可能该商家的商都被我们移除了,所以也要进行判断
                if(orderItemList.size()==0){
                    catList.remove(cart);
                }

            }else {
                //该商家没有该商品
                 orderItem = creatOrderItem(item, num);
                 orderItemList.add(orderItem);

            }

        }
        return catList;

    }

    /**
     * 判断该商家是否有该商品
     * @param orderItemList
     * @param itemId
     * @return
     */
    public TbOrderItem isSameItem(List<TbOrderItem> orderItemList, Long itemId) {
        for (TbOrderItem tbOrderItem : orderItemList) {
            if(itemId.equals(tbOrderItem.getItemId())){
                return tbOrderItem;
            }
        }
        return null;
    }

    /**
     * 创建一个购物车的商品对象
     * @param item
     * @param num
     * @return
     */

    public TbOrderItem creatOrderItem(TbItem item, Integer num) {
        TbOrderItem tbOrderItem = new TbOrderItem();
        tbOrderItem.setGoodsId(item.getGoodsId());
        tbOrderItem.setGoodsId(item.getGoodsId());
        tbOrderItem.setItemId(item.getId());
        tbOrderItem.setTitle(item.getTitle());
        tbOrderItem.setPrice(item.getPrice());
        tbOrderItem.setNum(num);
        tbOrderItem.setTotalFee(new BigDecimal(num*item.getPrice().doubleValue()));
        tbOrderItem.setPicPath(item.getImage());
        tbOrderItem.setSellerId(item.getSellerId());
        return tbOrderItem;
    }

    /**
     * 判断该购物车是否有该商家
     * @param catList
     * @param sellerId
     * @return
     */
    public Cart isSameCart(List<Cart> catList, String sellerId) {
        for (Cart cart : catList) {
            if(sellerId.equals(cart.getSellerId())){
                return cart;
            }
        }
        return null;
    }
}
