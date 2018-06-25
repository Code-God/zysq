package com.wfsc.common.bo.account;

import java.io.Serializable;

/**
 * 优惠券
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_coupon" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Coupon implements Serializable {

	private static final long serialVersionUID = -5034077849583421284L;

	/** 优惠券类型:洗车卡:10次 */
	public static final String TYPE_CLEAN_10 = "C_10";

	/** 优惠券类型:洗车卡:20次 */
	public static final String TYPE_CLEAN_20 = "C_20";

	/** 优惠券类型:洗车卡:30次 */
	public static final String TYPE_CLEAN_30 = "C_30";

	/** 优惠券类型:洗车卡:50次 */
	public static final String TYPE_CLEAN_50 = "C_50";

	/** 优惠券类型:洗车卡:100次 */
	public static final String TYPE_CLEAN_100 = "C_100";

	/** 优惠券类型:洗车卡:月卡 */
	public static final String TYPE_CLEAN_1M = "C_1M";

	/** 优惠券类型:洗车卡:3月卡 */
	public static final String TYPE_CLEAN_3M = "C_3M";

	/** 优惠券类型:洗车卡:半年 */
	public static final String TYPE_CLEAN_6M = "C_6M";

	/** 优惠券类型:洗车卡：一年 */
	public static final String TYPE_CLEAN_12M = "C_12M";

	/** 优惠券类型：普通抵扣券 */
	public static final String TYPE_MONEY = "M";

	//---------------------------------------------------------------------------
	
	/** 优惠券状态：未使用 */
	public static final Integer STATE_NEW = 0;

	/** 优惠券状态：已使用 */
	public static final Integer STATE_USED = 1;

	/** 优惠券状态：已过期 */
	public static final Integer STATE_EXPIRED = 2;

	private Long id;

	private String couponCode;// 优惠券代码

	private String couponName;// 优惠券名称

	private Float couponMoney;// 优惠券金额

	private Float needCustomMoney;// 需消费金额

	private String couponType;// 优惠券类型

	private String useLimit;// 使用限制

	private String userfulLife;// 有效期限

	/** 生成日期，即用户购买优惠券的日期 */
	private String generateDate;

	/** 有效期 */
	private Integer expireDays = 30;

	/** 因为优惠券是作为商品发布的，所以这里关联产品编码 */
	private String prdCode;

	/** 服务商ID */
	private Long serviceId;

	private Integer status = new Integer(0);// 状态 0-未使用  1-已使用

	/** 核销日期，服务商核销的日期 */
	private String consumeDate;

	/** 状态字符串 */
	private String statusStr;

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
	 * @return
	 * @hibernate.property type="long"
	 */
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
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
	 * @hibernate.property type="int"
	 */
	public Integer getExpireDays() {
		return expireDays;
	}

	public void setExpireDays(Integer expireDays) {
		this.expireDays = expireDays;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getConsumeDate() {
		return consumeDate;
	}

	public void setConsumeDate(String consumeDate) {
		this.consumeDate = consumeDate;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	/**
	 * @return
	 * @hibernate.property type="float"
	 */
	public Float getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(Float couponMoney) {
		this.couponMoney = couponMoney;
	}

	/**
	 * @return
	 * @hibernate.property type="float"
	 */
	public Float getNeedCustomMoney() {
		return needCustomMoney;
	}

	public void setNeedCustomMoney(Float needCustomMoney) {
		this.needCustomMoney = needCustomMoney;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getUseLimit() {
		return useLimit;
	}

	public void setUseLimit(String useLimit) {
		this.useLimit = useLimit;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getUserfulLife() {
		return userfulLife;
	}

	public void setUserfulLife(String userfulLife) {
		this.userfulLife = userfulLife;
	}

	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getStatusStr() {
		if (this.status == 0) {
			return "<font color=green>未使用</font>";
		} else if (this.status == 1) {
			return "<font color=red>已使用</font>";
		} else if (this.status == 2) {
			return "<font color=gray>已过期</font>";
		}
		return "<font color=green>未使用</font>";
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
}
