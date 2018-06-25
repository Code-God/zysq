package model.bo;

import java.io.Serializable;

/**
 * 
 * 联系我们
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="aboutus" lazy="false"
 */
public class AboutUs implements Serializable {

	private static final long serialVersionUID = -2923921484776305159L;

	private Long id;

	/** 调研标题 */
	private String ttitle;

	/** 调研正文描述 */
	private String tcontent;

	/** 发布时间* */
	private String pdate;

	public AboutUs() {
	}

	public AboutUs(String title, String content) {
		this.ttitle = title;
		this.tcontent = content;
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
	public String getTtitle() {
		return ttitle;
	}

	/**
	 * @param ttitle the ttitle to set
	 */
	public void setTtitle(String ttitle) {
		this.ttitle = ttitle;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTcontent() {
		return tcontent;
	}

	/**
	 * @param tcontent the tcontent to set
	 */
	public void setTcontent(String tcontent) {
		this.tcontent = tcontent;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPdate() {
		return pdate;
	}

	/**
	 * @param pdate the pdate to set
	 */
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}
}
