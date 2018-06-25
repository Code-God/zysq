package com.wfsc.actions.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.log.LogUtil;
import com.constants.CupidStrutsConstants;
import com.wfsc.annotation.Login;
import com.wfsc.common.bo.account.Address;
import com.wfsc.common.bo.account.Favourite;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.shopcart.ShopCart;
import com.wfsc.common.bo.system.City;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IAccountService;
import com.wfsc.services.favourite.IFavouriteService;
import com.wfsc.services.orders.IOrdersService;
import com.wfsc.services.product.IProductsService;
import com.wfsc.services.shopcart.IShopCartService;
import com.wfsc.services.stock.IStockService;
import com.wfsc.util.SysUtil;

/**
 * 购物车
 * 
 * @author Xiaojiapeng
 * 
 */
@SuppressWarnings("unchecked")
@Controller("shoppingAction")
@Scope("prototype")
@Login
public class ShoppingAction extends CupidBaseAction {

	private static final long serialVersionUID = -3567393270844062097L;
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	@Resource(name = "shopCartService")
	private IShopCartService shopCartService;
	
	@Resource(name = "favouriteService")
	private IFavouriteService favouriteService;
	
	@Resource(name = "stockService")
	private IStockService stockService;

	@Resource(name = "ordersService")
	private IOrdersService ordersService;
	
	@Resource(name = "accountService")
	private IAccountService accountService;
	
	@Autowired
	private IProductsService productService;
	
	// 添加到购物车
	public String add() {

		String prdCode = getPara("prdCode");
		int num = getParaToInt("num");

		updateShopPro(prdCode, num);
		
		request.setAttribute("prdCode", prdCode);

		return "add";
	}

	/**
	 * 更新购物车商品信息
	 * @param prdCode
	 * @param num
	 */
	private void updateShopPro(String prdCode, int num) {
		
		List<ShopCart> list = (List<ShopCart>) super.session.get(CupidStrutsConstants.SESSION_SHOP);
		if (list == null) {
			list = new ArrayList<ShopCart>();
		}
		// 重复++
		boolean flag = true;
		for (ShopCart x : list) {
			if (x.getPrdCode().equals(prdCode)) {
				x.setCount(x.getCount() + num);
				flag = false;
				// 更新购物记录
				shopCartService.update(x);
			}
		}
		// 不重复新增
		if (flag) {
			long uid = getCurrentUser().getId();
			
			ShopCart shop = new ShopCart();
			shop.setPrdCode(prdCode);
			shop.setCount(num);
			shop.setUserId(uid);
			// 持久化购物记录
			long id = shopCartService.add(shop);
			shop.setId(id);
			list.add(shop);
		}

		super.session.put(CupidStrutsConstants.SESSION_SHOP, list);
	}

	// 从购物车删除
	public String delete() {

		long uid = getCurrentUser().getId();
		String prdCode = getPara("prdCode");

		List<ShopCart> list = (List<ShopCart>) super.session.get(CupidStrutsConstants.SESSION_SHOP);
		if (CollectionUtils.isEmpty(list)) {
			return "toList";
		}

		// 删除商品
		Iterator<ShopCart> it = list.iterator();
		while (it.hasNext()) {
			ShopCart x = it.next();
			if (x.getPrdCode().equals(prdCode)){
				it.remove();
				// delete db info
				shopCartService.delByPrdCodeAndUid(uid, prdCode);
			}
		}

		super.session.put(CupidStrutsConstants.SESSION_SHOP, list);

		return "toList";
	}
	
	// 清空购物车
	public String deleteAll() {
		long uid = getCurrentUser().getId();
		
		super.session.put(CupidStrutsConstants.SESSION_SHOP, null);
		// delete db info
		shopCartService.clearShopCartByUserId(uid);

		return "toList";
	}

	// shopping
	public String list() {
		
		long uid = getCurrentUser().getId();
		
		User user = (User)session.get(CupidStrutsConstants.SESSION_USER);
		List<Favourite> favourites = favouriteService.getFavouritesByUserId(user.getId());
		
		// 此存必须查库(自动联查了商品信息)，因为添加商品时没有将商品信息完整的添入购物车seesion
		List<ShopCart> list = shopCartService.list(uid);
		for(ShopCart x : list){
			Products prd = x.getProduct();
			x.setDisMoney((prd.getPrice() - prd.getDisPrice()) * x.getCount() / 100f);
			x.setSumMoney(prd.getDisPrice()* x.getCount() /100f);
			for(Favourite fav : favourites){
				if(StringUtils.equals(fav.getProducts().getPrdCode(), prd.getPrdCode())){
					prd.setFavourite(true);
					break;
				}
			}
			// 查询库存
			// 获取城市信息
			City city = (City)super.session.get(CupidStrutsConstants.CURR_CITY);
			int stock = stockService.getStockByPCodeAndCCode(prd.getPrdCode(), city.getCode());
			prd.setStock(stock);
		}
		super.session.put(CupidStrutsConstants.SESSION_SHOP, list);
		setAttr("list", list);
		
		return "list";
	}

	// 订单确认
	public String confirm(){
		
		// 收货地址列表
		User user = this.getCurrentUser();
		List<Address> addrlist = accountService.findAddressByUserid(user.getId());
		
		setAttr("addrlist", addrlist);
		// 购物记录
		List<ShopCart> list = (List<ShopCart>) super.session.get(CupidStrutsConstants.SESSION_SHOP);
		if (CollectionUtils.isEmpty(list)) {
			return "toList";
		}
		setAttr("list", list);
		
		// 防止重复提交
		setAttr("hash", System.currentTimeMillis());
		
		return "order";
	}
	
	// 提交前置检查，库存是否充足
	public void checkStock(){
		// 获取城市信息
		City city = (City)super.session.get(CupidStrutsConstants.CURR_CITY);
		
		JSONObject json = new JSONObject();
		json.put("code", 1);
		
		// 购物记录
		List<ShopCart> list = (List<ShopCart>) super.session.get(CupidStrutsConstants.SESSION_SHOP);
		for(ShopCart shopCart : list){
			int stock = stockService.getStockByPCodeAndCCode(shopCart.getPrdCode(), city.getCode());
			if (stock < 1) {
				json.put("code", 0);
				json.put("name", shopCart.getProduct().getName());
			}
		}

		renderJson(json.toString());
	}
	final static String FROMHASHS = "FROMHASHS";
	// 提交订单
	public String commit(){
		
		// 检查重复提交
		String hash = getPara("hash");
		if (!StringUtils.isEmpty(hash)) {
			List<String> list = (List) session.get(FROMHASHS);
			if (list == null) {
				list = new ArrayList<String>();
			}
			if (list.contains(hash)) {
				// 重复跳到我的订单
				return "toMyOrder";
			} else {
				// 不重复
				list.add(hash);
				session.put(FROMHASHS, list);
			}
		}
		
		long uid = getCurrentUser().getId();
		// 获取城市信息
		City city = (City)super.session.get(CupidStrutsConstants.CURR_CITY);
		
		long addrId = getParaToLong("sel_addr");
		
		// 获取银行类型
		String bankType = getPara("sel_online_pay");
		String orderNo = ordersService.createOrder(uid, bankType, addrId, city.getCode());
		setAttr("orderNo", orderNo);
		
		// 清空购物车
		super.session.put(CupidStrutsConstants.SESSION_SHOP, null);
		
		return "commit";
	}
	
	// ajax增加
	public void increase(){
		String prdCode = getPara("prdCode");
		
		updateShopPro(prdCode, 1);
		
		JSONObject json = new JSONObject();
		json.put("code", 1);
		
		renderJson(json.toString());
	}
	
	// ajax减少
	public void reduce(){
		String prdCode = getPara("prdCode");
		
		updateShopPro(prdCode, -1);
		
		JSONObject json = new JSONObject();
		json.put("code", 1);
		
		renderJson(json.toString());
	}

	// ajax 收藏
	public void addFav() throws IOException{
		long uid = getCurrentUser().getId();
		long cid = getParaToLong("cid");
		String prdCode = request.getParameter("prdCode");
		JSONObject json = new JSONObject();
		if(StringUtils.isEmpty(prdCode)){
			json.put("result", "failed");
			json.put("msg", "无效参数");
		}else{
			Products product = productService.findByCode(prdCode);
			if(product == null){
				json.put("result", "failed");
				json.put("msg", "商品已下架");
			}else{
				//检测购物车里面是否存在该商品 如果已存在，则不做操作，购物车内删除即可，如果不存在，则移至收藏夹
				Favourite favourite = favouriteService.getFavouriteByUserIdAndPrdId(uid, product.getId());
				if(favourite == null){
					//加入收藏夹
					favourite = new Favourite();
					favourite.setUserid(uid);
					favourite.setStoreDate(new Date());
					favourite.setProducts(product);
					
					favouriteService.add(favourite);
					//删除购物车里面的这条记录
					shopCartService.delFromCart(cid);
					
					json.put("result", "success");
					json.put("msg", "加入收藏夹成功");
				}else{
					//删除购物车里面的这条记录
					shopCartService.delFromCart(cid);
					
					json.put("result", "success");
					json.put("msg", "加入收藏夹成功");
				}
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		response.getWriter().flush();
		
		
	}
	
	// ajax 取消收藏
	public void reduceFav(){
		long uid = getCurrentUser().getId();
		Long proId = getParaToLong("proId");
		
		favouriteService.deleteByUserIdAndProId(uid, proId);
	}
}
