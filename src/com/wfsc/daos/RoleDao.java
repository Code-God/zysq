/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Apex Ocean
 * Filename : UserDao.java
 * Create   : wenjun.cui@tekview.com, Jan 30, 2008/10:24:57 AM
 */
package com.wfsc.daos;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.user.Role;

/**
 * @since cupid
 * 
 * @author jacky.wang
 */
@Repository("roleDao")
public class RoleDao extends EnhancedHibernateDaoSupport<Role> {

	public long addRole(Role role) {
		getHibernateTemplate().saveOrUpdate(role);
		return role.getId();
	}

	@Override
	protected String getEntityName() {
		return Role.class.getName();
	}
	
	/**
	 * 将角色从表中删除
	 */
	public boolean deleteRole(Role roleObj) {
		getHibernateTemplate().delete(roleObj);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Role>getRolesByIds(final List<Long>roleIds){
		return (List<Role>)getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Role where id in (:ids)";
				Query query = session.createQuery(hql);
				query.setParameterList("ids",roleIds);
				return query.list();
			}
			
		});
	}
	
	/**
	 * 根据roleName返回表中对应的Role对象
	 */
	@SuppressWarnings("unchecked")
	public Role getRoleWithPermissionByName(String roleName) {
		Role roleObj = null;
		List<Role> result = getHibernateTemplate().find("from Role where roleName = ?", roleName);
		if (CollectionUtils.isNotEmpty(result)) {
			roleObj = result.get(0);
			Hibernate.initialize(roleObj.getPerms());
			Hibernate.initialize(roleObj.getUsers());
		}
		return roleObj;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role>getRolesByNames(final List<Long>roleNames){
		return (List<Role>)getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Role where roleName in (:roleNames)";
				Query query = session.createQuery(hql);
				query.setParameterList("roleName",roleNames);
				return query.list();
			}
			
		});
	}
	
}