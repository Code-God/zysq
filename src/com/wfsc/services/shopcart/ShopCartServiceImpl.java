package com.wfsc.services.shopcart;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.shopcart.ShopCart;
import com.wfsc.daos.shopcart.ShopCartDao;
import com.wfsc.services.product.IProductsService;

@Service("shopCartService")
@SuppressWarnings("unchecked")
public class ShopCartServiceImpl implements IShopCartService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Autowired
	private ShopCartDao shopCartDao;
	
	@Autowired
	private IProductsService productsService;

	public Long add(ShopCart shopCart) {
		return shopCartDao.saveEntity(shopCart);
	}
	
	public void update(ShopCart shopCart){
		shopCartDao.updateEntity(shopCart);
	}

	@Override
	public boolean delFromCart(Long cid) {
		try{
			shopCartDao.deleteEntity(cid);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	@Override
	public boolean delByPrdCodeAndUid(Long userId, String prdCode) {
		try{
			shopCartDao.deleteByUserIdAndPrdCode(userId, prdCode);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public List<ShopCart> list(Long userId) {
		List<ShopCart> allEntities = shopCartDao.getShopCartByUserId(userId);
		//附加产品对象
		for (ShopCart sc : allEntities) {
			Products product = productsService.getByPrdCode(sc.getPrdCode());
			if(product != null)
				sc.setProduct(product);
		}
		return allEntities;
	}

	@Override
	public void clearShopCartByUserId(long userId) {
		shopCartDao.deleteShopCartByUserId(userId);
	}

	@Override
	public void add2Cart(ShopCart sc) {
		ShopCart shopCart = shopCartDao.getScByUserIdAndPrdCode(sc.getUserId(), sc.getPrdCode());
		if(shopCart != null){
			shopCart.setCount(shopCart.getCount() + sc.getCount());
			shopCartDao.updateEntity(shopCart);
		}else{
			shopCartDao.saveEntity(sc);
		}
	}
}
