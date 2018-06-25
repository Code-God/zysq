package com.wfsc.daos.account;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.account.ReturnProducts;

/**
 * 
 */
@SuppressWarnings("unchecked")
@Repository("returnProductsDao")
public class ReturnProductsDao extends EnhancedHibernateDaoSupport<ReturnProducts> {


	@Override
	protected String getEntityName() {
		return ReturnProducts.class.getName();
	}
	
	public Page<ReturnProducts> findForPage(Page<ReturnProducts> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct c from Comments as c where 1=1 ");
		try {
			Date sdate = null;
			Date edate = null;
			for (String key : paramap.keySet()) {
				if ("prdCode".equals(key)) {
					hql.append(" and c.prdCode = :prdCode");
					dataMap.put("prdCode", paramap.get("prdCode"));
					continue;
				}
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and c.pdate >= :sdate " );
					dataMap.put("sdate", sdate);
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and c.pdate <= :edate ");
					dataMap.put("edate", edate);
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<ReturnProducts> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 分页查询(前台专用)
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<ReturnProducts> findPage(Page<ReturnProducts> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from Comments where 1=1 ");
		for(String key : paramap.keySet()){
			if("prdCode".equals(key)){
				hql.append(" and prdCode = '" + paramap.get(key) + "'");
				continue;
			}
			if("stars".equals(key)){
				String val = paramap.get(key).toString();
				if (!val.equals("0")) {
					hql.append(" and stars = " + val);
					continue;
				}
			}
		}
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<ReturnProducts> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}
}