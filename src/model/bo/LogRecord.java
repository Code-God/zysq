package model.bo;

import java.io.Serializable;

/**
 * 
 * 签到和抢沙发表
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="logrecord" lazy="false"
 */
public class LogRecord implements Serializable {

	private static final long serialVersionUID = -8562084010632161070L;

	/** 沙发 */
	public static final int ACT_SOFA = 1;

	/** 签到 */
	public static final int ACT_SIGN = 2;
	
	/** 登录 */
	public static final int ACT_LOGIN = 3;
	

	private Long id;

	/** 动作时间 */
	private String actTime;

	/** 操作者姓名 */
	private String userName;
	
	/** 操作者loginId */
	private String loginId;

	/** 沙发-1 签到-2 */
	private int actType;

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
	public String getActTime() {
		return actTime;
	}

	public void setActTime(String actTime) {
		this.actTime = actTime;
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
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getActType() {
		return actType;
	}

	public void setActType(int actType) {
		this.actType = actType;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getLoginId() {
		return loginId;
	}

	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
