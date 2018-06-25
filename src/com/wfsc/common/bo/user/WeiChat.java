package com.wfsc.common.bo.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 * @version 1.0 
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="weichat_log" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class WeiChat implements java.io.Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8047804125565477230L;
	
	private Long id;
	
	private String toUserName;
	
	private String fromUserName;
	
	private String msgType;
	
	private Date createTime;
	
	private String picUrl;
	
	private String mediaId;
	
	private String msgId;
	
	private Integer reviewState;//待审核0，1，审核通过，2，审核未通过
	
	/**
	 * @hibernate.property type="int" column="review_state"
	 * @return
	 */
	public Integer getReviewState() {
		return reviewState;
	}
	public void setReviewState(Integer reviewState) {
		this.reviewState = reviewState;
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
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	/**
	 * @hibernate.property type="data"
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@Override
	public String toString() {
		return "weiChat [id=" + id + ", toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", msgType=" + msgType
				+ ", createTime=" + createTime + ", picUrl=" + picUrl
				+ ", mediaId=" + mediaId + ", msgId=" + msgId + "]";
	}
	
}
