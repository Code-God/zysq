/******************************************************************************** 
 * Create Author   : Administrator
 * Create Date     : Sep 25, 2009
 * File Name       : Role.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.wfsc.common.bo.user;

import java.util.HashSet;
import java.util.Set;


/**
 * @author allen.wang
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_role" lazy="false"
 */
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = -6117441076416793198L;

	private long id;

	/** 角色名称 */
	private String roleName;

	/** 角色描述 */
	private String roleDescription;

	/** 是否可以删除,系统默认角色不允许被删除 */
	private boolean deletable;
	
	private String ck = "0";

	/** user与role之前双向多对多，因为需要知道角色下拥有的用户，级联设为NONE */
	private Set<Admin> users = new HashSet<Admin>(0);

	/** role与permission之间单向多对多 */
	private Set<Permission> perms = new HashSet<Permission>(0);
	public Role() {
	}

	public Role(String roleName, String description) {
		this.roleName = roleName;
		this.roleDescription = description;
	}

	public Role(long id, String name, String roleDescription, boolean del) {
		this.id = id;
		this.roleName = name;
		this.roleDescription = roleDescription;
		this.deletable = del;
	}

	/**
	 * 用户名是唯一的，只要用户名相等的两个角色就认为是相等的
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Role role = (Role) obj;
		if (this.roleName.equals(role.getRoleName()))
			return true;
		else
			return false;
	}

	/** hash code */
	public int hashCode() {
		int hash = 1;
		if (roleName != null)
			hash = roleName.length();
		hash = (int) (id ^ (id >>> 32)) + 3 * hash;
		return hash;
	}

	/**
	 * hibernate中的主键
	 * 
	 * @return Returns the id.
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 角色名
	 * 
	 * @return roleName不允许为null
	 * 
	 * @hibernate.property type="string"
	 * @hibernate.property unique="true"
	 */
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 是否允许删除
	 * 
	 * @hibernate.property type="boolean"
	 * @return
	 */
	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	/**
	 * 角色的描述信息
	 * 
	 * @return 描述信息
	 * 
	 * @hibernate.property type="string"
	 */
	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	/**
	 * @hibernate.collection-many-to-many class="com.wfsc.common.bo.user.Admin" column="adminId"
	 * @hibernate.collection-key column="roleId"
	 * @hibernate.set name="users" table="wf_admin_role" inverse="true" cascade="none" lazy="false"
	 * @return
	 */
	public Set<Admin> getUsers() {
		return users;
	}

	public void setUsers(Set<Admin> users) {
		this.users = users;
	}

	/**
	 * @hibernate.collection-many-to-many class="com.wfsc.common.bo.user.Permission" column="permissionId"
	 * @hibernate.collection-key column="roleId"
	 * @hibernate.set table="wf_role_permission" inverse="false" cascade="save-update" lazy="false"
	 * @return
	 * 这里的lazy必须为false，否则权限标签里无法加载到权限信息
	 */
	public Set<Permission> getPerms() {
		return perms;
	}

	public void setPerms(Set<Permission> perms) {
		this.perms = perms;
	}

	public String getCk() {
		return ck;
	}

	public void setCk(String ck) {
		this.ck = ck;
	}
	
}
