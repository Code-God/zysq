package com.wfsc.services.account;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.account.Address;
import com.wfsc.common.bo.account.Coupon;
import com.wfsc.common.bo.account.Favourite;
import com.wfsc.common.bo.account.ReturnProducts;

/**
 * 会员中心：交易管理：我的订单，商品评论， 我的收藏，退换货
 * 会员中心：账户管理：个人资料，收货地址，积分，优惠劵
 * 
 */
public interface IAccountService {
	public void saveOrUpdateAddress(Address address);
	public void saveOrUpdateCoupon(Coupon coupon);
	public void saveOrUpdateFavourite(Favourite favourite);
	public void saveOrUpdateReturnProducts(ReturnProducts returnProducts);
	
	public void deleteAddressById(Long id);
	public void deleteCouponById(Long id);
	public void deleteFavouriteById(Long id);
	public void deleteReturnProductsById(Long id);
	
	public Page<Address> findAddressForPage(Page<Address> page, Map<String, Object> paramap);
	public Page<Coupon> findCouponForPage(Page<Coupon> page, Map<String, Object> paramap);
	public Page<Favourite> findFavouriteForPage(Page<Favourite> page, Map<String, Object> paramap);
	public Page<ReturnProducts> findReturnProductsForPage(Page<ReturnProducts> page, Map<String, Object> paramap);
	
	public List<Address> findAddressByUserid(Long id);
	public List<Coupon> findCouponByUserid(Long id);
	public List<Favourite> findFavouriteByUserid(Long id);
	public List<ReturnProducts> findReturnProductsByUserid(Long id);
	
	public Address findAddressById(Long id);
	public Coupon findCouponById(Long id);
	public Favourite findFavouriteById(Long id);
	public ReturnProducts findReturnProductsById(Long id);
	
	public void setDefaultAddr(Long id,Long userId);
	public List<Coupon> findCouponByUseridAndstatus(Long userid,Integer status);
	
}
