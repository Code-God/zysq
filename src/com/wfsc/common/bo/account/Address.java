package com.wfsc.common.bo.account;


/**
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_address" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Address {
	
	private static final long serialVersionUID = 2500471860354748649L;
	private Long id;
	private String province;//省
	private String city;//市
	private String area;//区
	private String detailAddr;//详细地址
	private String zipcode;//邮编
	private String phone;//手机
	private String tel;//电话
	private String email;//邮箱
	private String alias;//地址的别名，比如家庭地址或公司地址
	private Integer isDefault;//是否是默认地址
	private String username;//收货人姓名
	private Long userid;//地址所属人
	private String fullAddr;
	
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getDetailAddr() {
		return detailAddr;
	}
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return
	 * @hibernate.property type="long"
	 */
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getFullAddr() {
		return this.province+this.city+this.area+this.detailAddr;
	}
	public void setFullAddr(String fullAddr) {
		this.fullAddr = fullAddr;
	}
	
}
