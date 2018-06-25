package model.bo;

import java.io.Serializable;


/**
 * 
 * 考试表，总表， 明细表见：publishedTesting
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="onlineTests" lazy="false"
 */
public class OnlineTests implements Serializable {

	private static final long serialVersionUID = -3232069933786809177L;

	private Long id;

	/** 名称 */
	private String testTitle;

	private String testDesc;

	/** 发布时间 */
	private String pdate;

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
	public String getTestTitle() {
		return testTitle;
	}

	/**
	 * @param testTitle the testTitle to set
	 */
	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTestDesc() {
		return testDesc;
	}

	/**
	 * @param testDesc the testDesc to set
	 */
	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
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
