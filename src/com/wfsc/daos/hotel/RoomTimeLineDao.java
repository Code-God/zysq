package com.wfsc.daos.hotel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.bo.hotel.RoomTimeline;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.ServerBeanFactory;
import com.base.util.Page;
import com.wfsc.common.bo.keyword.KeyWord;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.Products;

/**
 * 房间时间表
 * 
 * @author jacky
 * 
 */
@SuppressWarnings("unchecked")
@Repository("roomTimelineDao")
public class RoomTimeLineDao extends EnhancedHibernateDaoSupport<RoomTimeline> {
	 

	@Override
	protected String getEntityName() {
		return RoomTimeline.class.getName();
	}
	/**
	 * 分页查询(前台专用)
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<RoomTimeline> findPage(Page<RoomTimeline> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from RoomTimeline as p where 1=1 ");
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
		List<RoomTimeline> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}
	 
}
