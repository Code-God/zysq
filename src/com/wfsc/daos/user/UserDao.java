package com.wfsc.daos.user;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugDiseaseItem;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jsoup.helper.StringUtil;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import antlr.StringUtils;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.user.User;


@SuppressWarnings("unchecked")
@Repository("userDao")
public class UserDao extends EnhancedHibernateDaoSupport<User> {

	@Override
	protected String getEntityName() {
		return User.class.getName();
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public long saveUser(User user){
		getHibernateTemplate().saveOrUpdate(user);
		return user.getId();
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user){
		getHibernateTemplate().saveOrUpdate(user);
		return true;
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public boolean deleteUser(User user){
		getHibernateTemplate().delete(user);
		return true;
	}
	
	/**
	 * 判断用户是否存在，即判断该邮箱是否在系统中注册过
	 * @return
	 */
	public boolean isExistByEmail(String email){
		Iterator<Long> iterator = getHibernateTemplate().find(" select count(*) from " + getEntityName() + " where email = ?", email).iterator();
		while (iterator.hasNext()) {
			return iterator.next().intValue() > 0;
		}
		return false;
	}
	
	/**
	 * 判断该手机号码是否被绑定，系统中不允许出现两个相同的手机号码
	 * @param telephone
	 * @return
	 */
	public boolean isExistByTelphone(String telphone){
		Iterator<Long> iterator = getHibernateTemplate().find(" select count(*) from " + getEntityName() + " where telephone = ?", telphone).iterator();
		while (iterator.hasNext()) {
			return iterator.next().intValue() > 0;
		}
		return false;
	}
	
	/**
	 * 检查昵称是否已经被占用
	 * @param nickName
	 * @return
	 */
	public boolean isExistByNickName(String nickName){
		Iterator<Long> iterator = getHibernateTemplate().find(" select count(*) from " + getEntityName() + " where nickName = ?", nickName).iterator();
		while (iterator.hasNext()) {
			return iterator.next().intValue() > 0;
		}
		return false;
	}
	
	/**
	 * 获取所有的用户
	 * @return
	 */
	public List<User> getAllUsers(){
		return getAllEntities();
	}
	
	/**
	 * 根据邮箱地址查询用户信息
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email){
		User user = null;
		List<User> result = getHibernateTemplate().find("from User where email = ?", email);
		if (CollectionUtils.isNotEmpty(result)) {
			user = result.get(0);
		}
		return user;
	}
	
	
	/**
	 * 根据手机号码查询用户信息
	 * @param telphone
	 * @return
	 */
	public User getUserByTelphone(String telphone){
		User user = null;
		List<User> result = getHibernateTemplate().find("from User where telephone = ?", telphone);
		if (CollectionUtils.isNotEmpty(result)) {
			user = result.get(0);
		}
		return user;
	}
	/**
	 * 根据昵称查询用户信息
	 * @param telphone
	 * @return
	 */
	public User getUserByNickName(String nickName){
		User user = null;
		List<User> result = getHibernateTemplate().find("from User where nickName = ?", nickName);
		if (CollectionUtils.isNotEmpty(result)) {
			user = result.get(0);
		}
		return user;
	}
	
	/**
	 * 在线用户列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getOnlineUsers() {
		return getHibernateTemplate().find("from User where online = ?", true);
	}
	
	public void setUserOffLine(String nickName) {
		User user = this.getUserByNickName(nickName);
		if (user != null) {
			user.setOnline(false);
			getHibernateTemplate().update(user);
		}
	}
	
	public Page<User> findForPage(Page<User> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("select distinct obj from User as obj where 1=1 ");
		try {
			for (String key : paramap.keySet()) {
				Object obj = (Object)paramap.get(key);
				String val = null;
				if (obj == null) {
					continue;
				}
				val = obj.toString();
				if (StringUtil.isBlank(val)) {
					continue;
				}
				
				if ("realName".equals(key)) {
					hql.append(" and obj.realName like '%" + val + "%'");
					continue;
				}
				if ("sex".equals(key)) {
					hql.append(" and obj.sex = '" + val + "'");
					continue;
				}
				if ("ageStart".equals(key)) {
					hql.append(" and obj.age >= " + val);
					continue;
				}
				if ("ageEnd".equals(key)) {
					hql.append(" and obj.age <= " + val);
					continue;
				}
				if ("mobilePhone".equals(key)) {
					hql.append(" and obj.mobilePhone = '" + val + "'");
					continue;
				}
				if ("province".equals(key)) {
					hql.append(" and obj.province = '" + val + "'");
					continue;
				}
				if ("city".equals(key)) {
					hql.append(" and obj.city = '" + val + "'");
					continue;
				}
				if ("address".equals(key)) {
					hql.append(" and obj.address like '%" + val + "%'");
					continue;
				}
				if ("status".equals(key)) {
					hql.append(" and obj.status = " + val);
					continue;
				}
				if ("auditStatus".equals(key)) {
					hql.append(" and obj.auditStatus = " + val);
					continue;
				}
				if ("userType".equals(key)) {
					hql.append(" and obj.userType = " + val);
					continue;
				}
				
			}
			hql.append("  and obj.userType!=3 order by regDate desc ");
			int totalCount = this.countByHql(hql.toString());
			
			List<User> list = this.findList4Page(hql.toString(), page
					.getFirst() - 1, page.getPageSize());
			page.setData(list);
			page.setTotalCount(totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 根据email删除用户
	 * @param email 需要删除的用户的email
	 * @return 删除的用户个数
	 */
	public int deleteUserByEmail(final String email){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "delete from User where email = ?";
				Query query = session.createQuery(hql);
				query.setString(0, email);
				return query.executeUpdate();
			}
			
		});
	}
	
	/**
	 * 根据注册时间统计用户注册数量
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int countUserByCreateTime(final Date startTime, final Date endTime){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select count(*) from User where regDate > ? and regDate <= ?";
				Query query = session.createQuery(hql);
				query.setDate(0, startTime);
				query.setDate(1, endTime);
				return ((Number)query.iterate().next()).intValue();
			}
			
		});
	}

	/**
	 * 通过openid查询用户信息
	 * @return
	 */
	public User getUserByOpenid(String openid) {
		final String hql="from User where openId='"+openid+"'";
		List<User> list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createQuery(hql);
				return q.list();
			}
		});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 通过用户id查询推荐的患者信息
	 * @param userid
	 * @return
	 */
	public List getRecommendByUserid(String userid){
		final String sql="select us.realname,us.age,"
				+ " case us.sex when 1 then '男' when 2 then '女' else '未知' end as sex ,"
				+ " us.audit_status "
				+ " from drug_u_d_relation as rel inner join wf_user us on rel.user_id=us.id "
				+"  where rel.referrer_id="+userid;
		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql);
				return q.list();
			}
		});
		return list;
	}
	
	/**
	 * 通过用户id查询自己主动报名的信息（最多一条数据）
	 * @param userid
	 * @return
	 */
	public List getActiveEnteredUserid(String userid){
		final String sql="select us.realname,us.age,"
				+ " case us.sex when 1 then '男' when 2 then '女' else '未知' end as sex ,"
				+ " us.audit_status "
				+ " from drug_u_d_relation as rel inner join wf_user us on rel.user_id=us.id "
				+" where rel.user_id="+userid;
		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql);
				return q.list();
			}
		});	
		return list;
	}

	
	/**
	 * 根据手机号查找用户
	 * 
	 * @return
	 */
	public User getUserByMobile(String mobile) {
		List<User> list = getHibernateTemplate().find("from User where mobilePhone = ?", mobile);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}


	/**
	 * 更新用户的审核状态
	 * 
	 * @param userIds
	 * @param auditStatus
	 */
	public void updateAuditStatus(final List<Long> userIds, final int auditStatus) {
		getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "update User u set u.auditStatus = :auditStatus where u.id in (:userIds)";
				Query query = session.createQuery(hql);
				query.setParameter("auditStatus", auditStatus);
				query.setParameterList("userIds", userIds);
				return query.executeUpdate();
			}
			
		});
	}
	
	/**
	 * 查询所有的邀请人
	 * @return
	 */
	public Page getAllInviterList(final Page page, Map<String,Object> paramap){
		final StringBuffer sql=new StringBuffer();
		sql.append("select  u2.id,u2.realname,u2.nickname,u2.mobile_phone,u2.regdate,count(u2.id),u2.sex,u2.age,u2.city,u2.address,u2.userType,u2.regDate from wf_user as u1, wf_user as u2  where  u1.inviter_id=u2.id  ");
		for(String key : paramap.keySet()){
			if("realname".equals(key)){
				sql.append(" and u2.realname like '%"+paramap.get(key)+"%'");
				continue;
			}
			if("nickname".equals(key)){
				sql.append(" and u2.nickname like '%"+paramap.get(key)+"%'");
				continue;
			}
		}
		
		sql.append(" group by u2.id  ");		
		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql.toString());
				q.setFirstResult(page.getFirst()-1);
				q.setMaxResults(page.getPageSize());
				return q.list();
			}
		});	
		//查询所有list，获取条数
		List list2= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql.toString());
				return q.list();
			}
		});	
		
		if(list2!=null&&list2.size()>0){
			page.setTotalCount(list2.size());
		}else{
			page.setTotalCount(0);
		}		
		page.setData(list);
		
		return page;
	}
	
	/**
	 * 通过用户id查询该邀请人邀请的所有人列表
	 * @return
	 */
	public Page getBeInvitedListByUserid(final Page page,int userid){
		final StringBuffer hql=new StringBuffer();
		hql.append(" from User where inviterId="+userid+" order by regDate desc");
	
		List<User> list=this.findList4Page(hql.toString(), page.getFirst()-1, page.getPageSize());
		int count=this.countByHql(hql.toString());
		page.setTotalCount(count);
		page.setData(list);		
		return page;
	}
}
