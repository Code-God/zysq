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

/**
 *  配送员
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_logCharger" lazy="false"
 */
public class LogCharger implements java.io.Serializable {

	private static final long serialVersionUID = -6883326884693425571L;

	private Long id;

	private String username;

	/** 状态: 1 - 可用 0 - 禁用 */
	private Integer status;
	
	/** 密码（需加密） */
	private String password;
	
	/** 维度，最大精度10位，6位小数， 如113.122343 */
	private Double lat;
	
	/** 经度，最大精度10位，6位小数 */
	private Double lon;
	
	/**
	 * 最后登录日期
	 */
	private Date lastLogin;
	
	

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
	 * @hibernate.property type="string"
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	 * @hibernate.property type="double"
	 */
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}
	/**
	 * @hibernate.property type="double"
	 */
	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	/**
	 * @hibernate.property type="timestamp" column="lastLogin"
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	
}