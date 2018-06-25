package com.wfsc.daos.comment;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.wfsc.common.bo.comment.Comments;

/**
 * 
 * 评论
 * 
 * @author Xiaojiapeng
 * 
 */
@SuppressWarnings("unchecked")
@Repository("commentsDao")
public class CommentsDao extends EnhancedHibernateDaoSupport<Comments> {

	/**
	 * 根据商品编码获取评论
	 * 
	 * @param prdCode
	 * @return
	 */
	public List<Comments> queryByPrdCode(String prdCode) {
		// String sql = "select cm.*,us.nickname from wf_comments cm LEFT JOIN wf_user us on cm.creatorId = us.id where cm.prdCode = ?";
		String hql = "from Comments where prdCode = ?";
		return super.getHibernateTemplate().find(hql, prdCode);
	}

	@Override
	protected String getEntityName() {
		return Comments.class.getName();
	}
	
	public Page<Comments> findForPage(Page<Comments> page, Map<String,Object> paramap){
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
			List<Comments> list = this.findList4PageWithParama(hql.toString(), page
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
	public Page<Comments> findPage(Page<Comments> page, Map<String,Object> paramap){
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
		List<Comments> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}
	
	public List<Comments> getCommentsByProductId(final String prdCode, final int start, final int limit){
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Comments>>(){

			@Override
			public List<Comments> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Comments where prdCode = ?";
				Query query = session.createQuery(hql);
				query.setString(0, prdCode);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
			
		});
	}
	
	public List<Comments> getCommentsByUserId(final long userId, final int start, final int limit){
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Comments>>(){

			@Override
			public List<Comments> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Comments where creatorId = ?";
				Query query = session.createQuery(hql);
				query.setLong(0, userId);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
			
		});
	}
	
	public Map<String,Integer> getCommentsForReport(final Integer stars){
		return getHibernateTemplate().execute(new HibernateCallback<Map<String, Integer>>(){
			Map<String, Integer> result = new LinkedHashMap<String, Integer>();
			@Override
			public Map<String, Integer> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select prdTopCode, count(id) from Comments  group by prdTopCode order by prdTopCode";
				if(stars!=null){
					hql = "select prdTopCode, count(id) from Comments  where stars="+stars+" group by prdTopCode order by prdTopCode";
				}
				Query query = session.createQuery(hql);
				List<Object[]> list = query.list();
				if(CollectionUtils.isNotEmpty(list)){
					for(Object[] obj : list){
						result.put(obj[0].toString(), Integer.parseInt(obj[1] + ""));
					}
				}
				return result;
			}
			
		});
	}
}