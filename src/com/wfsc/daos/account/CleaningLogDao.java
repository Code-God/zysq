package com.wfsc.daos.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.car.CleaningLogs;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 * 
 */
@SuppressWarnings("unchecked")
@Repository("cleaningLogDao")
public class CleaningLogDao extends EnhancedHibernateDaoSupport<CleaningLogs> {

	@Override
	protected String getEntityName() {
		return CleaningLogs.class.getName();
	}
	
	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<CleaningLogs> list = new ArrayList<CleaningLogs>();
		String hql = "from CleaningLogs where 1=1 ";
		String countHql = "select count(id) from CleaningLogs where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			//根据时间查询
			if (paramMap.get("startDate") != null && !StringUtils.isEmpty(paramMap.get("startDate"))) {
				cond += " and theDate > '" + Long.valueOf(paramMap.get("startDate")) + "'";
			}
			if (paramMap.get("endDate") != null && !StringUtils.isEmpty(paramMap.get("endDate"))) {
				cond += " and theDate < '" + Long.valueOf(paramMap.get("endDate")) + "'";
			}
			//卡编号
			if (paramMap.get("cardCode") != null && !StringUtils.isEmpty(paramMap.get("cardCode"))) {
				cond += " and cardCode like '%" +  paramMap.get("cardCode") + "%'";
			}
			
			if(paramMap.get("openId") != null && !StringUtils.isEmpty(paramMap.get("openId"))){
				cond += " and openId ='" +  paramMap.get("openId") + "'";
			}
		}
		hql += cond + " order by  theDate desc";
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