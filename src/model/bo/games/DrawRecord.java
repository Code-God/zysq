package model.bo.games;

import java.io.Serializable;

/**
 * 
 * 抽奖记录
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="drawRecords" lazy="false"
 */
public class DrawRecord implements Serializable {

	private static final long serialVersionUID = -2371460674815047161L;

	private Long id;

	/** 抽奖人的opneId */
	private String openId;
	
	/** 活动ID */
	private Long actId;

	/** 时间 */
	private String drawTime;

	/** 游戏类型： 1-大转盘 2- 3- 4- */
	private int gtype = 1;
	
	/** 中奖码 见PrizeResult */
	private String code;
	
	/** 奖品，如果为空说明未中奖 */
	private String prize;
	
	
	//中奖者信息====================
//	"openId", "actId", "mail", "tel", "address", "username"
	private String mail;
	private String tel;
	private String address;
	private String username;
	/** 是否兑奖： 0-未兑奖  1-已兑奖 */
	private int pstatus = 0;
	

	public DrawRecord() {
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
	public String getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(String drawTime) {
		this.drawTime = drawTime;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getGtype() {
		return gtype;
	}

	public void setGtype(int gtype) {
		this.gtype = gtype;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrize() {
		return prize;
	}

	
	public void setPrize(String prize) {
		this.prize = prize;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getActId() {
		return actId;
	}

	
	public void setActId(Long actId) {
		this.actId = actId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCode() {
		return code;
	}

	
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getMail() {
		return mail;
	}

	
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTel() {
		return tel;
	}

	
	public void setTel(String tel) {
		this.tel = tel;
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

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getPstatus() {
		return pstatus;
	}

	
	public void setPstatus(int pstatus) {
		this.pstatus = pstatus;
	}
}
