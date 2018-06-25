package model.bo.act;

import java.io.Serializable;

/**
 * 用户领取的红包记录表
 * 在用户购买商品的时候，通过此表可以查询可以抵扣的红包
 * 红包一旦被使用过，就失效。
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="userhongbao" lazy="false"
 */
public class UserHongBao implements Serializable {

	private static final long serialVersionUID = -4135464676632398902L;

	/** 0 红包 */
	public static final int TYPE_HB = 0;

	/** 1 优惠券 */
	public static final int TYPE_COUPON = 1;
	
	private Long id;
	 
	/** 所属公司 */
	private Long orgId;

	/** 红包序列号 */
	private String hbuuid;

	private Long userId;

	//这两个属性不能放这里冗余，否则红包的面额如果被修改，这里无法同步更新
//	/** 红包价值，单位：分 100分=1元 */
//	private Long hbvalue = 0L;
//	
//	/** 针对优惠券的属性 */
//	private float deduct;
	
	/** 用来描述红包或优惠券的说明，可能跟某些促销活动有关 */
	private String tips;
	
	/** 红包领取时间 */
	private String getDate;

	/** 状态， 是否已经用掉 0-未使用， 1-已使用 2-已过期 */
	private int status = 0;

	/** 类型; 0-红包 1-优惠券 */
	private int thetype = 0;
	
	//----------- 非持久对象 填充后用来在UI上显示 ------
	private HongBao hb;
	
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
	public Long getUserId() {
		return userId;
	}

	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getGetDate() {
		return getDate;
	}

	
	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTips() {
		return tips;
	}

	
	public void setTips(String tips) {
		this.tips = tips;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getHbuuid() {
		return hbuuid;
	}

	
	public void setHbuuid(String hbuuid) {
		this.hbuuid = hbuuid;
	}

	
	public HongBao getHb() {
		return hb;
	}

	
	public void setHb(HongBao hb) {
		this.hb = hb;
	}
}
