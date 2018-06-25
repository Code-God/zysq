package com.wfsc.common.bo.shopcart;

import java.io.Serializable;

import com.wfsc.common.bo.product.Products;

/**
 * 购物车
 * 
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_shopcart" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class ShopCart implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 商品信息 * */
	private Products product;

	/**
	 * 数据库ID，自增长
	 */
	private long id;

	/**
	 * 用户ID
	 */
	private long userId;

	/**
	 * 商品编号
	 */
	private String prdCode;

	/**
	 * 购买数量
	 */
	private int count;
	
	private float disMoney;
	private float sumMoney;

	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrdCode() {
		return prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public float getDisMoney() {
		return disMoney;
	}

	public void setDisMoney(float disMoney) {
		this.disMoney = disMoney;
	}

	public float getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(float sumMoney) {
		this.sumMoney = sumMoney;
	}
	

}
