package model.bo.food;

import java.io.Serializable;

import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.user.User;

/**
 * 购物车 
 *
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="ShoppingCart" lazy="false"
 */
public class ShoppingCart implements Serializable{
	
	private static final long serialVersionUID = -1523099917961965798L;

	private Long id;
	
	private User user;
	
	/** 预定的产品  */
	private Products product;
	
	/** 数量 */
	private int scount;

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
	 * @hibernate.many-to-one class="com.wfsc.common.bo.product.Products" column="prdId"
	 */
	public Products getProduct() {
		return product;
	}

	
	public void setProduct(Products product) {
		this.product = product;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getScount() {
		return scount;
	}

	
	public void setScount(int scount) {
		this.scount = scount;
	}
	
	
}
