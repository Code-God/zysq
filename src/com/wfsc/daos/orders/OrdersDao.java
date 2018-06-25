package com.wfsc.daos.orders;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.bo.act.UserHongBao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.order.Orders;

/**
 * 
 * 评论
 * 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ordersDao")
public class OrdersDao extends EnhancedHibernateDaoSupport<Orders> {
	@Override
	protected String getEntityName() {
		return Orders.class.getName();
	}
	
	/**
	 * 根据用户Id查询我的订单
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Orders> getOrderByUserId(final long userId, final int status, final int start, final int limit){
		return getHibernateTemplate().execute(new HibernateCallback<List<Orders>>(){

			@Override
			public List<Orders> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Orders where user.id = ? and status = ?";
				if(99==status){
					hql = "from Orders where user.id = ?";
				}
				Query query = session.createQuery(hql);
				query.setLong(0, userId);
				if(99!=status){
					query.setInteger(1, status);
				}
				query.setFirstResult(start);
				query.setMaxResults(99);
				return query.list();
			}
		});
	}
	
	public Page<Orders> findForPage(Page<Orders> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct o from Orders as o where 1=1 ");
		try {
			Date sdate = null;
			Date edate = null;
			for (String key : paramap.keySet()) {
				if ("orderNo".equals(key)) {
					hql.append(" and o.orderNo = :orderNo");
					dataMap.put("orderNo", paramap.get("orderNo"));
					continue;
				}
				if ("status".equals(key)) {
					hql.append(" and o.status = :status");
					dataMap.put("status", paramap.get("status"));
					continue;
				}
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and o.odate >= :sdate " );
					dataMap.put("sdate", sdate);
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and o.odate <= :edate ");
					dataMap.put("edate", edate);
				}
				
				if ("orgCode".equals(key)) {
					hql.append(" and fxCode = :orgCode ");
					dataMap.put("orgCode", paramap.get("orgCode"));
				}
				if ("personId".equals(key)) {
					hql.append(" and fxpersonId = :fxpersonId ");
					dataMap.put("fxpersonId", paramap.get("personId"));
				}
			}
			hql.append(" order by odate desc");
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<Orders> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	public Map<String,Object> getOrdersNumForReport(Date sdate,Date edate, String orgCode){
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		String hql = "from Orders where odate>=? and odate<=? and status!=0 and status!=9 and fxCode like '"+ orgCode +"%' order by odate";
		List<Orders> os = this.getHibernateTemplate().find(hql, sdate,edate);
		if(os!=null){
			Calendar c = Calendar.getInstance();
			for(Orders o : os){
				c.setTime(o.getOdate());
				String ym = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1);
				Object count = result.get(ym);
				if(count==null){
					result.put(ym, 1);
				}else{
					int value = Integer.valueOf(count.toString()).intValue();
					result.put(ym, ++value);
				}
			}
		}
		return result;
	}
	public Map<String,Object> getOrdersMoneyForReport(Date sdate,Date edate, String orgCode){
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		String hql = "from Orders where odate>=? and odate<=? and status!=0 and status!=9 and fxCode like '"+ orgCode +"%' order by odate";
		List<Orders> os = this.getHibernateTemplate().find(hql, sdate,edate);
		if(os!=null){
			Calendar c = Calendar.getInstance();
			for(Orders o : os){
				c.setTime(o.getOdate());
				String ym = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1);
				Object money = result.get(ym);
				if(money==null){
					result.put(ym, o.getFee());
				}else{
					float value = ((Float)money).floatValue();
					result.put(ym, value+o.getFee().floatValue());
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据订单号查询订单 
	 * @param out_trade_no
	 * @return
	 */
	public Orders getOrderByTradeNo(String out_trade_no) {
		return this.getUniqueEntityByOneProperty("orderNo", out_trade_no);
	}

	public Map<String, Object> queryAreaRecord(int start, int limit, Map<String, String> param) {
		Long adminId = Long.valueOf(param.get("dailiAdminId"));
		Map<String, Object> result = new HashMap<String, Object>();
		List<Orders> list = new ArrayList<Orders>();
		String hql = "from Orders where (user.city='"+ param.get("area") +"' or user.province='"+ param.get("province") +"')";
		String countHql = "select count(id) from Orders where (user.city='"+ param.get("area") +"' or user.province='"+ param.get("province") +"')";
		String cond = "";
		//根据下单时间倒序排序
		hql += cond + " order by  odate desc";
		countHql += cond;
		List find = this.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = this.getPagingEntitiesByHql(hql, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}
}