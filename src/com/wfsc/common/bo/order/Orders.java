package com.wfsc.common.bo.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wfsc.common.bo.user.User;
import com.wfsc.util.DateUtil;

/**
 * 订单
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_orders" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Orders implements Serializable {

	/** 失败订单 */
	public static final int STATUS_FAIL = 0;

	/** 经过支付服务器的确认，成功的订单 */
	public static final int STATUS_OK = 2;

	/** 订单已生成，待确认状态 */
	public static final int STATUS_PENDING = 1;

	private static final long serialVersionUID = -3222083537911724061L;

	public static final String CTYPE_RMB = "RMB";

	private Long id;

	/** 订单号 */
	private String orderNo;

	/** 财付通交易ID */
	private String transactionId;

	/** 银行订单号 */
	private String bankBillNo;

	/** 银行类型 */
	private String bankType;

	/** 支付用户 */
	private User user;
	
	private String userName;
	
	private Long feePrice;

	/** 支付金额:单位：元 */
	private Float fee;

	/** 下单时间 */
	private Date odate;
	private String odateStr;
	
	/** 支付时间 datetime */
	private Date chargeDate;
	private String chargeDateStr;

	/** 支付类型 */
	private String ctype = "RMB";

	/** 订单状态 订单状态 0 – 未支付， 1-已支付，2-已发货，3-已完成 9-废弃*/
	private int status = 0;
	private String statusStr;
	
	private Long transFeePrice;
	
	/** 发货时间 */
	private String deliverDate;
	/** 确认收货时间 */
	private String getDate;
	
	/** 运费 */
	private Float transFee;
	
	// 收货地址
	private String address;

	// 收货人姓名
	private String addressee;
	
	// 收货人电话
	private String phone;
	
	
	/** 推广人微信号 */
	private String vname;
	/** 发票抬头 */
	private String invoiceTitle;
	
	/** 卖家留言 */
	private String buyerMsg;
	
	
	private List<OrdersDetail> ordersDetail = new ArrayList<OrdersDetail>();

	/** 所属分销商代码 */
	private String fxCode;
	
	/** 分销客ID */
	private Long fxpersonId;
	
	/** 是否结算   0-未结算， 1-已结算 */
	private int clear = 0;
	

	/** 支付系统交易ID */
	private String transId;
	
	/** 订单类别 0- 普通订单   1-车险订单 3- 待扩展.... */
	private int category = 0;
	

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
	 * @hibernate.many-to-one class="com.wfsc.common.bo.user.User" column="userId"
	 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Float getFee() {
		if(this.feePrice != null){
			return Float.valueOf(this.feePrice / 100f);
		}
		return fee;
	}

	public void setFee(Float fee) {
		if(fee != null){
			this.feePrice = (long)(fee.floatValue() * 100);
		}
		this.fee = fee;
	}

	/**
	 * @hibernate.property type="timestamp" column="chargeDate"
	 */
	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getBankBillNo() {
		return bankBillNo;
	}

	public void setBankBillNo(String bankBillNo) {
		this.bankBillNo = bankBillNo;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @hibernate.property type="timestamp" column="odate"
	 */
	public Date getOdate() {
		return odate;
	}

	
	public void setOdate(Date odate) {
		this.odate = odate;
	}

	public Float getTransFee() {
		if(transFeePrice != null){
			this.transFee = transFeePrice / 100f;
		}
		return transFee;
	}

	public void setTransFee(Float transFee) {
		if(transFee != null){
			this.transFeePrice = (long)(transFee.floatValue() * 100);
		}
		this.transFee = transFee;
	}

	public List<OrdersDetail> getOrdersDetail() {
		return ordersDetail;
	}

	public void setOrdersDetail(List<OrdersDetail> ordersDetail) {
		this.ordersDetail = ordersDetail;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @hibernate.property type="long" column="fee"
	 */
	public Long getFeePrice() {
		return feePrice;
	}

	public void setFeePrice(Long feePrice) {
		this.feePrice = feePrice;
	}
	
	/**
	 * @hibernate.property type="long" column="transFee"
	 */
	public Long getTransFeePrice() {
		return transFeePrice;
	}

	public void setTransFeePrice(Long transFeePrice) {
		this.transFeePrice = transFeePrice;
	}
	
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getVname() {
		return vname;
	}

	
	public void setVname(String vname) {
		this.vname = vname;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getBuyerMsg() {
		return buyerMsg;
	}

	
	public void setBuyerMsg(String buyerMsg) {
		this.buyerMsg = buyerMsg;
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
	public String getTransId() {
		return transId;
	}

	
	public void setTransId(String transId) {
		this.transId = transId;
	}

	
	public String getOdateStr() {
		if(this.odate != null){
			return DateUtil.getLongDate(this.odate);
		}else{
			return "";
		}
	}

	
	public void setOdateStr(String odateStr) {
		this.odateStr = odateStr;
	}

	
	public String getChargeDateStr() {
		if(this.chargeDate != null){
			return DateUtil.getLongDate(this.chargeDate);
		}else{
			return "";
		}
	}

	
	public void setChargeDateStr(String chargeDateStr) {
		this.chargeDateStr = chargeDateStr;
	}

	
	public String getStatusStr() {
//		0 – 未支付， 1-已支付，2-已发货，3-已完成 9-废弃*
		if(this.status == 0){
			return "未支付";
		}else if(this.status == 1){
			return "已支付";
		}else if(this.status == 2){
			return "已发货";
		}else if(this.status == 3){
			return "已完成";
		}else if(this.status == 9){
			return "废弃";
		}
		return "";
	}

	
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getFxCode() {
		return fxCode;
	}

	
	public void setFxCode(String fxCode) {
		this.fxCode = fxCode;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getFxpersonId() {
		return fxpersonId;
	}

	
	public void setFxpersonId(Long fxpersonId) {
		this.fxpersonId = fxpersonId;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getClear() {
		return clear;
	}

	
	public void setClear(int clear) {
		this.clear = clear;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getDeliverDate() {
		return deliverDate;
	}

	
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getGetDate() {
		return getDate;
	}

	
	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getCategory() {
		return category;
	}

	
	public void setCategory(int category) {
		this.category = category;
	}
}
