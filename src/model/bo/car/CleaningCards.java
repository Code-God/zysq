package model.bo.car;

import java.io.Serializable;

import util.SysUtil;

import com.wfsc.common.bo.account.Coupon;

/**
 * 洗车卡记录
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="car_cleaningCards" lazy="false"
 */
public class CleaningCards implements Serializable {

	private static final long serialVersionUID = -8712780114655502743L;

	

	/** 洗车卡状态：未使用 */
	public static final Integer STATE_NEW = 0;
	/** 洗车卡状态 ： 次数用尽 */
	public static final Integer STATE_USED = 1;
	/** 洗车卡状态：已过期*/
	public static final Integer STATE_EXPIRED = 2;
	
	private Long id;

	/** 车主姓名 */
	private String userName;

	/** 车主关注后的 openId */
	private String openId;
	
	/** 适用服务商,在发布洗车卡商品时，需要选择所属服务商 */
	private Long serviceId;
	
	/** 非持久，服务商名称 */
	private String serviceName;

	/** 卡编号 */
	private String cardCode;

	/**
	 * 洗车卡类型：
	 * 
	 * @see Coupon.TYPE_CLEAN_10 Coupon.TYPE_CLEAN_30 Coupon.TYPE_CLEAN_60...
	 */
	private String cardType;
	private String cardTypeStr;


	
	public String getServiceName() {
		return serviceName;
	}

	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getCardTypeStr() {
		return SysUtil.getCardTypeStr(cardType);
	}

	public void setCardTypeStr(String cardTypeStr) {
		this.cardTypeStr = cardTypeStr;
	}

	/**
	 * 总次数，如果是月卡，半年卡或年卡类型的，此值为0
	 */
	private Integer totalPoints = 0;

	/** 剩余点数，每次洗车后减少1 */
	private Integer leftPoints = 0;

	/** 生成日期 */
	private String generateDate;

	/** 过期日期 */
	private String expireDate;

	/**
	 * 卡状态： 0 - 正常， 1-用完      2-已过期
	 */
	private Integer status = 0;
	private String statusStr = "正常";

	
	public String getStatusStr() {
		if(this.status == 0){
			return "正常";
		}else if(this.status == 1){
			return "用完";
		}else if(this.status == 2){
			return "已过期";
		}
		return "正常";
	}

	
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
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
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getServiceId() {
		return serviceId;
	}

	
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
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
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getLeftPoints() {
		return leftPoints;
	}

	public void setLeftPoints(Integer leftPoints) {
		this.leftPoints = leftPoints;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
}
