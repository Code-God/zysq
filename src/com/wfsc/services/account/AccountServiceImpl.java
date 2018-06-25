package com.wfsc.services.account;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.util.Page;
import com.wfsc.common.bo.account.Address;
import com.wfsc.common.bo.account.Coupon;
import com.wfsc.common.bo.account.Favourite;
import com.wfsc.common.bo.account.ReturnProducts;
import com.wfsc.daos.account.AddressDao;
import com.wfsc.daos.account.CouponDao;
import com.wfsc.daos.account.FavouriteDao;
import com.wfsc.daos.account.ReturnProductsDao;
import com.wfsc.daos.orders.OrdersDao;
import com.wfsc.daos.user.UserDao;

@Service("accountService")
@SuppressWarnings("unchecked")
public class AccountServiceImpl implements IAccountService {

	@Resource
	private AddressDao addressDao;

	@Resource
	private FavouriteDao favouriteDao;

	@Resource
	private CouponDao couponDao;

	@Resource
	private ReturnProductsDao returnProductsDao;

	@Resource
	private UserDao userDao;

	@Resource
	private OrdersDao ordersDao;

	@Override
	public void deleteAddressById(Long id) {
		addressDao.deleteEntity(id);
	}

	@Override
	public void deleteCouponById(Long id) {
		couponDao.deleteEntity(id);
	}

	@Override
	public void deleteFavouriteById(Long id) {
		favouriteDao.deleteEntity(id);
	}

	@Override
	public void deleteReturnProductsById(Long id) {
		returnProductsDao.deleteEntity(id);
	}

	@Override
	public List<Address> findAddressByUserid(Long id) {
		return addressDao.getEntitiesByOneProperty("userid", id);
	}

	@Override
	public Page<Address> findAddressForPage(Page<Address> page, Map<String, Object> paramap) {
		return addressDao.findForPage(page, paramap);
	}

	@Override
	public List<Coupon> findCouponByUserid(Long id) {
		return couponDao.getEntitiesByOneProperty("userid", id);
	}

	@Override
	public Page<Coupon> findCouponForPage(Page<Coupon> page, Map<String, Object> paramap) {
//		return couponDao.q(page, paramap);
		return null;
	}

	@Override
	public List<Favourite> findFavouriteByUserid(Long id) {
		return favouriteDao.getEntitiesByOneProperty("userid", id);
	}

	@Override
	public Page<Favourite> findFavouriteForPage(Page<Favourite> page, Map<String, Object> paramap) {
		return favouriteDao.findForPage(page, paramap);
	}

	@Override
	public List<ReturnProducts> findReturnProductsByUserid(Long id) {
		return returnProductsDao.getEntitiesByOneProperty("userid", id);
	}

	@Override
	public Page<ReturnProducts> findReturnProductsForPage(Page<ReturnProducts> page, Map<String, Object> paramap) {
		return returnProductsDao.findForPage(page, paramap);
	}

	@Override
	public void saveOrUpdateAddress(Address address) {
		//默认地址的处理， 先重置所有默认地址标识。
		if(address.getIsDefault() != null && address.getIsDefault() == 1){
			addressDao.getHibernateTemplate().bulkUpdate("update Address set isDefault = 0 where userid=" + address.getUserid());
		}
		addressDao.saveOrUpdateEntity(address);
	}

	@Override
	public void saveOrUpdateCoupon(Coupon coupon) {
		couponDao.saveOrUpdateEntity(coupon);
	}

	@Override
	public void saveOrUpdateFavourite(Favourite favourite) {
		favouriteDao.saveOrUpdateEntity(favourite);
	}

	@Override
	public void saveOrUpdateReturnProducts(ReturnProducts returnProducts) {
		returnProductsDao.saveOrUpdateEntity(returnProducts);
	}

	@Override
	public Address findAddressById(Long id) {
		return addressDao.getEntityById(id);
	}

	@Override
	public Coupon findCouponById(Long id) {
		return couponDao.getEntityById(id);
	}

	@Override
	public Favourite findFavouriteById(Long id) {
		return favouriteDao.getEntityById(id);
	}

	@Override
	public ReturnProducts findReturnProductsById(Long id) {
		return returnProductsDao.getEntityById(id);
	}

	@Override
	public void setDefaultAddr(Long id, Long userId) {
		List<Address> addrlist = addressDao.getEntitiesByPropNames(new String[] { "userid", "isDefault" }, new Object[] {
				userId, 1 });
		if (addrlist != null && addrlist.size() > 0) {
			Address addrDef = addrlist.get(0);
			addrDef.setIsDefault(0);
			saveOrUpdateAddress(addrDef);
		}
		Address addr = findAddressById(id);
		addr.setIsDefault(1);
		saveOrUpdateAddress(addr);
	}

	public List<Coupon> findCouponByUseridAndstatus(Long userid, Integer status) {
		return couponDao.getEntitiesByPropNames(new String[] { "userid", "status" }, new Object[] { userid, status });
	}
}
