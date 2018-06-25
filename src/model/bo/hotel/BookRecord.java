package model.bo.hotel;

import java.io.Serializable;

import com.wfsc.util.DateUtil;

/**
 * 房间预定记录表 Id | prdId | roomId | startTime | endTime| bookUser | telephone | booktype| bstatus roomId – 房间号  startTime –
 * 保留开始时间  endTime – 保留结束时间  bookUser– 预约者姓名  telephone – 电话  bstatus – 预约单状态： 0 – 已取消、已过期 1-预约中
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="bookRecord" lazy="false"
 */
public class BookRecord implements Serializable {

	private static final long serialVersionUID = 98498998068076019L;

	// 0 – 已取消、已过期 1-预约中
	public static final int STATUS_IDLE = 0;

	public static final int STATUS_BOOKED = 1;

	private Long id;

	/** 公司ID */
	private Long orgId;

	/** 预约者openId */
	private String openId;

	/** 产品ID, 根据这个产品ID可以知道用户预定的是什么类型的房间 */
	private Long prdid;
	
	/** 非持久属性， 根据prdid，获取产品名称 */
	private String prdName;

	/** 房间数量 */
	private int booknum;

	/** 房间ID， 预留字段， 除非和酒店内部的订房系统做对接 */
	private String roomId;

	/** 预约时间 */
	private String bookDate;

	private String bookDateStr;

	/** 到店时间 */
	private String arriveTime;

	private String arriveTimeStr;

	/** 提交时间 */
	private String submitDate;

	private String submitDateStr;

	/** 预定者姓名 */
	private String bookUser;

	private String telephone;

	/** 预约类型 booktype – 预约类型 */
	private int booktype;

	/** 预约单状态： 0 – 已取消、已过期 1-预约中 */
	private int bstatus;

	/** 预约状态对应文本，非持久 */
	private String bstatusStr;

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
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getPrdid() {
		return prdid;
	}

	public void setPrdid(Long prdid) {
		this.prdid = prdid;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getBookUser() {
		return bookUser;
	}

	public void setBookUser(String bookUser) {
		this.bookUser = bookUser;
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
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getBooktype() {
		return booktype;
	}

	public void setBooktype(int booktype) {
		this.booktype = booktype;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getBstatus() {
		return bstatus;
	}

	public void setBstatus(int bstatus) {
		this.bstatus = bstatus;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getBooknum() {
		return booknum;
	}

	public void setBooknum(int booknum) {
		this.booknum = booknum;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
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

	public String getBstatusStr() {
		// 0 – 已取消、已过期 1-预约中
		if (this.bstatus == 0) {
			return "<font color=red>已取消</font>";
		} else {
			return "预约中";
		}
	}

	public void setBstatusStr(String bstatusStr) {
		this.bstatusStr = bstatusStr;
	}

	public String getBookDateStr() {
		Long d = DateUtil.convertStringDate2Long(this.bookDate, "yyyy-MM-dd HH:mm");
		return DateUtil.convertLong2String(d, "MM/dd HH:mm");
	}

	public void setBookDateStr(String bookDateStr) {
		this.bookDateStr = bookDateStr;
	}

	public String getArriveTimeStr() {
		Long d = DateUtil.convertStringDate2Long(this.arriveTime, "yyyy-MM-dd HH:mm");
		return DateUtil.convertLong2String(d, "MM/dd HH:mm");
	}

	public void setArriveTimeStr(String arriveTimeStr) {
		this.arriveTimeStr = arriveTimeStr;
	}

	public String getSubmitDateStr() {
		Long d = DateUtil.convertStringDate2Long(this.submitDate, "yyyy-MM-dd HH:mm");
		return DateUtil.convertLong2String(d, "MM/dd HH:mm");
	}

	public void setSubmitDateStr(String submitDateStr) {
		this.submitDateStr = submitDateStr;
	}

	
	public String getPrdName() {
		return prdName;
	}

	
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
}
