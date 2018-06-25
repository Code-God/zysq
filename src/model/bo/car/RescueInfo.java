package model.bo.car;

import java.io.Serializable;

/**
 * 代理商：救援信息
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="car_rescueInfo" lazy="false"
 */
public class RescueInfo implements Serializable {

	private static final long serialVersionUID = 1343001702644669140L;

	private Long id;

	/** 所属地区，不同代理商所管理地区不同 */
	private String area;
	
	/** 服务商名称 */
	private String facName;

	/** 服务内容描述 */
	private String serviceContent;

	/** 联系电话 */
	private String telephone;

	/** 地址 */
	private String address;
	
	/** 联系人 */
	private String charger;


	/** 服务价格 */
	private Long price;

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCharger() {
		return charger;
	}

	
	public void setCharger(String charger) {
		this.charger = charger;
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
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getArea() {
		return area;
	}

	
	public void setArea(String area) {
		this.area = area;
	}
	
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
