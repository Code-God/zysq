/******************************************************************************** 
 * Create Author   : Andy Cui
 * Create Date     : Oct 14, 2009
 * File Name       : User.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.wfsc.common.bo.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jacky 管理员
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_admin" lazy="false"
 */
public class Admin implements java.io.Serializable {

	private static final long serialVersionUID = -6883377244693425571L;

	private Long id;

	/** 登录时用的用户名，不是姓名,为了安全起见,登录名不出现在搜索结果里 */
	private String username;

	/** 状态: 1 - 可用 0 - 禁用 2-待审批 */
	private Integer status;
	
	/** 管理员类型： 1 - 系统管理员  9-经销商管理员 */
	private Integer type;

	/** 密码（需加密） */
	private String password;
	
	private int logCount;
	
	/** 管理员所属地区:省 */
	private String province;
	
	/** 管理员所属地区 */
	private String area;
	
	private String company;
	private String address;
	
	/** 管理员与角色的双向多对多 */
	private Set<Role> roles = new HashSet<Role>(0);
	
	/** 非持久属性 用来显示角色名的 */
	private String roleString;

	/** 非持久， 是否是超级管理员 */
	private boolean superAdmin;
	/**
	 */
	private Date lastLoginDate;
	
	/** 是否在线 ,默认为不在线 ，暂时不用*/
	private boolean online = false;
	
	private String email;
	
	private String phone;
	
//	/** 级别编码000-超级管理员， 000000 - 企业总公司管理员  9位-一级分销商 12位-二级分销商 */
//	private String levelcode;
	
	private String openId;
	
	/** 用来表示该管理员的上级管理员 */
	private Long pid;
	
	
	//---------------------------------- 非持久属性  --------------------------------
	/** 管理员管理的分销商名称 */
	private String orgName;
	
	
	public Admin() {
	}

	public Admin(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCompany() {
		return company;
	}

	
	public void setCompany(String company) {
		this.company = company;
	}
	
	/**
	 * @hibernate.property type="string"
	 */
	public String getAddress() {
		return address;
	}

	
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getProvince() {
		return province;
	}

	
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @hibernate.collection-many-to-many class="com.wfsc.common.bo.user.Role" column="roleId"
	 * @hibernate.collection-key column="adminId"
	 * @hibernate.set table="wf_admin_role" inverse="false" cascade="none" lazy="false"
	 * @return
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 判断用户是否具备指定的权限
	 * 
	 * @param permissionId 权限项ID
	 * 
	 * @return true则用户具备指定的权限，false则表示用户不具备对应的权限
	 */
	public boolean hasPermission(String permissionId) {
		if (permissionId == null)
			return false;
		Set<Role> roles = getRoles();
		if (roles == null || roles.isEmpty()) {
			return false;
		} else {
			for (Role role : roles) {
				Set<Permission> permissions = role.getPerms();
				if (permissions != null && permissions.size() > 0) {
					for (Permission permission : permissions) {
						if (permission.getActionId() != null && permission.getActionId().trim().equals(permissionId.trim())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}


	
	public String getRoleString() {
		StringBuffer sb = new StringBuffer();
		for (Role r : roles) {
			sb.append(r.getRoleName()).append(",");
		}
		return sb.toString();
	}

	
	public void setRoleString(String roleString) {
		this.roleString = roleString;
	}

	
	public boolean isSuperAdmin() {
		if(this.getRoleString().indexOf("超级管理员") != -1){
			return true;
		}
		return false;
	}
	public boolean isAdmin() {
		if(this.getRoleString().indexOf("平台管理员") != -1){
			return true;
		}
		return false;
	}
	
	public void setSuperAdmin(boolean isSuperAdmin) {
		this.superAdmin = isSuperAdmin;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="lastLoginDate"
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public Integer getStatus() {
		return status;
	}

	
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public Integer getType() {
		return type;
	}

	
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getLogCount() {
		return logCount;
	}

	
	public void setLogCount(int logCount) {
		this.logCount = logCount;
	}
	
	/**
	 * 是否在线
	 * 
	 * @hibernate.property type="boolean" column="isOnline"
	 */
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
//	/**
//	 * @hibernate.property type="string"
//	 */
//	public String getLevelcode() {
//		return levelcode;
//	}
//
//	public void setLevelcode(String levelcode) {
//		this.levelcode = levelcode;
//	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOpenId() {
		return openId;
	}

	
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @hibernate.property type="long"
	 */
	public Long getPid() {
		return pid;
	}

	
	public void setPid(Long pid) {
		this.pid = pid;
	}

	
	public String getOrgName() {
		return orgName;
	}

	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public boolean isDaili() {
		if(this.getRoleString().indexOf("代理商") != -1){
			return true;
		}
		return false;
	}
	
	
}