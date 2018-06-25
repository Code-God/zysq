package com.wfsc.common.bo.product;

import java.io.Serializable;

/**
 * 商品库存
 * 商品库存和城市关联，商品如果支持该城市的配送，那么就会有一条库存数据，也就是说，如果一件商品支持10个城市配送，那么应该有10条库存数据
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_stock" lazy="false"
 */
public class ProductStock implements Serializable {
	
	private static final long serialVersionUID = 6747026003632760520L;

	private Long id;
	
	/**
	 * 商品编号
	 * 通过数据库外键关系维护库存数据，如果商品下架（即删除了），则数据库通过外键关系，将该商品所有的库存相关数据都删除
	 */
	private String prdCode;
	
	/**
	 * 库存
	 */
	private Integer stock;
	
	/**
	 * 城市编码
	 * 程序维护库存数据，如果一件商品开始是支持该城市配送的，后来不再支持（即在该城市下架该商品），那么通过程序代码，将该城市下面的商品库存数据都删除
	 */
	private Integer cityCode;

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
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}
	
}
