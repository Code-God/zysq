package com.wfsc.common.bo.order;

import java.io.Serializable;
import java.util.Date;

import com.wfsc.common.bo.product.Products;

/**
 * 订单详情
 * 
 * @version 1.0
 * @since cupid 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_orderDetail" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class OrdersDetail implements Serializable {
	
	private static final long serialVersionUID = 3888227777409888948L;

	private Long id;

	/** 订单号 */
	private String orderNo;

	/** 商品名称 */
	private String prodName;
	/** 非持久属性-----------商品名称处理，对于过长的，需要截断 */
	private String prodNameStr;

	/** 商品数量 */
	private Integer prdCount;
	
	private Long prdPrice;
	
	private Long totalPrice;

	/** 单价 */
	private Float price;

	/** 总价:单位：元 */
	private Float total;
	/** 商品code */
	private String prdCode;
	/** 商品图片地址 */
	private String prdImage;
	/** 是否已评论，1是0否 */
	private Integer isComment = 0;
	/** 购买日期*/
	private Date buyDate;
	//非持久属性
	private Products product;

	public OrdersDetail(){}
	public OrdersDetail(Products prd){
		this.prodName = prd.getName();
		this.prdCode = prd.getPrdCode();
		this.prdPrice = prd.getPrice();
		this.prdImage = prd.getPicUrl1();
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
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getPrdCount() {
		return prdCount;
	}

	public void setPrdCount(Integer prdCount) {
		this.prdCount = prdCount;
	}

	public Float getPrice() {
		if(this.prdPrice != null){
			return Float.valueOf(this.prdPrice / 100f);
		}
		return price;
	}

	public void setPrice(Float price) {
		if(price != null){
			this.totalPrice = (long)(price.floatValue() * 100);
		}
		this.price = price;
	}
	
	public Float getTotal() {
		if(this.totalPrice != null){
			return Float.valueOf(this.totalPrice / 100f);
		}
		return total;
	}

	public void setTotal(Float total) {
		if(total != null){
			this.totalPrice = (long)(total.floatValue() * 100);
		}
		this.total = total;
	}
	
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrdImage() {
		return prdImage;
	}

	public void setPrdImage(String prdImage) {
		this.prdImage = prdImage;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getIsComment() {
		return isComment;
	}

	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
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
	 * @hibernate.property type="timestamp" column="buyDate"
	 */
	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	/**
	 * @hibernate.property type="long" column="price"
	 * @return
	 */
	public Long getPrdPrice() {
		return prdPrice;
	}
	public void setPrdPrice(Long prdPrice) {
		this.prdPrice = prdPrice;
	}
	
	/**
	 * @hibernate.property type="long" column="total"
	 * @return
	 */
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Products getProduct() {
		return product;
	}
	
	public void setProduct(Products product) {
		this.product = product;
	}
	
	public String getProdNameStr() {
		if(this.prodName.length() > 10){
			return this.prodName.substring(0, 6) + "...";
		}
		return prodName;
	}
	
	public void setProdNameStr(String prodNameStr) {
		this.prodNameStr = prodNameStr;
	}

	
}
