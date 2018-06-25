package model.bo;

import java.io.Serializable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * 专家表
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="experts" lazy="false"
 */
public class Experts implements Serializable {

	private static final long serialVersionUID = -4733676120157615669L;

	private Long id;

	/** 专家姓名 */
	private String expName;

	/** 专家描述 */
	private String expDesc;

	/** 非持久属性用来显示用 */
	private String expDescStr;

	/** 照片url */
	private String picUrl;

	/** 创建时间 */
	private String createDate;

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
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getExpName() {
		return expName;
	}

	public void setExpName(String expName) {
		this.expName = expName;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getExpDesc() {
		return expDesc;
	}

	public void setExpDesc(String expDesc) {
		this.expDesc = expDesc;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getExpDescStr() {
		if (this.expDesc != null) {
			Document parse = Jsoup.parse(expDesc);
			if(parse.text().length() > 31){
				return parse.text().substring(0, 30) + "...";
			}else{
				return parse.text();
			}
		}
		return "";
	}

	public void setExpDescStr(String expDescStr) {
		this.expDescStr = expDescStr;
	}
}
