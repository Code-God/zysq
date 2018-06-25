package model.bo.car;

import java.io.Serializable;

/**
 * 车辆信息
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="car_carInfo" lazy="false"
 */
public class CarInfo implements Serializable {

	private static final long serialVersionUID = 3977779513416706112L;

	private Long id;

	/** 车主姓名 */
	private String userName;

	private String telephone;

	/** 车主关注后的 openId */
	private String openId;

	/** 车牌号 */
	private String carNo;

	/** 车架号，车辆识别代码 */
	private String carSn;

	/** 车辆型号 */
	private String carModel;

	/** 发动机号码 */
	private String enginSn;

	/** 城市 */
	private String city;

	/** 注册日期 */
	private String regDate;

	/** 是否过户车辆 0 -不是， 1-是过户 */
	private int guohu = 0;

	/** 单子状态： 0 - 待核保 1-已反馈 */
	private int flag = 0;

	/** 商业险起保日期 */
	private String bizInsStartDate;

	/** 交强险起保日期 */
	private String jqxStartDate;

	/** 用户提交日期 */
	private String submitDate;

	/** 保险费用,单位：分 */
	private Long price;

	/** 报价反馈日期 */
	private String feedbackDate;

	/** 保险信息，非持久 */
	private CarInsurenceInfo ciInfo;
	
	/** 关联的订单号 */
	private Long orderId;


	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getOrderId() {
		return orderId;
	}

	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public CarInsurenceInfo getCiInfo() {
		return ciInfo;
	}

	public void setCiInfo(CarInsurenceInfo ciInfo) {
		this.ciInfo = ciInfo;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getGuohu() {
		return guohu;
	}

	public void setGuohu(int guohu) {
		this.guohu = guohu;
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
	public String getBizInsStartDate() {
		return bizInsStartDate;
	}

	public void setBizInsStartDate(String bizInsStartDate) {
		this.bizInsStartDate = bizInsStartDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getJqxStartDate() {
		return jqxStartDate;
	}

	public void setJqxStartDate(String jqxStartDate) {
		this.jqxStartDate = jqxStartDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCarSn() {
		return carSn;
	}

	public void setCarSn(String carSn) {
		this.carSn = carSn;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getEnginSn() {
		return enginSn;
	}

	public void setEnginSn(String enginSn) {
		this.enginSn = enginSn;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
	public String getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(String feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
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
}
