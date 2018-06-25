package com.wfsc.daos.account;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.account.Favourite;

/**
 * 
 */
@SuppressWarnings("unchecked")
@Repository("favouriteDao")
public class FavouriteDao extends EnhancedHibernateDaoSupport<Favourite> {


	@Override
	protected String getEntityName() {
		return Favourite.class.getName();
	}
	
	public Page<Favourite> findForPage(Page<Favourite> page, Map<String,Object> paramap){
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
			List<Favourite> list = this.findList4PageWithParama(hql.toString(), page
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
	public Page<Favourite> findPage(Page<Favourite> page, Map<String,Object> paramap){
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
		List<Favourite> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}
	
	/**
	 * 分页查询我的收藏
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Favourite> getFavouriteByUserId(final long userId, final int start, final int limit){
		return getHibernateTemplate().execute(new HibernateCallback<List<Favourite>>(){

			@Override
			public List<Favourite> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Favourite where userid = ?";
				Query query = session.createQuery(hql);
				query.setLong(0, userId);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
			
		});
	}
	
	/**
	 * 查询我的收藏
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Favourite> getFavouriteByUserId(final long userId){
		return getHibernateTemplate().execute(new HibernateCallback<List<Favourite>>(){

			@Override
			public List<Favourite> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Favourite where userid = ?";
				Query query = session.createQuery(hql);
				query.setLong(0, userId);
				return query.list();
			}
			
		});
	}

	/**
	 * 删除收藏
	 * @param userId 用户Id
	 * @param proId 产品ID
	 */
	public void deleteByUserIdAndProId(Long userId, Long proId){
//		getHibernateTemplate().find("delete from Favourite where userid = ? and Products.id = ?", userId, proId);
		System.out.println("删除收藏：userId=" + userId +" proId=" + proId);
	}
	
	public Favourite getFavouriteByUserIdAndPrdId(final long userId, final long prdId){
		List<Favourite> favourites = getHibernateTemplate().find("from Favourite where userid = ? and products.id = ?", userId, prdId);
		if(CollectionUtils.isNotEmpty(favourites)){
			return favourites.get(0);
		}
		return null;
	}
}