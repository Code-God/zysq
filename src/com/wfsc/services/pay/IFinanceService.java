package com.wfsc.services.pay;

import java.util.List;
import java.util.Map;

import com.wfsc.common.bo.order.Consume;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.tenpay.RequestHandler;
import com.wfsc.tenpay.ResponseHandler;


/**
 * 财务相关service 
 *
 * @author jacky
 * @version 1.0
 * @since cupid 1.0
 */
public interface IFinanceService {
	/**
	 * 订单查询
	 * Long userId, String startTime, String endTime 
	 * @param param - 查询参数
	 * @param start
	 * @param pageSize
	 * @return lis[0] - total list[1] - List&lt;Orders&gt;  
	 */
	public List<Object>  getMyOrders(Map<String, String> param, int start, int pageSize);
	
	/**
	 * 消费记录查询，可按时间
	 * @param param - 查询参数
	 * @param start
	 * @param pageSize
	 * @return  lis[0] - total list[1] - List&lt;Consume&gt; 
	 */
	public List<Object> getConsumeList(Map<String, String> param, int start, int pageSize);
	
	/** 保存订单 */
	public boolean saveOrder(Orders order);
	/**
	 * 新增消费记录 
	 **/
	public boolean createConsume(Consume cm);

	/**
	 * 创建一个订单 
	 * @param reqHandler
	 * @param id
	 */
	public void createOrder(RequestHandler reqHandler, Long id);
	
	/**
	 * 财付通服务器返回的支付成功通知
	 * @param resHandler
	 */
	public int notifySuccess(ResponseHandler resHandler);

	/**
	 * 返回该用户所有的充值累计费用
	 * @param userId
	 * @return
	 */
	public int getTotalFee(Long userId);
	
}
