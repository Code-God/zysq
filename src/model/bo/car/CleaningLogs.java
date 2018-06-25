package model.bo.car;

import java.io.Serializable;

/**
 * 车主洗车记录
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="car_cleaningLogs" lazy="false"
 */
public class CleaningLogs implements Serializable {

	private static final long serialVersionUID = -8712780114655502743L;

	private Long id;

	/** 车主姓名 */
	private String userName;

	/** 车主关注后的 openId */
	private String openId;

	/** 洗车卡编号 */
	private String cardCode;
	/** 非持久属性 */
	private String serviceName;
	
	public String getServiceName() {
		return serviceName;
	}

	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/** 洗车日期 */
	private String theDate;

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
	public String getCardCode() {
		return cardCode;
	}

	
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTheDate() {
		return theDate;
	}

	public void setTheDate(String theDate) {
		this.theDate = theDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
}
