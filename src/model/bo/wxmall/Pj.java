package model.bo.wxmall;

import java.io.Serializable;

import com.wfsc.common.bo.user.User;

/**
 * 订单明细表
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="pj" lazy="false"
 */
public class Pj implements Serializable {

	private static final long serialVersionUID = 4524431908042141111L;

	private Long id;
	
	/** 企业ID */
	private Long orgId;
	
	/** 所属订单ID */
	private Long orderId;

	/** 产品ID */
	private Long prdid;
	
	//------非持久属性
	private String prdName;
	private String orderNum;

	/** 评价者 */
	private User pjer;
	/** 非持久，评价者姓名 */
	private String userName;
	
	/** 虚拟的评价用户名 */
	private String pjerName;
	

	private String content;

	/** 评价分数：1-5 */
	private int score = 5;
	private String scoreStr;
	

	private String pjDate;

	/**
	 * @hibernate.many-to-one class="com.wfsc.common.bo.user.User" column="userId" not-null="false"
	 */
	public User getPjer() {
		return pjer;
	}

	public void setPjer(User pjer) {
		this.pjer = pjer;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPjDate() {
		return pjDate;
	}

	public void setPjDate(String pjDate) {
		this.pjDate = pjDate;
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
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getOrderId() {
		return orderId;
	}

	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	
	public String getPrdName() {
		return prdName;
	}

	
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	
	public String getOrderNum() {
		return orderNum;
	}

	
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	
	public String getScoreStr() {
		StringBuffer s = new StringBuffer();
		for(int i=0; i< this.score; i++){
			s.append("★");
		}
		return s.toString();
	}

	
	public void setScoreStr(String scoreStr) {
		this.scoreStr = scoreStr;
	}

	
	public String getUserName() {
		return this.pjer.getNickName();
	}

	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPjerName() {
		return pjerName;
	}

	
	public void setPjerName(String pjerName) {
		this.pjerName = pjerName;
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

}
