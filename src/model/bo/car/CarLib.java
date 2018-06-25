package model.bo.car;

import java.io.Serializable;

/**
 * 车型数据库
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="carlib" lazy="false"
 */
public class CarLib implements Serializable {

	private static final long serialVersionUID = -7421370960470060726L;

	private Long id;

	/** 字母 */
	private String alphabeta;

	/** 品牌名称 */
	private String brandName;

	/** 车厂商名称 */
	private String facName;

	/** 车系名称， 如奥迪A1(进口) */
	private String typeName;

	/** 车型年款， 如2011款 */
	private String carYear;

	/** 车型名称 */
	private String carTypeName;

	private String brandCountry;

	/** 级别 */
	private String carLevel;

	/** 排量 */
	private int cc;

	/** 变速箱 */
	private String gear;

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
	public String getAlphabeta() {
		return alphabeta;
	}

	public void setAlphabeta(String alphabeta) {
		this.alphabeta = alphabeta;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getFacName() {
		return facName;
	}

	public void setFacName(String facName) {
		this.facName = facName;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCarYear() {
		return carYear;
	}

	public void setCarYear(String carYear) {
		this.carYear = carYear;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getBrandCountry() {
		return brandCountry;
	}

	public void setBrandCountry(String brandCountry) {
		this.brandCountry = brandCountry;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCarLevel() {
		return carLevel;
	}

	public void setCarLevel(String carLevel) {
		this.carLevel = carLevel;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}
}
