package com.wfsc.common.bo.system;

import java.io.Serializable;

/**
 *
 *@hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_emailserver_config" lazy="false"
 */
public class EmailServerConfig implements Serializable {
	
	private static final long serialVersionUID = -8587937781163611010L;

	private Long id;
	
	private String smtpAddress;
	
	private String userName;
	
	private String password;
	
	private Integer port = 25;
	
	private String fromAddress;
	
	private String subjectPrefix;

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
	 * @return
	 * @hibernate.property 
	 */
	public String getSmtpAddress() {
		return smtpAddress;
	}

	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
	}

	/**
	 * @return
	 * @hibernate.property 
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return
	 * @hibernate.property 
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return
	 * @hibernate.property 
	 */
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return
	 * @hibernate.property 
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * @return
	 * @hibernate.property 
	 */
	public String getSubjectPrefix() {
		return subjectPrefix;
	}

	public void setSubjectPrefix(String subjectPrefix) {
		this.subjectPrefix = subjectPrefix;
	}
	

}
