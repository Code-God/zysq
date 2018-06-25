package model.bo.food;

import java.io.Serializable;

import com.wfsc.common.bo.user.User;

/**
 * 虚拟货币充值记录
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="chargefeerecord" lazy="false"
 */
public class ChargeFeeRecord implements Serializable {

	private static final long serialVersionUID = -3237519255125692666L;

	private Long id;

	/** 所属用户 */
	private User user;

	/** 货币数量 */
	private int fb;

	/** 充值时间 */
	private String chargeDate;

	/** 到期时间,已经过期的记录自动从用户表扣除 */
	private String expireDate;

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getFb() {
		return fb;
	}

	public void setFb(int fb) {
		this.fb = fb;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(String chargeDate) {
		this.chargeDate = chargeDate;
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
}
