package model.vo;

import java.io.Serializable;

/**
 * "openid":" OPENID", " nickname": NICKNAME, "sex":"1", "province":"PROVINCE" "city":"CITY", "country":"COUNTRY",
 * "headimgurl":
 * "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
 * "privilege":[ "PRIVILEGE1" "PRIVILEGE2" ], "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
 * 
 * @author jacky
 * @version 1.0
 * @since Resint 1.0
 */
public class WxUser implements Serializable {

	private static final long serialVersionUID = 8507592582818497437L;

	private String openid;

	private String nickName;
	/** 1 - 男， 2-女， 0- 未知 */
	private String sex;

	private String province;

	private String city;

	private String country;

	private String headimgurl;

	private String unionid;
	/** 订阅时间 */
	private String subscribeTime;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	
	public String getSex() {
		return sex;
	}

	
	public void setSex(String sex) {
		this.sex = sex;
	}

	
	public String getSubscribeTime() {
		return subscribeTime;
	}

	
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("nickname=" + nickName);
		sb.append(" | sex=" + sex);
		sb.append(" | headimgurl=" + headimgurl);
		sb.append(" | city=" + city);
		sb.append(" | province=" + province);
		sb.append(" | country=" + country);
		sb.append(" | subscribeTime=" + subscribeTime);
		sb.append(" | unionid=" + unionid);
		return sb.toString();
	}
	
}
