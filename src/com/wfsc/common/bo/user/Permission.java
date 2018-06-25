/*
 * Copyright 2007 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : Permission.java
 * Create   : jimsu@tekview.com, Jan 28, 2007/2:52:24 PM
 */
package com.wfsc.common.bo.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Permission条目
 * 
 * @author jimsu
 * @since 2.3
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_permission" lazy="false"
 */
public class Permission implements java.io.Serializable {

	private static final long serialVersionUID = -6425252208542316084L;

	/** 持久化ID */
	private Long id;

	/** 权限名 */
	private String permissionName;

	/** 权限描述 */
	private String permissionDescription;

	/** 权限的ACTIONID */
	private String actionId;

	/** 是否出厂数据 */
	private boolean canDelete;
	
	/** 是否选中的标记， 用来在页面上显示 */
	private String ck = "0";
	
	/** 父权限项 */
	private Permission parentPermission;

	/** 子权限项 */
	private Set<Permission> subPermissions = new HashSet<Permission>(0);
	
	/**
	 * 排了序的subPermissions，按id从小到大排序
	 */
	private List<Permission> sortSubPermissions = new ArrayList<Permission>();

	public Permission() {
	}

	/**
	 * hibernate中的主键
	 * 
	 * @return Returns the id.
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return permissionName不允许为null
	 * 
	 * @hibernate.property type="string"
	 * @hibernate.property unique="true"
	 */
	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}


	/**
	 * 角色的描述信息
	 * 
	 * @return 描述信息
	 * 
	 * @hibernate.property type="string"
	 */
	public String getPermissionDescription() {
		return permissionDescription;
	}

	public void setPermissionDescription(String permissionDescription) {
		this.permissionDescription = permissionDescription;
	}

	/**
	 * @return
	 * 
	 * @hibernate.property type="string" not-null="true"
	 * @hibernate.property unique="true"
	 */
	public String getActionId() {
		return actionId;
	}


	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	/**
	 * actionId是唯一的，actionId相同的权限就认为是相等的
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Permission permission = (Permission) obj;
		if (this.actionId.equals(permission.getActionId()))
			return true;
		else
			return false;
	}

	/**
	 * 是否允许删除
	 * 
	 * @hibernate.property type="boolean"
	 * @return
	 */
	public boolean isCanDelete() {
		return canDelete;
	}

	
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	
	public String getCk() {
		return ck;
	}

	
	public void setCk(String ck) {
		this.ck = ck;
	}
	
	/**
	 * 
	 * @return
	 * @hibernate.many-to-one class="com.wfsc.common.bo.user.Permission" column="parent_permission_id"
	 *                        not-null="false"
	 */
	public Permission getParentPermission() {
		return parentPermission;
	}

	public void setParentPermission(Permission parentPermission) {
		this.parentPermission = parentPermission;
	}

	/**
	 * 
	 * @return
	 * 
	 * @hibernate.collection-key column="parent_permission_id"
	 * @hibernate.collection-one-to-many class="com.wfsc.common.bo.user.Permission"
	 * @hibernate.set cascade="delete" lazy="true"
	 */
	public Set<Permission> getSubPermissions() {
		return subPermissions;
	}

	public void setSubPermissions(Set<Permission> subPermissions) {
		this.subPermissions = subPermissions;
	}
	
	public List<Permission> getSortSubPermissions() {
		List<Permission> perList = new ArrayList<Permission>();
		Object[] subPer = this.subPermissions.toArray();
		// 冒泡排序
		for (int i = 0; i < subPer.length; i++) {
			for (int j = i + 1; j < subPer.length; j++) {
				if (((Permission) subPer[i]).getId() > ((Permission) subPer[j]).getId()) {
					Permission per = new Permission();
					per = (Permission) subPer[j];
					subPer[j] = subPer[i];
					subPer[i] = per;
				}
			}
			perList.add((Permission) subPer[i]);
		}
		return perList;
	}

	public void setSortSubPermissions(List<Permission> sortSubPermissions) {
		this.sortSubPermissions = sortSubPermissions;
	}

}
