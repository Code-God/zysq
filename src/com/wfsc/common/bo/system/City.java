package com.wfsc.common.bo.system;

import java.io.Serializable;

/**
 * 城市表 该表描述了系统所支持的城市以及热门城市
 * 城市编码对应的城市名称数据来源于百度城市数据库
 * 
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_city" lazy="false"
 */
public class City implements Serializable{
	
	private static final long serialVersionUID = 2407106674975469140L;

	private Long id;
	
	/**
	 * 城市编码
	 */
	private Integer code;
	
	/**
	 * 城市名
	 */
	private String name;
	
	/**
	 * 热门城市
	 */
	private boolean hot = false;

	/**
	 * 系统是否支持该城市
	 */
	private boolean support = false;
	
	/**
	 * 上级区域id
	 */
	private Long parentId;
	
	/**
	 * 城市层次码（每3位代表一个层级）
	 */
	private String cityCode;

	/**
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property type="boolean"
	 * @return
	 */
	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	/**
	 * @hibernate.property type="boolean"
	 * @return
	 */
	public boolean isSupport() {
		return support;
	}

	public void setSupport(boolean support) {
		this.support = support;
	}

	/**
	 * @hibernate.property type="long" column="parentId"
	 * @return
	 */
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	
}
