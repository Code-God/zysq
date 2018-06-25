package com.wfsc.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugQualification;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.user.WeiChat;

@SuppressWarnings("unchecked")
@Repository("weiChatDao")
public class WeiChatDao extends EnhancedHibernateDaoSupport<WeiChat> {

	@Override
	protected String getEntityName() {
		return WeiChat.class.getName();
	}
	
	/**
	 * 保存会话记录
	 * @param user
	 * @return
	 */
	public List<WeiChat> saveWeiChat(WeiChat weiChat){
		getHibernateTemplate().saveOrUpdate(weiChat);
		return getAllWeiChats();
	}
	
	/**
	 * 更新会话
	 * @param user
	 * @return
	 */
	public boolean updateWeiChat(WeiChat weiChat){
		getHibernateTemplate().saveOrUpdate(weiChat);
		return true;
	}
	
	/**
	 * 删除会话
	 * @param user
	 * @return
	 */
	public boolean deleteWeiChat(WeiChat weiChat){
		getHibernateTemplate().delete(weiChat);
		return true;
	}
	
	/**
	 * 获取所有的会话
	 * @return
	 */
	public List<WeiChat> getAllWeiChats(){
		return getAllEntities();
	}
	
	/**
	 * 查看消息列表
	 * @param page
	 * @param paramap
	 * @return
	 */
	public  Page<Object> findPageForNews(final Page<Object> page, Map<String,Object> paramap){
		final StringBuffer sql = new StringBuffer("select q.id, q.toUserName,q.fromUserName,u.nickname,q.msgType,q.createTime,q.picUrl,q.mediaId,q.msgId, q.review_state"
				+ " from weichat_log as q"
				+ " inner join wf_user u on q.fromUserName=u.openid"
				+ " where 1=1");
		
		
		
		for(String key : paramap.keySet()){
			if("hospital".equals(key)){
				sql.append(" and u.nickname like '%"+paramap.get(key)+"%'");
				continue;
			}
			if("reviewState".equals(key)){
				sql.append(" and q.review_state like '%"+paramap.get(key)+"%'");
				continue;
			}
			if("startTransDate".equals(key)){
				sql.append(" and q.createTime > '"+paramap.get(key)+"'");
				continue;
			}
			if("endTransDate".equals(key)){
				sql.append(" and q.createTime < '"+paramap.get(key)+"'");
				continue;
			}
		}
		sql.append(" order by  q.id  desc");
		
//		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws SQLException, HibernateException {
//				Query q = session.createSQLQuery(sql.toString());
//				q.setFirstResult(page.getFirst()-1);
//				q.setMaxResults(page.getPageSize());
//				return q.list();
//			}
//		});
		
		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql.toString());
				page.setTotalCount(q.list().size());
				q.setFirstResult(page.getFirst()-1);
				q.setMaxResults(page.getPageSize());
				
				return q.list();
			}
		});
		
		
		if(list!=null&&list.size()>0){
//			page.setTotalCount(list.size());
		}else{
			page.setTotalCount(0);
			list= new ArrayList();
		}
		page.setData(list);
		return page;
	}
	
	/**
	 * 通过认证id查询认证信息
	 * @param id
	 * @return
	 */
	public Object findNewsById(String id){
		final StringBuffer sql = new StringBuffer("select q.id, q.toUserName,q.fromUserName,u.nickname,q.msgType,q.createTime,q.picUrl,q.mediaId,q.msgId, q.review_state"
				+ " from weichat_log as q"
				+ " inner join wf_user u on q.fromUserName=u.openid"
				+ " where q.id="+id);
		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql.toString());
				return q.list();
			}
		});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
		
	}
	

}
