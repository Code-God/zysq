package com.wfsc.common.bo.account;

import java.util.Date;

import com.wfsc.common.bo.product.Products;

/**
 * 我的收藏
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_favourite" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Favourite {
	private static final long serialVersionUID = 2500471860354748649L;
	private Long id;
	private Products products;//收藏商品
	private Date storeDate;//收藏日期
	private Long userid;
	
	/**
	 * @return
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
	 * @return
	 * @hibernate.many-to-one class="com.wfsc.common.bo.product.Products" column="productsId"
	 *                        not-null="true"
	 */
	public Products getProducts() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="storeDate"
	 */
	public Date getStoreDate() {
		return storeDate;
	}
	public void setStoreDate(Date storeDate) {
		this.storeDate = storeDate;
	}
	/**
	 * @return
	 * @hibernate.property type="long"
	 */
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	

}
