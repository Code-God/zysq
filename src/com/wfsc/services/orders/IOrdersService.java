package com.wfsc.services.orders;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.order.OrdersDetail;

public interface IOrdersService {
	
	public String generateCodeByType(String type);
	
	/**
	 * 查询我的订单，支持分页
	 * @param userId
	 * @param status 订单类型： 0 – 未完成， 1-已完成 2-已作废
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Orders> getOrderByUserId(long userId, int status, int start, int limit);
	
	public Orders getOrderByCode(String orderNo);
	
	public OrdersDetail getOrdersDetailById(Long id);
	
	public void saveOrUpdateDetail(OrdersDetail ordersDetail);

	/**
	 * 创建订单和详情
	 * @param userId 用户ID
	 * @param bankType 银行类型
	 * @param addressId 收货地址信息
	 * @param cityCode TODO
	 * @return 订单号
	 */
	public String createOrder(Long userId, String bankType, Long addressId, Integer cityCode);
	
	/**
	 * 根据订单单号查询该订单下的已下单商品
	 * @param orderNo
	 * @return
	 */
	public List<OrdersDetail> getOrderDetailsByOrderNo(String orderNo);
	public void saveOrUpdateOrder(Orders orders);
	public List<OrdersDetail> getOrdersDetailByUser(Long userId,Integer isComment);
	public Page<Orders> findForPage(Page<Orders> page, Map<String,Object> paramap);
	public void deleteByIds(List<Long> ids);

	public void createCarInsOrder(Orders order);
}
