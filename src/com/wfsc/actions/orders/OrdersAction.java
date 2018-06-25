package com.wfsc.actions.orders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.base.util.Page;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.order.OrdersDetail;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.orders.IOrdersService;
import com.wfsc.services.system.ISystemLogService;
import com.wfsc.util.DateUtil;

/**
 * 
 * @author hl
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("OrdersAction")
@Scope("prototype")
public class OrdersAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840813332299260353L;

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);
	
	@Resource(name = "ordersService")
	private IOrdersService ordersService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	private Orders orders;
	
	private OrdersDetail od;

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public OrdersDetail getOd() {
		return od;
	}

	public void setOd(OrdersDetail od) {
		this.od = od;
	}
	public String manager(){
		list();
		return "manager";
	}
	
	public String list(){
		try{
			Page<Orders> page = new Page<Orders>();
			Map<String,Object> paramap = new HashMap<String,Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String orderNo = request.getParameter("orderNo");
			String status = request.getParameter("status");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			
			//从分销客管理查询订单传递的参数： 分销客用户ID
			String personId = request.getParameter("personId");
			
			if(StringUtils.isNotEmpty(orderNo)){
				paramap.put("orderNo", orderNo);
				request.setAttribute("orderNo", orderNo);
			}
			
			if(StringUtils.isNotEmpty(status)){
				paramap.put("status", status);
				request.setAttribute("status", Integer.valueOf(status));
			}
			if(StringUtils.isNotEmpty(startTime)){
				paramap.put("startTime", startTime+" 00:00:01");
				request.setAttribute("startTime", startTime);
			}
			if(StringUtils.isNotEmpty(endTime)){
				paramap.put("endTime", endTime+" 23:59:59");
				request.setAttribute("endTime", endTime);
			}
			if(StringUtils.isNotEmpty(personId)){
				paramap.put("personId", personId);
			}
			
			//需要根据当前登录管理员的分销商编码来过滤
			paramap.put("orgCode", this.getCurrentOrgCode());
			
			page = ordersService.findForPage(page, paramap);
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath() + "/admin/orders_list.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("orderslist", page.getData());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "list";
	}
	public String changeOrderState(){
		String orderState = request.getParameter("orderState");
		String ost = "";
		if("2".equals(orderState)){
			ost = "已发货";
		}else if("3".equals(orderState)){
			ost = "已完成";
		}
		String orderNo = request.getParameter("orderNo");
		Orders orders = ordersService.getOrderByCode(orderNo);
		orders.setStatus(Integer.valueOf(orderState));
		//设置发货时间
		orders.setDeliverDate(DateUtil.getLongCurrentDate());
		ordersService.saveOrUpdateOrder(orders);
		Admin admin = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_ORDER, admin.getUsername(), "将订单"+orders.getOrderNo()+"的状态改为" + ost);
		systemLogService.saveSystemLog(systemLog);
		return "ok";
	}
	public String ordersDetai(){
		String host = Version.getInstance().getNewProperty("image.server.ip");
		String port = Version.getInstance().getNewProperty("image.server.port");
		String url = "http://"+host+":"+port+"/images/";
		request.setAttribute("imgServer", url);
		String orderNo = request.getParameter("orderNo");
		orders = ordersService.getOrderByCode(orderNo);
		List<OrdersDetail> ods = new ArrayList<OrdersDetail>();
		if(orders!=null&&orders.getOrdersDetail()!=null){
			ods = orders.getOrdersDetail();
		}
		request.setAttribute("ods", ods);
		return "detail";
	}
	
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		ordersService.deleteByIds(idList);
		Admin admin = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_ORDER, admin.getUsername(), "删除订单");
		systemLogService.saveSystemLog(systemLog);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
