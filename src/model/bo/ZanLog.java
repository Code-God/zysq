package model.bo;

import java.io.Serializable;

/**
 * 
 * 点赞的记录，防止重复点赞
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="zanLog" lazy="false"
 */
public class ZanLog implements Serializable {

	private static final long serialVersionUID = -7685511647332840959L;

	private Long id;

	/** 点赞人ID */
	private String loginId;

	/** 点赞时间 */
	private String ztime;

	/** 目标ID */
	private String targetId;

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
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getZtime() {
		return ztime;
	}

	public void setZtime(String ztime) {
		this.ztime = ztime;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
}
