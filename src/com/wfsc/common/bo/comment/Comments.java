package com.wfsc.common.bo.comment;

import java.io.Serializable;
import java.util.Date;

import com.wfsc.common.bo.user.User;

/**
 * 评价
 * 
 * @version 1.0
 * @since cupid 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_comments" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Comments implements Serializable {

	private static final long serialVersionUID = 388822880489888948L;

	private Long id;

	/** 评论人ID,即用户ID */
	private Long creatorId;
	/** 评论人昵称 */
	private String nickName;

	/** 商品编码 */
	private String prdCode;
	/** 商品名称*/
	private String prdName;

	/** 评论内容 */
	private String content;
	//回复内容
	private String resContent;

	/** 满意度：1很不满意，2不满意，3一般，4满意，5非常满意 */
	private Integer stars;

	/** 评论日期 */
	private Date pdate;
	/** 格式化评论日期 */
	private String showDate;

	/** 关联用户 * */
	private User user;
	
	private Long orderDetailId;
	
	/**
	 * 所属一级分类，方便报表统计
	 */
	private String prdTopCode;

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
	 */
	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPrdCode() {
		return prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	/**
	 * @hibernate.property type="timestamp" column="pdate"
	 */
	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getResContent() {
		return resContent;
	}

	public void setResContent(String resContent) {
		this.resContent = resContent;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}
	/**
	 * @hibernate.property type="long"
	 */
	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getPrdTopCode() {
		return prdTopCode;
	}

	public void setPrdTopCode(String prdTopCode) {
		this.prdTopCode = prdTopCode;
	}
	

}
