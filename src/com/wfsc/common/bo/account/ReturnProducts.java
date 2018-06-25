package com.wfsc.common.bo.account;

import java.util.Date;

/**
 * 退换货
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_returnproducts" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class ReturnProducts {
	private static final long serialVersionUID = 2500471860354748649L;
	private Long id;
	private String returnCode;//退换货编号
	private Integer type;//类型，退货还是换货,1退2换
	private String status;//状态
	private String orderNo;//关联的订单号
	private Date returnDate;//申请退换货时间
	private Long userid;
	private String reason;//退换货原因
	private String addr;//取件地址
	private String phone;//联系人手机
	private String username;//联系人姓名
	private String prdCode;
	private String prdName;
	
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
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="returnDate"
	 */
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getPrdName() {
		return prdName;
	}
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	
	
}
