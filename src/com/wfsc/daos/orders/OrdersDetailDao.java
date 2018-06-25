package com.wfsc.daos.orders;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.order.OrdersDetail;

/**
 * 
 * 评论
 * 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ordersDetailDao")
public class OrdersDetailDao extends EnhancedHibernateDaoSupport<OrdersDetail> {
	@Override
	protected String getEntityName() {
		return OrdersDetail.class.getName();
	}
	public List<OrdersDetail> getOrdersDetailByUser(Long userId,
			Integer isComment){
		List<OrdersDetail> list = new ArrayList<OrdersDetail>();
//		String hql = "select distinct od from OrdersDetail as od left join com.wfsc.common.bo.order.Orders as o with od.orderNo=o.orderNo where o.status=3 and o.user.id="+userId+" and od.isComment="+isComment;
		String hql = "select distinct od from OrdersDetail od, Orders o where od.orderNo=o.orderNo and o.status=3 and o.user.id="+userId+" and od.isComment="+isComment;
//		String hql = "select distinct od from OrdersDetail as od  where od.isComment="+isComment;
		list = this.getHibernateTemplate().find(hql);
		return list;
	}
}