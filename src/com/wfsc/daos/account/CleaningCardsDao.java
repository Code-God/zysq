package com.wfsc.daos.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.car.CleaningCards;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 * 
 */
@SuppressWarnings("unchecked")
@Repository("cleaningCardsDao")
public class CleaningCardsDao extends EnhancedHibernateDaoSupport<CleaningCards> {

	@Override
	protected String getEntityName() {
		return CleaningCards.class.getName();
	}
	
	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<CleaningCards> list = new ArrayList<CleaningCards>();
		String hql = "from CleaningCards where 1=1 ";
		String countHql = "select count(id) from CleaningCards where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			//根据区域查询
			if (paramMap.get("serviceId") != null && !StringUtils.isEmpty(paramMap.get("serviceId"))) {
				cond += " and serviceId = " + Long.valueOf(paramMap.get("serviceId"));
			}
			//卡编号
			if (paramMap.get("cardCode") != null && !StringUtils.isEmpty(paramMap.get("cardCode"))) {
				cond += " and cardCode like '%" +  paramMap.get("cardCode") + "%'";
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