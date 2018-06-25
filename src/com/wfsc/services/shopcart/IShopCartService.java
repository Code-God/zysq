package com.wfsc.services.shopcart;

import java.util.List;

import com.wfsc.common.bo.shopcart.ShopCart;

/**
 * 商品分类
 * 
 * @author Xiaojiapeng
 * 
 */
public interface IShopCartService {

	/**
	 * 添加购物记录
	 * 
	 * @param shopCart 购物记录
	 */
	public Long add(ShopCart shopCart);
	
	/**
	 * 更新购物记录
	 * @param shopCart
	 */
	public void update(ShopCart shopCart);

	/**
	 * 列表方法
	 * 查询用户购物记录
	 * @param userId
	 * @return
	 */
	public List<ShopCart> list(Long userId);
	
	/**
	 * 从购物车删除条目
	 * @param cid - 购物车数据库ID
	 * @return
	 */
	public boolean delFromCart(Long cid);
	
	/**
	 * 从购物车中删除单个商品
	 * @param userId
	 * @param prdCode
	 * @return
	 */
	public boolean delByPrdCodeAndUid(Long userId, String prdCode);
	
	/**
	 * 清空用户的购物车
	 * @param userId
	 */
	public void clearShopCartByUserId(long userId);
	
	/**
	 * 添加到购物车，手机APP专用
	 * @param sc
	 */
	public void add2Cart(ShopCart sc);
}
