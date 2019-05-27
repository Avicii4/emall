package com.emall.service.impl;

import com.emall.common.Const;
import com.emall.common.ServerResponse;
import com.emall.dao.CartMapper;
import com.emall.pojo.Cart;
import com.emall.service.ICartService;
import com.emall.vo.CartProductVo;
import com.emall.vo.CartVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Harry Chou
 * @date 2019/5/27
 */
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    public ServerResponse add(Integer userId, Integer productId, Integer count){
        Cart cart=cartMapper.selectCartByUserIdProductId(userId,productId);
        if(cart==null){
            // 商品不在购物车中，则新增商品的记录
            Cart cartItem=new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartMapper.insert(cartItem);
        } else {
            // 商品在购物车中，增加数量
            count +=cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return null;
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo=new CartVo();
        List<Cart> cartList=cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();


    }
}
