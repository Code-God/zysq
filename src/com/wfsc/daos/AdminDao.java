/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Apex Ocean
 * Filename : UserDao.java
 * Create   : wenjun.cui@tekview.com, Jan 30, 2008/10:24:57 AM
 */
package com.wfsc.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.Role;
import com.wfsc.util.SysUtil;

/**
 * @since cupid
 * 
 * @author jacky.wang
 */
@Repository("adminDao")
public class AdminDao extends EnhancedHibernateDaoSupport<Admin> {

	public long addAdmin(Admin admin) {
		getHibernateTemplate().saveOrUpdate(admin);
		return admin.getId();
	}

	/**
	 * 根据userName返回表中对应的User对象
	 */
	@SuppressWarnings("unchecked")
	public Admin getUserWithPermissionByName(String userName) {
		Admin userObj = null;
		List<Admin> result = getHibernateTemplate().find("from Admin where username = ?", userName);
		if (CollectionUtils.isNotEmpty(result)) {
			userObj = result.get(0);
			Hibernate.initialize(userObj.getRoles());
			for (Role r : userObj.getRoles()) {
				Hibernate.initialize(r.getPerms());
			}
		}
		return userObj;
	}

	/**
	 * 更新表中的用户信息
	 */
	public boolean updateAdmin(Admin admin) {
	//	getHibernateTemplate().merge(admin);
//		getHibernateTemplate().saveOrUpdate(admin);
		getHibernateTemplate().update(admin);
		return true;
	}

	/**
	 * 删除表中的user
	 */
	public boolean deleteUser(Admin userObj) {
		getHibernateTemplate().delete(userObj);
		return true;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Admin> getAllUsers() {
		List<Admin> find = getHibernateTemplate().find("from Admin");
		/*try{
			for (Admin admin: find) {
				Hibernate.initialize(admin.getRoles());
				for (Role r : admin.getRoles()) {
					Hibernate.initialize(r.getPerms());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		return find;
	}


	@Override
	protected String getEntityName() {
		return Admin.class.getName();
	}


	@SuppressWarnings("unchecked")
	public List<Admin> getAdminByNames(List<String> username) {
		if (null == username)
			return null;
		StringBuffer sb = new StringBuffer();
		sb.append("from Admin user where user.username in (");
		sb.append(" '");
		sb.append(SysUtil.StringArrayToStr(SysUtil.StringListToStringArray(username), "','"));
		sb.append("' )");
		return getHibernateTemplate().find(sb.toString());
	}

	/**
	 * 
	 * 判断该用户是否存在
	 * 
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isExistByUserName(String userName) {
		Iterator<Long> iterator = getHibernateTemplate().find(
				" select count(*) from " + getEntityName() + " where username = ?", userName).iterator();
		while (iterator.hasNext()) {
			return iterator.next().intValue() > 0;
		}
		return false;
	}

	/**
	 * 
	 * 判断用户列表中是否有不存在的用户 全部存在返回false ,包含一个不存在返回true
	 * 
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isNotExistByUserNames(List<String> userName) {
		StringBuffer sb = new StringBuffer(" select count(*) from ");
		sb.append(getEntityName());
		sb.append(" users  where username  in ( '").append(SysUtil.listToString(userName, "','")).append("') ");
		Iterator<Long> iterator = getHibernateTemplate().find(sb.toString()).iterator();
		while (iterator.hasNext()) {
			return iterator.next().intValue() != userName.size();
		}
		return false;
	}
	
	/**
	 * 根据权限id找出拥有该权限的所有用户
	 * 
	 * @param actionId 权限id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Admin> getUserByPermissionId(String actionId) {
		actionId = StringUtils.trimToNull(actionId);
		if (null == actionId) {
			return null;
		} else {
			String hql = " select distinct user.id,user.username  from Admin user  join user.roles role join  role.perms perms where  perms.actionId = '"
					+ actionId + "' ";
			Iterator iterator = getHibernateTemplate().find(hql).iterator();
			List<Admin> userList = new ArrayList<Admin>();
			while (iterator.hasNext()) {
				Admin user = new Admin();
				Object[] usertem = (Object[]) iterator.next();
				if (usertem[0] != null) {
					user.setId((Long) usertem[0]);
					user.setUsername((String) usertem[1]);
					userList.add(user);
				}
			}
			return userList;
		}
	}

	/**
	 * 根据权限id找出拥有该权限的所有用户
	 * 
	 * @param actionId 权限id
	 * @return
	 */
	public Integer countUserByPermissionId(String actionId) {
		actionId = StringUtils.trimToNull(actionId);
		if (null == actionId) {
			return 0;
		} else {
			String hql = " select count(user) from Admin user  join user.roles role join  role.perms perms where  perms.actionId ='"
					+ actionId + "' ";
			Iterator<Long> iterator = getHibernateTemplate().find(hql).iterator();
			while (iterator.hasNext()) {
				Long sum = (Long) iterator.next();
				return sum.intValue();
			}
			return 0;
		}
	}
	
	/**
	 * 在线管理员列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Admin> getOnlineAdmins() {
		return getHibernateTemplate().find("from Admin where online = ?", true);
	}
	
	public void setUserOffLine(String userName) {
		Admin user = this.getUniqueEntityByOneProperty("username", userName);
		if (user != null) {
			user.setOnline(false);
			getHibernateTemplate().update(user);
		}
	}
	
	public Page<Admin> findPageForAdmin(Page<Admin> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("select distinct a from Admin as a left join a.roles as r where 1=1 ");
		for(String key : paramap.keySet()){
			if("username".equals(key)){
				hql.append(" and a.username like '%"+paramap.get(key)+"%'");
				continue;
			}
			if("status".equals(key)){
				hql.append(" and a.status ="+paramap.get(key));
				continue;
			}
			if("rolename".equals(key)){
				hql.append(" and r.roleName like '%"+paramap.get(key)+"%'");
				continue;
			}
			if("parentAdminId".equals(key)){
				hql.append(" and a.pid = " + paramap.get(key));
				continue;
			}
			if("pop".equals(key)){//排除admin
				hql.append(" and r.roleName <> '超级管理员'");
				continue;
			}
		}
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<Admin> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		for(Admin a : list){
			/*Hibernate.initialize(a.getRoles());
			for (Role r : a.getRoles()) {
				Hibernate.initialize(r.getPerms());
			}*/
			if(CollectionUtils.isNotEmpty(a.getRoles())){
				String roleString = "";
				for(Role r : a.getRoles()){
					roleString += r.getRoleName()+",";
				}
				a.setRoleString(roleString);
			}
		}
	//	page.setTotalCount(list==null?0:list.size());
		page.setData(list);
		return page;
	}
	
}