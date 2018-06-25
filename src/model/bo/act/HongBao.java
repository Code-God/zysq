package model.bo.act;

import java.io.Serializable;

/**
 * 红包、优惠券表; 管理员创建的红包和优惠券都在这张表里记录
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="hongbao" lazy="false"
 */
public class HongBao implements Serializable {

	private static final long serialVersionUID = 206767549654909317L;

	/** 0 红包 */
	public static final int TYPE_HB = 0;

	/** 1 优惠券 */
	public static final int TYPE_COUPON = 1;

	private Long id;
	
	/** 为防止造假和推测，红包批次的唯一ID */
	private String uuid;

	/** 公司ID */
	private Long orgId;

	/** 红包价值，单位：分 | 100分=1元 */
	private Long hbvalue;
	
	//转化为元后用来显示的字段
	private String hbvalueStr;
	
	/** 优惠券折扣 */
	private float deduct;

	/** 过期时间(对优惠券有用，对红包保留) */
	private String expireDate;
	
	/** 创建时间 */
	private String createDate;

	/** 状态，是否被领取，是否已经用掉 0-未使用， 1-已使用 */
	private int status = 0;

	/** 类型; 0-红包 1-优惠券 */
	private int thetype = 0;

	/** 发放数量 */
	private int num;
	
	/** 已经领取的数量 */
	private int numused;
	
	
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
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getHbvalue() {
		return hbvalue;
	}

	public void setHbvalue(Long hbvalue) {
		this.hbvalue = hbvalue;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getThetype() {
		return thetype;
	}

	public void setThetype(int thetype) {
		this.thetype = thetype;
	}

	/**
	 * @hibernate.property type="float"
	 * @return
	 */
	public float getDeduct() {
		return deduct;
	}

	
	public void setDeduct(float deduct) {
		this.deduct = deduct;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getNum() {
		return num;
	}

	
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getNumused() {
		return numused;
	}

	
	public void setNumused(int numused) {
		this.numused = numused;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCreateDate() {
		return createDate;
	}

	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getUuid() {
		return uuid;
	}

	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	public String getHbvalueStr() {
		return String.valueOf(this.hbvalue/100);
	}

	
	public void setHbvalueStr(String hbvalueStr) {
		this.hbvalueStr = hbvalueStr;
	}
}
