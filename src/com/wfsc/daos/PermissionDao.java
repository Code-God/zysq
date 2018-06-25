/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Apex Ocean
 * Filename : UserDao.java
 * Create   : wenjun.cui@tekview.com, Jan 30, 2008/10:24:57 AM
 */
package com.wfsc.daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.user.Permission;

/**
 * @since cupid
 * 
 * @author jacky.wang
 */
@Repository("permissionDao")
public class PermissionDao extends EnhancedHibernateDaoSupport<Permission> {

	public long addPermission(Permission permission) {
		getHibernateTemplate().saveOrUpdate(permission);
		return permission.getId();
	}

	@Override
	protected String getEntityName() {
		return Permission.class.getName();
	}

	public List<Permission> getAllSubPermission() {
		List<Permission> find = getHibernateTemplate().find("from Permission where parentPermission is not null ");
		return find;
	}
}