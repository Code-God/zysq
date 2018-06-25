package com.wfsc.common.bo.order;

import java.io.Serializable;

import com.wfsc.common.bo.user.User;

/**
 * 消费记录
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="f_consume" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Consume implements Serializable {

	private static final long serialVersionUID = 2282896123940009292L;

	public static final String ACT_DATING = "代理约见";
	public static final String ACT_MSG = "私信";
	public static final String ACT_GIFT = "礼品";
	
	private Long id;

	/** 当前用户 */
	private User user;

	/** 实际消费金额 */
	private float fee;

	/** 消费时间 datetime*/
	private String consumeTime;
	
	/** 消费项目 */
	private String action;
	
	/** 消费状态，0-冻结  1-完成  2-取消 */
	private int status;
	
	
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
	 * @hibernate.many-to-one class="com.wfsc.common.bo.user.User" column="userId"
	 */
	public User getUser() {
		return user;
	}

	
	public void setUser(User user) {
		this.user = user;
	}



	/**
	 * @hibernate.property type="float"
	 */
	public float getFee() {
		return fee;
	}

	
	public void setFee(float fee) {
		this.fee = fee;
	}


	/**
	 * @hibernate.property type="string"
	 */
	public String getConsumeTime() {
		return consumeTime;
	}

	
	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}


	/**
	 * @hibernate.property type="string"
	 */
	public String getAction() {
		return action;
	}

	
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getStatus() {
		return status;
	}

	
	public void setStatus(int status) {
		this.status = status;
	}
}
