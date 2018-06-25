package com.wfsc.common.bo.order;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单物流
 * 
 * @version 1.0
 * @since cupid 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_logistics" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Logistics implements Serializable {
	
	private static final long serialVersionUID = 388822887409888948L;

	private Long id;

	/** 订单号 */
	private String orderCode;

	/** 物流状态， 0-未发货  1-已发货 2-已送达*/
	private Integer logistics;

	/** 物流负责人 */
	private String logCharger;
	
	private Long logChargerId;

	/** 发货时间 */
	private Date startDate;

	/** 结束时间 */
	private Date finishDate;


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
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	/**
	 * @hibernate.property type="int"
	 */
	public Integer getLogistics() {
		return logistics;
	}

	public void setLogistics(Integer logistics) {
		this.logistics = logistics;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getLogCharger() {
		return logCharger;
	}

	public void setLogCharger(String logCharger) {
		this.logCharger = logCharger;
	}
	/**
	 * @hibernate.property type="timestamp" column="startDate"
	 */
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @hibernate.property type="timestamp" column="finishDate"
	 */
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public Long getLogChargerId() {
		return logChargerId;
	}

	public void setLogChargerId(Long logChargerId) {
		this.logChargerId = logChargerId;
	}
	
	
}
