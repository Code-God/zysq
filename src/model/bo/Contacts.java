package model.bo;

import java.io.Serializable;

/**
 * 
 * 联系我们
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="contacts" lazy="false"
 */
public class Contacts implements Serializable {

	private static final long serialVersionUID = -8780788189423049885L;

	private Long id;

	/** 联系方式区域标题 */
	private String ctitle;
	
	private Long orgId;
	
	private Integer locId;
	
	private String areaCode;
	
	private String telCode;
	
	/**
	 * 0 - 隐藏  1-显示
	 */
	private int flag;
	

	public Contacts() {
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
	public String getCtitle() {
		return ctitle;
	}

	
	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
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
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getLocId() {
		return locId;
	}

	
	public void setLocId(Integer locId) {
		this.locId = locId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getAreaCode() {
		return areaCode;
	}

	
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTelCode() {
		return telCode;
	}

	
	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getFlag() {
		return flag;
	}

	
	public void setFlag(int flag) {
		this.flag = flag;
	}

}
