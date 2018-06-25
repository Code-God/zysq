package com.wfsc.services.pay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.wfsc.common.bo.order.Consume;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.user.User;
import com.wfsc.daos.orders.OrdersDao;
import com.wfsc.daos.pay.ConsumeDao;
import com.wfsc.daos.user.UserDao;
import com.wfsc.tenpay.RequestHandler;
import com.wfsc.tenpay.ResponseHandler;
import com.wfsc.util.SysUtil;

@Service("finService")
public class FinanceServiceImpl implements IFinanceService {

	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	@Autowired
	private OrdersDao ordersDao;

	@Autowired
	private ConsumeDao consumeDao;
	@Autowired
	private UserDao userDao;

	@Override
	public boolean createConsume(Consume cm) {
		try {
			consumeDao.saveEntity(cm);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Object> getConsumeList(Map<String, String> param, int start, int pageSize) {
		try{
			List<Object> rtList = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer("from Consume c where 1=1 ");
			if (param.get("userId") != null) {
				Long userId = Long.valueOf(param.get("userId"));
				hql.append(" and c.user.id= " + userId);
			}
			if (param.get("startTime") != null) {
				hql.append(" and c.consumeTime >= '" + param.get("startTime").toString() + "' ");
			}
			if (param.get("endTime") != null) {
				hql.append(" and c.consumeTime <= '" + param.get("endTime").toString() + "' ");
			}
			
			String countHql = "select count(id) " + hql.toString();
			List find = consumeDao.getHibernateTemplate().find(countHql);
			List<Consume> list = consumeDao.getPagingEntitiesByHql(hql.toString(), start, pageSize);
			rtList.add(find.get(0));
			rtList.add(list);
			return rtList;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Object> getMyOrders(Map<String, String> param, int start, int pageSize) {
		StringBuffer hql = new StringBuffer("from Orders c where status="+ Orders.STATUS_OK +" and 1=1 ");
		if (param.get("userId") != null) {
			Long userId = Long.valueOf(param.get("userId"));
			hql.append(" and c.user.id= " + userId);
		}
		if (param.get("startTime") != null) {
			hql.append(" and c.chargeDate >= '" + param.get("startTime").toString() + "' ");
		}
		if (param.get("endTime") != null) {
			hql.append(" and c.chargeDate <= '" + param.get("endTime").toString() + "' ");
		}
		String countHql = "select count(id) " + hql.toString();
		List find = ordersDao.getHibernateTemplate().find(countHql);
		List<Orders> list = ordersDao.getPagingEntitiesByHql(hql.toString(), start, pageSize);
		
		List<Object> rtList = new ArrayList<Object>();
		rtList.add(find.get(0));
		rtList.add(list);
		return rtList;
	}

	@Override
	public boolean saveOrder(Orders order) {
		try {
			ordersDao.saveEntity(order);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}

	public void setConsumeDao(ConsumeDao consumeDao) {
		this.consumeDao = consumeDao;
	}

	@Override
	public void createOrder(RequestHandler rh, Long id) {
			logger.info("生成订单....");
			Orders order = new Orders();
//			mo.setBankBillNo(rh.getParameter("bank_billno"));//银行订单号
			order.setOrderNo(rh.getParameter("out_trade_no"));//订单号
			String chargeFee = rh.getParameter("total_fee");//单位：分
			double total_fee = Double.valueOf(chargeFee);
			int fee = (int) total_fee;//单位是分，订单表里需要转化成元
			order.setFee(fee/100f);
			order.setChargeDate(new Date());
			order.setCtype(Orders.CTYPE_RMB);
			order.setStatus(Orders.STATUS_PENDING);
			User user = userDao.getEntityById(id);
			order.setUser(user);
			ordersDao.saveEntity(order);
			logger.info("创建订单成功.....");
	}

	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public int notifySuccess(ResponseHandler resHandler) {
		// 商户订单号
		String out_trade_no = resHandler.getParameter("out_trade_no");
		// 金额,以分为单位
		String total_fee = resHandler.getParameter("total_fee");
		// 如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
		String discount = resHandler.getParameter("discount");
		//银行订单号
		String bankBillNo = resHandler.getParameter("bank_billno");
		String bankType = resHandler.getParameter("bank_type");
		String notifyId = resHandler.getParameter("notify_id");
		//财付通订单号
		String transactionId = resHandler.getParameter("transaction_id");
		
		try{
			//1 处理数据库逻辑,更新订单记录，
			// 注意交易单不要重复处理
			// 注意判断返回金额
			Orders order = ordersDao.getOrderByTradeNo(out_trade_no);
			if(order != null){
				logger.info("订单号：" + out_trade_no + " 订单金额：" + order.getFee() + "   total_fee=" + total_fee);
				if(order.getFee() * 100 != Double.valueOf(total_fee).doubleValue()){
					//费用不一致，异常。
					order.setStatus(Orders.STATUS_FAIL);
					ordersDao.updateEntity(order);
					return 0;//不用再通知了
				}else if(order.getStatus() == Orders.STATUS_OK){
					return 0;//不用再通知了
				}
				//更新订单数据
				order.setBankBillNo(bankBillNo);
				order.setBankType(bankType);
				order.setStatus(Orders.STATUS_OK);//成功
//				order.setUserId(userId);//之前就有的。
				ordersDao.updateEntity(order);
				logger.info("支付网关成功通知.........."+ notifyId);
				
				//2 往用户表里更新金币
				User user = userDao.getEntityById(order.getUser().getId());
				//因为totalFee的单位是分，所以这里要除以100
				int gold = (int) (Integer.valueOf(total_fee)/100);
//				user.setGold(user.getGold() + gold);
				
				//生成绑定密码
				generateBindCode(user);
				
				userDao.updateEntity(user);
				logger.info(" | 用户：" + user.getNickName()+ " 成功充值了" + gold + "金币。");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("即时到帐充值异常...." + e.getMessage());
		}
		
		return 0;
	}
	
	private void generateBindCode(User user) {
//		if(user.getVipCode() == null){ //尚未生成
			String code = SysUtil.getBindCode();
			logger.info("生成vip密码：" + code);
//			user.setVipCode(code);
//		}
	}

	public static void main(String[] args){
		float f = 0.01f;
		if(f * 100 != Double.valueOf(1).doubleValue()){
			System.out.println("1111");
		}
	}

	@Override
	public int getTotalFee(Long userId) {
		String sql = "select sum(fee) from Orders where userId = " + userId + " and status = 2";
		List find = this.ordersDao.getHibernateTemplate().find(sql);
		logger.info("getTotalFee find.get(0)=" + find.get(0));
		if(find.get(0) != null){
			String s = find.get(0).toString();
			return Float.valueOf(s).intValue();
		}else{
			return 0;
		}
	}
}
