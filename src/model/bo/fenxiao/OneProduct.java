package model.bo.fenxiao;

import java.io.Serializable;

/**
 * 
 * 爆款商品表
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="oneproduct" lazy="false"
 */
public class OneProduct implements Serializable {

	private static final long serialVersionUID = -4858031003051284912L;

	private Long id;
	
	private String prdCode;

	private String title;

	private String prdDesc;

	/** 此爆款产品所属总销商 */
	private Long orgId;

	private String publishDate;

	private String pic1;
	private String pic1PrdCode;

	private String pic2;
	private String pic2PrdCode;

	private String pic3;
	private String pic3PrdCode;

	private String pic4;
	private String pic4PrdCode;

	private String pic5;
	private String pic5PrdCode;

	private String pic6;
	private String pic6PrdCode;

	private String pic7;
	private String pic7PrdCode;

	private String pic8;
	private String pic8PrdCode;

	private String pic9;
	private String pic9PrdCode;

	private String pic10;
	private String pic10PrdCode;

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
	public String getPrdDesc() {
		return prdDesc;
	}

	public void setPrdDesc(String prdDesc) {
		this.prdDesc = prdDesc;
	}

	/**
	 * @return
	 * @hibernate.property type="long"
	 */
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic3() {
		return pic3;
	}

	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic4() {
		return pic4;
	}

	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic5() {
		return pic5;
	}

	public void setPic5(String pic5) {
		this.pic5 = pic5;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic6() {
		return pic6;
	}

	public void setPic6(String pic6) {
		this.pic6 = pic6;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic7() {
		return pic7;
	}

	public void setPic7(String pic7) {
		this.pic7 = pic7;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic8() {
		return pic8;
	}

	public void setPic8(String pic8) {
		this.pic8 = pic8;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic9() {
		return pic9;
	}

	public void setPic9(String pic9) {
		this.pic9 = pic9;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic10() {
		return pic10;
	}

	public void setPic10(String pic10) {
		this.pic10 = pic10;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPrdCode() {
		return prdCode;
	}

	
	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic1PrdCode() {
		return pic1PrdCode;
	}

	
	public void setPic1PrdCode(String pic1PrdCode) {
		this.pic1PrdCode = pic1PrdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic2PrdCode() {
		return pic2PrdCode;
	}

	
	public void setPic2PrdCode(String pic2PrdCode) {
		this.pic2PrdCode = pic2PrdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic3PrdCode() {
		return pic3PrdCode;
	}

	
	public void setPic3PrdCode(String pic3PrdCode) {
		this.pic3PrdCode = pic3PrdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic4PrdCode() {
		return pic4PrdCode;
	}

	
	public void setPic4PrdCode(String pic4PrdCode) {
		this.pic4PrdCode = pic4PrdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic5PrdCode() {
		return pic5PrdCode;
	}

	
	public void setPic5PrdCode(String pic5PrdCode) {
		this.pic5PrdCode = pic5PrdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic6PrdCode() {
		return pic6PrdCode;
	}

	
	public void setPic6PrdCode(String pic6PrdCode) {
		this.pic6PrdCode = pic6PrdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic7PrdCode() {
		return pic7PrdCode;
	}

	
	public void setPic7PrdCode(String pic7PrdCode) {
		this.pic7PrdCode = pic7PrdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic8PrdCode() {
		return pic8PrdCode;
	}

	
	public void setPic8PrdCode(String pic8PrdCode) {
		this.pic8PrdCode = pic8PrdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic9PrdCode() {
		return pic9PrdCode;
	}

	
	public void setPic9PrdCode(String pic9PrdCode) {
		this.pic9PrdCode = pic9PrdCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPic10PrdCode() {
		return pic10PrdCode;
	}

	
	public void setPic10PrdCode(String pic10PrdCode) {
		this.pic10PrdCode = pic10PrdCode;
	}
}
