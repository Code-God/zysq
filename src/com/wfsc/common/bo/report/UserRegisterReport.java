package com.wfsc.common.bo.report;

import java.io.Serializable;

/**
 * 用于统计用户注册数据
 * 用户注册时需要更新该表的数据，根据当前时间所属的年份、月份、周三个条件，更新count计数器，如果没有该条记录，则新增一条记录
 * @author xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_report_user" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class UserRegisterReport implements Serializable {

	private static final long serialVersionUID = 2329951428814105667L;
	
	private long id;
	
	/**
	 * 年份
	 */
	private int year;
	
	/**
	 * 月份
	 */
	private int month;
	
	/**
	 * 周
	 */
	private int week;
	
	/**
	 * 该段时间内用户注册的数量
	 */
	private int regCount;
	
	/** 属于哪个分销商 */
	private Long orgId;
	

	/**
	 * @return
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public int getRegCount() {
		return regCount;
	}

	public void setRegCount(int regCount) {
		this.regCount = regCount;
	}

	/**
	 * @return
	 * @hibernate.property type="long"
	 */
	public Long getOrgId() {
		return orgId;
	}

	
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	

}
