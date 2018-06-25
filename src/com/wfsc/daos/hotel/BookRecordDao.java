package com.wfsc.daos.hotel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.hotel.BookRecord;
import model.bo.hotel.RoomTimeline;
import model.bo.wxmall.Pj;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;

/**
 * 房间预定表
 * 
 * @author jacky
 * 
 */
@SuppressWarnings("unchecked")
@Repository("bookRecordDao")
public class BookRecordDao extends EnhancedHibernateDaoSupport<BookRecord> {
	 

	@Override
	protected String getEntityName() {
		return BookRecord.class.getName();
	}
	
	
	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BookRecord> list = new ArrayList<BookRecord>();
		String hql = "from BookRecord where 1=1 ";
		String countHql = "select count(id) from BookRecord where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			//根据用户openId查询
			if (paramMap.get("openId") != null && !StringUtils.isEmpty(paramMap.get("openId"))) {
				cond += " and openId = " + paramMap.get("openId") + "";
			}
			
			//供管理员后台查看的参数
			if (paramMap.get("orgId") != null && !StringUtils.isEmpty(paramMap.get("orgId"))) {
				cond += " and orgId = " + paramMap.get("orgId") + "";
			}
			
		}
		hql += cond + " order by  submitDate desc";
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
	/**
	 * 分页查询(前台专用)
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<BookRecord> findPage(Page<BookRecord> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from BookRecord as p where 1=1 ");
		// 条件查询
		for(String key : paramap.keySet()){
			if ("sort".equals(key)) {
				continue;
			}
			if("prdCatCode".equals(key)){
				hql.append(" and p.prdCatCode like '" + paramap.get(key) + "%'");
				continue;
			}
			if("key".equals(key)){
				hql.append(" and p.name like '%"+ paramap.get(key) +"%'");
				continue;
			}
		}
//		// 排序
//		if (paramap.get("sort") != null) {
//			String sort = (String)paramap.get("sort");
//			if (sort.equals("0")) {
//				hql.append("order by p.id");
//			} else if (sort.equals("1")) {
//				hql.append("order by p.price");
//			} else if(sort.equals("2")){
//				System.out.println("按评论排序");
//			}
//			// 排序类型
//			String isDesc = (String)paramap.get("isDesc");
//			if (isDesc != null && Boolean.parseBoolean(isDesc)) {
//				hql.append(" DESC");
//			}
//		}
//		
//		if(paramap.get("sorter") != null){
//			String sorter = (String)paramap.get("sorter");
//			String order = (String)paramap.get("order");
//			hql.append("order by p." + sorter + " " + order);
//		}else{
//			hql.append("order by p.disPrice,p.price");
//		}
		
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<BookRecord> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}
	 
}
