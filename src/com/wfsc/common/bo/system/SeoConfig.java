package com.wfsc.common.bo.system;

import java.io.Serializable;

/**
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_seo_config" lazy="false"
 */
public class SeoConfig implements Serializable {

	private static final long serialVersionUID = -609598551526536079L;
	
	private Long id;
	
	private String title;
	
	private String keywords;
	
	private String description;

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
	 * @hibernate.property type="string"
	 */
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
