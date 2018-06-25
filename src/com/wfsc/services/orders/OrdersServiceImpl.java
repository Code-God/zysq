package com.wfsc.services.orders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.account.Address;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.order.OrdersDetail;
import com.wfsc.common.bo.shopcart.ShopCart;
import com.wfsc.common.bo.user.User;
import com.wfsc.daos.GenOrderNumDao;
import com.wfsc.daos.account.AddressDao;
import com.wfsc.daos.orders.OrdersDao;
import com.wfsc.daos.orders.OrdersDetailDao;
import com.wfsc.daos.stock.ProductStockDao;
import com.wfsc.daos.user.UserDao;
import com.wfsc.services.shopcart.IShopCartService;

@Service("ordersService")
public class OrdersServiceImpl implements IOrdersService {
	Logger log = LogUtil.getLogger(LogUtil.SERVER);
	@Resource
	private OrdersDao ordersDao;
	@Resource
	private OrdersDetailDao ordersDetailDao;
	@Resource
	private GenOrderNumDao genOrdernumDao;
	@Resource
	private AddressDao addressDao;
	@Resource
	private UserDao userDao;
	@Resource
	private ProductStockDao productStockDao;

	@Resource(name = "shopCartService")
	private IShopCartService shopCartService;
	
	/**
	 * 生成订单号
	 * 格式=={5位保留字符}+YYYYMMDD+6位递增序号
	 * 例如 D0000-20150104-000012
	 */
	public synchronized String generateCodeByType(String type){
		Long serilNum = genOrdernumDao.getOrderNum(type);
		String code = "";
		if("orderNo".equals(type)){
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String dfm = sf.format(new Date());
			//6位递增序号，不够的前面补0
			String numCode = String.format("%06d", serilNum);  
			code = "D0000-"+dfm+"-"+numCode;
			return code;
		}
		return type+"-"+serilNum.toString();
	}

	@Override
	public List<Orders> getOrderByUserId(long userId, int status, int start, int limit) {
		return ordersDao.getOrderByUserId(userId, status, start, limit);
	}
	
	public Orders getOrderByCode(String orderNo){
		Orders o = ordersDao.getUniqueEntityByOneProperty("orderNo", orderNo);
		List<OrdersDetail> ods = ordersDetailDao.getEntitiesByOneProperty("orderNo", orderNo);
		if(o!=null && ods!=null){
			o.setOrdersDetail(ods);
		}
		return o;
	}
	
	public OrdersDetail getOrdersDetailById(Long id){
		return ordersDetailDao.getEntityById(id);
	}
	
	public void saveOrUpdateDetail(OrdersDetail ordersDetail){
		ordersDetailDao.saveOrUpdateEntity(ordersDetail);
	}
	
	@Override
	public String createOrder(Long userId, String bankType, Long addressId, Integer cityCode){

		Address addr = addressDao.getEntityById(addressId);
		
		User user = userDao.getEntityById(userId);
		
		String orderNo = generateCodeByType("orderNo");
		List<ShopCart> list = shopCartService.list(userId);
		Date buyDate = new Date();
		// 订单总金额
		long fee = 0;
		// 订单预生成
		for(ShopCart shopCart : list){
			OrdersDetail detail = new OrdersDetail(shopCart.getProduct());
			detail.setBuyDate(buyDate);
			detail.setOrderNo(orderNo);
			detail.setPrdCount(shopCart.getCount());
			long price = shopCart.getProduct().getDisPrice() == null ? shopCart.getProduct().getPrice() : shopCart.getProduct().getDisPrice();
			long total = shopCart.getCount() * price;
			detail.setTotalPrice(total);
			ordersDetailDao.saveEntity(detail);
			fee += total;
			// 更新库存数量
			productStockDao.reduceStock(cityCode, shopCart.getPrdCode(), shopCart.getCount());
		}
		
		Orders order = new Orders();
		order.setOrderNo(orderNo);
		order.setOdate(buyDate);
		order.setStatus(0);
		order.setFeePrice(fee);
		// TODO 运费 如何获得??
		order.setTransFee(0f);
		order.setBankType(bankType);
		// 收货信息
		order.setAddress(addr.getDetailAddr());
		order.setAddressee(addr.getUsername());
		order.setPhone(addr.getPhone());
		order.setUser(user);
		
		ordersDao.saveEntity(order);
		
		//订单生成成功后，删除购物车里面已经下单的物品
		shopCartService.clearShopCartByUserId(userId);
		
		return orderNo;
	}

	@Override
	public List<OrdersDetail> getOrderDetailsByOrderNo(String orderNo) {
		List<OrdersDetail> orderDetails = ordersDetailDao.getEntitiesByOneProperty("orderNo", orderNo);
		return orderDetails;
	}

	@Override
	public void saveOrUpdateOrder(Orders orders) {
		ordersDao.saveOrUpdateEntity(orders);
	}

	@Override
	public List<OrdersDetail> getOrdersDetailByUser(Long userId,
			Integer isComment) {
		return ordersDetailDao.getOrdersDetailByUser(userId,isComment);
	}
	public Page<Orders> findForPage(Page<Orders> page, Map<String,Object> paramap){
		return ordersDao.findForPage(page, paramap);
	}
	
	public void deleteByIds(List<Long> ids){
		ordersDao.deletAllEntities(ids);
	}

	@Override
	public void createCarInsOrder(Orders order) {
		ordersDao.saveEntity(order);
	}
}
