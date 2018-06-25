package model.bo;

import java.io.Serializable;

/**
 * 
 * 用户反馈
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="feedback" lazy="false"
 */
public class Feedback implements Serializable {

	private static final long serialVersionUID = -6650912185116653601L;

	private Long id;

	/** 反馈人 */
	private String username;
	
	//非持久属性
	private String telephone;

	/** 反馈内容 */
	private String fcontent;

	/** 反馈时间* */
	private String fdate;

	public Feedback() {
	}

	public Feedback(String name, String content) {
		this.username = name;
		this.fcontent = content;
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
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getFcontent() {
		return fcontent;
	}

	
	/**
	 * @param fcontent the fcontent to set
	 */
	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getFdate() {
		return fdate;
	}

	
	/**
	 * @param fdate the fdate to set
	 */
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
