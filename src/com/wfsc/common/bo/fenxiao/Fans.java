package com.wfsc.common.bo.fenxiao;

import java.io.Serializable;

/**
 * 经销商的粉丝列表
 * 
 * @author Jacky
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_fans"
 *                  lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Fans implements Serializable {

	private static final long serialVersionUID = 5994870801529243652L;
	// {
	// "subscribe": 1,
	// "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
	// "nickname": "Band",
	// "sex": 1,
	// "language": "zh_CN",
	// "city": "广州",
	// "province": "广东",
	// "country": "中国",
	// "headimgurl":
	// "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
	// "subscribe_time": 1382694957,
	// "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
	// }
	/**
	 * ID
	 */
	private Long id;

	private String openId;

	/** 昵称 */
	private String nickName;
	/** 头像 */
	private String headimgurl;

	private int sex = 1;

	private String city;

	private String province;

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
	 */
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	/**
	 * @hibernate.property type="int"
	 */
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @hibernate.property type="string"
	 */
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

}
