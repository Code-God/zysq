package com.wfsc.daos.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.ServerBeanFactory;
import com.wfsc.common.bo.account.Coupon;
import com.wfsc.daos.product.ProductCatDao;

/**
 * 
 */
@SuppressWarnings("unchecked")
@Repository("couponDao")
public class CouponDao extends EnhancedHibernateDaoSupport<Coupon> {


	@Override
	protected String getEntityName() {
		return Coupon.class.getName();
	}
	
	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Coupon> list = new ArrayList<Coupon>();
		String hql = "from Coupon where 1=1 ";
		String countHql = "select count(id) from Coupon where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			//根据区域查询
			if (paramMap.get("serviceId") != null && !StringUtils.isEmpty(paramMap.get("serviceId"))) {
				cond += " and serviceId = " + Long.valueOf(paramMap.get("serviceId"));
			}
			if (paramMap.get("couponCode") != null && !StringUtils.isEmpty(paramMap.get("couponCode"))) {
				cond += " and couponCode like '%" +  paramMap.get("couponCode") + "%'";
			}
			if (paramMap.get("userId") != null && !StringUtils.isEmpty(paramMap.get("userId"))) {
				cond += " and userId = " +  paramMap.get("userId");
			}
			if (paramMap.get("status") != null && !StringUtils.isEmpty(paramMap.get("status"))) {
				cond += " and status = " +  paramMap.get("status");
			}
			
			//分类ID
			if(paramMap.get("pid") != null && !"0".equals(paramMap.get("pid"))){
				//根据分类ID查找分类名称
				ProductCatDao dao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
				String catCode = dao.getEntityById(Long.valueOf(paramMap.get("pid"))).getCode();
				//根据分类名，查找该分类下的商品
				cond += " and  prdCode like '" +  catCode + "%'";
			}
			
		}
		hql += cond + " order by  generateDate desc";
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