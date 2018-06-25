package com.wfsc.common.bo.product;

import java.io.Serializable;


/**
 * 新品特惠推荐
 * 
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_prdrecommend" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class ProductRecommend implements Serializable {

	private static final long serialVersionUID = 6482097698762227897L;

	/** 新品推荐* */
	public static int TYPE_NEW = 1;
	/** 本周特惠* */
	public static int TYPE_CHEAP = 2;

	private long id;

	// 1-新品推荐,2-本周特惠
	private int type;
	
	/**关联商品**/
	private Products product;

	/**
	 * @hibernate.many-to-one class="com.wfsc.common.bo.product.Products" column="prdId"
	 *                        not-null="true"
	 * @return
	 */
	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

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
	 * @hibernate.property type="int" column="type"
	 * @return
	 */
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
