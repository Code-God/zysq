package com.wfsc.common.bo.product;

import java.io.Serializable;
import java.util.Date;

/**
 * 已售商品记录
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_product_sold" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class ProductSold implements Serializable{

	private static final long serialVersionUID = -8900170583962069278L;
	
	private long id;
	
	/**
	 * 商品编码
	 */
	private String prdCode;
	
	/**
	 * 商品名称
	 */
	private String prdName;
	
	/**
	 * 售出时的价格（单价）
	 */
	private float price;
	
	/**
	 * 售出数量
	 */
	private int sales;
	
	/**
	 * 售出日期
	 */
	private Date saleDate;
	
	/**
	 * 购买的用户Id
	 */
	private long userId;

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
	 * @hibernate.property type="string"
	 */
	public String getPrdCode() {
		return prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	/**
	 * @return
	 * @hibernate.property type="float"
	 */
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp"
	 */
	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	/**
	 * @return
	 * @hibernate.property type="long"
	 */
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
