package model.bo.food;

import java.io.Serializable;

/**
 * 推广日志
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="tuigLog" lazy="false"
 */
public class TuigLog implements Serializable {

	private static final long serialVersionUID = -8979398317137313923L;

	private Long id;

	/** 用户的openId */
	private String openId;

	/** 点击的链接地址 */
	private String url;

	/** 推广日期 */
	private String tdate;

	/** 订单号 */
	private String orderNum;

	/** 购买的费用 */
	private float fee;
	
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
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * @hibernate.property type="float"
	 * @return
	 */
	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
}
