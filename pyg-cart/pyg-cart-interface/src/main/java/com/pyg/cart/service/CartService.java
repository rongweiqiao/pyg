package com.pyg.cart.service;

import com.pyg.vo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findRedisCartList(String loginName);

    List<Cart> addRedisAndCookie(List<Cart> redisCatList, List<Cart> cookieCartList);


    void addRedisCart(List<Cart> redisCatList, String loginName);

    public List<Cart> addItemsToCartList(List<Cart> cartList, Long itemId, Integer num);
}
