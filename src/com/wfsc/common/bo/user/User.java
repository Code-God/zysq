package com.wfsc.common.bo.user;

import java.util.Date;

import util.SysUtil;
import model.bo.auth.Org;

import com.wfsc.util.DateUtil;

/**
 * @author jacky
 * @version 1.0 用户基本信息,原则上不可修改的信息: 用户名(登录名),真实姓名,性别,等等
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_user" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class User implements java.io.Serializable {

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", username=" + username
				+ ", nickName=" + nickName + ", telephone=" + telephone
				+ ", openId=" + openId + ", qq=" + qq + ", lastLogin="
				+ lastLogin + ", lastLoginStr=" + lastLoginStr + ", loginIp="
				+ loginIp + ", password=" + password + ", status=" + status
				+ ", substate=" + substate + ", regDate=" + regDate
				+ ", regDateStr=" + regDateStr + ", orders=" + orders
				+ ", activeCode=" + activeCode + ", level=" + level
				+ ", online=" + online + ", statusStr=" + statusStr
				+ ", score=" + score + ", cityCode=" + cityCode + ", org="
				+ org + ", orgname=" + orgname + ", headimgurl=" + headimgurl
				+ ", country=" + country + ", subscribTime=" + subscribTime
				+ ", flag=" + flag + ", yj=" + yj + ", orderFee=" + orderFee
				+ ", realName=" + realName + ", mobilePhone=" + mobilePhone
				+ ", sex=" + sex + ", age=" + age + ", birthDate=" + birthDate
				+ ", province=" + province + ", city=" + city
				+ ", cityLevelCode=" + cityLevelCode + ", address=" + address
				+ ", medicalHistoryDescription=" + medicalHistoryDescription
				+ ", auditStatus=" + auditStatus + ", userPic=" + userPic
				+ ", twoDimensionCode=" + twoDimensionCode
				+ ", registrationDate=" + registrationDate
				+ ", wechatNickname=" + wechatNickname + ", wechatId="
				+ wechatId + ", userType=" + userType + ", inviterId="
				+ inviterId + ", qr_code_url=" + qr_code_url
				+ ", qr_code_time=" + qr_code_time + ", hospital=" + hospital
				+ ", department=" + department + ", position=" + position
				+ ", points=" + points + ", pid=" + pid + "]";
	}


	private static final long serialVersionUID = 2500471860354748649L;

	private Long id;

	/**
	 * 注册邮箱，可用于登录，相当于用户名，唯一，数据库中不应该存在两个相同的邮箱地址
	 */
	private String email;

	/** 用户姓名 */
	private String username;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 手机号码，可用于登录，唯一，数据库中不应该存在两个相同的手机号码
	 */
	private String telephone;

	/**
	 * 微信公众号的ID，保留
	 */
	private String openId;

	/**
	 * QQ号码
	 */
	private String qq;

	/**
	 * 最后登录时间
	 */
	private Date lastLogin;

	private String lastLoginStr;

	/**
	 * 登录IP
	 */
	private String loginIp;

	/**
	 * MD5编码，密码
	 */
	private String password;

	/**
	 * 0 – 禁用， 1-可用， 9-待激活
	 */
	private Integer status = new Integer(0);

	/** 订阅状态： 1-订阅， 0-未订阅  */
	private int substate = 1;

	/**
	 * 注册日期
	 */
	private Date regDate;

	//--------------------- 非持久属性 ----------------
	private String regDateStr;
	private int orders;


	/**
	 * 激活码，在用户注册成功以后生成
	 */
	private String activeCode;

	/**
	 * 用户级别，预留
	 */
	private Integer level;

	/** 是否在线 ,默认为不在线 */
	private boolean online = false;

	private String statusStr;

	private Integer score;// 积分

	/**
	 * 用户选择城市的编码，当用户登录后，每次更改了该编码后，将会记录如数据库
	 */
	private Integer cityCode;

	/** 组织机构 */
	private Org org;

	private String orgname;

	private String headimgurl;

	private String country;


	private String subscribTime;

	/** 0 - 普通会员 1-分销客 */
	private int flag;

	/** 当该用户属于分销客时的佣金， 单位： 分； 非持久 */
	private Long yj;

	/** 非持久，订单总额 */
	private Long orderFee;



	/** 
	 * 真实姓名
	 */
	private String realName;
	/** 
	 * 联系电话
	 */
	private String mobilePhone;
	/** 
	 * 性别：1：男；2：女；0：未知；默认值1；
	 */
	private String sex;
	/** 
	 * 年龄
	 */
	private int age;
	
	/** 
	 * 出生日期
	 */
	private Date birthDate;

	/** 
	 * 所在省份
	 */
	private String province;
	/** 
	 * 所在城市
	 */
	private String city;
	/** 
	 * 所在城市层次编码
	 */
	private String cityLevelCode;
	/** 
	 * 通讯地址
	 */
	private String address;
	/** 
	 * 病史描述
	 */
	private String medicalHistoryDescription;
	/** 
	 * 审核状态：0：待审核；11：病史审核通过；12：病史审核未通过；21：联系中通过；21：联系中未通过；31：医院筛选通过；32：医院筛选未通过；41：审核通过；42：审核未通过；默认值0；
	 */
	private int auditStatus;

	/** 
	 * 头像图片路径
	 */
	private String userPic;
	/** 
	 * 二维码图片路径
	 */
	private String twoDimensionCode;
	/** 
	 * 注册日期
	 */
	private Date registrationDate;
	/** 
	 * 微信昵称
	 */
	private String wechatNickname;
	/** 
	 * 微信id
	 */
	private String wechatId;
	
	/** 
	 * 用户类型：1：医生；2：护士；3：患者；9：其他； 默认值9；
	 */
	private int userType;

	
	/**
	 * 邀请人id
	 */
	private Integer inviterId;
	
	/**
	 * 带有用户id的临时二维码
	 */
	private String qr_code_url;
	
	/**
	 * 二维码获取时间
	 */
	private Date qr_code_time;
	
	/**
	 * 医院
	 */
	private String hospital;
	
	/**
	 * 科室
	 */
	private String department;
	
	/**
	 * 1:实习医生，2：住院总，3：主治医生，4：副主任医师，5：主任医师，9：其他
	 */
	
	private String position;
	
	/**
	 * 用户积分
	 */
	
	private Integer points;
	




	public Long getOrderFee() {
		return orderFee;
	}


	public void setOrderFee(Long orderFee) {
		this.orderFee = orderFee;
	}


	/** 上线的分销客ID */
	private Long pid;

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

	/**
	 * @hibernate.property type="string"
	 */
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}

	/**
	 * @return
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="email" not-null="false" length="255"
	 * @hibernate.property unique="true"
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="nickname" length="255"
	 */
	public String getNickName() {
		
		return SysUtil.decodeBase64(nickName);
	}

	public void setNickName(String nickName) {
		
		this.nickName = SysUtil.encodeBase64(nickName);
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="telephone" length="15"
	 */
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="openid" length="255"
	 */
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="qq" length="20"
	 */
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="lastlogin"
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="loginip" length="50"
	 */
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="password" not-null="false" length="255"
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return
	 * @hibernate.property type="int" column="status" not-null="false" length="1"
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="regdate" not-null="true"
	 */
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="activecode" length="100"
	 */
	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	/**
	 * @return
	 * @hibernate.property type="int" column="level" length="1"
	 */
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 是否在线
	 * 
	 * @hibernate.property type="boolean" column="isOnline"
	 */
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getStatusStr() {
		// 0 – 禁用， 1-可用， 9-待激活
		if (this.status.intValue() == 0) {
			return "<font color=red>禁用</font>";
		}
		if (this.status.intValue() == 1) {
			return "<font color=green>正常</font>";
		}
		if (this.status.intValue() == 9) {
			return "待激活";
		}
		return "正常";
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	/**
	 * 积分
	 * 
	 * @hibernate.property type="int"
	 */
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 所属组织机构
	 * 
	 * @hibernate.many-to-one class="model.bo.auth.Org" column="orgId" not-null="false"
	 */
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getOrgname() {
		if(this.org != null){
			return this.org.getOrgname();
		}else{
			return "";
		}
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getLastLoginStr() {
		return DateUtil.getLongDate(this.lastLogin);
	}

	public void setLastLoginStr(String lastLoginStr) {
		this.lastLoginStr = lastLoginStr;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getSubscribTime() {
		return subscribTime;
	}

	public void setSubscribTime(String subscribTime) {
		this.subscribTime = subscribTime;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	//非持久
	public Long getYj() {
		return yj;
	}


	public void setYj(Long yj) {
		this.yj = yj;
	}

	/**
	 * @hibernate.property type="long"
	 */
	public Long getPid() {
		return pid;
	}


	public void setPid(Long pid) {
		this.pid = pid;
	}


	public String getRegDateStr() {
		return DateUtil.getLongDate(this.regDate);
	}


	public void setRegDateStr(String regDateStr) {
		this.regDateStr = regDateStr;
	}


	public int getOrders() {
		return orders;
	}


	public void setOrders(int orders) {
		this.orders = orders;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getSubstate() {
		return substate;
	}


	public void setSubstate(int substate) {
		this.substate = substate;
	}


	/**
	 * @hibernate.property type="string" column="realName"
	 */
	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @hibernate.property type="string" column="mobile_phone"
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}





	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	/**
	 * @hibernate.property type="int" column="age"
	 */
	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	/**
	 * @hibernate.property type="string" column="address"
	 */
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * @hibernate.property type="string" column="medical_history_description"
	 */
	public String getMedicalHistoryDescription() {
		return medicalHistoryDescription;
	}


	public void setMedicalHistoryDescription(String medicalHistoryDescription) {
		this.medicalHistoryDescription = medicalHistoryDescription;
	}


	/**
	 * @hibernate.property type="int" column="audit_status"
	 */
	public int getAuditStatus() {
		return auditStatus;
	}


	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}


	/**
	 * @hibernate.property type="string" column="user_pic"
	 */
	public String getUserPic() {
		return userPic;
	}


	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}


	/**
	 * @hibernate.property type="string" column="two_dimension_code"
	 */
	public String getTwoDimensionCode() {
		return twoDimensionCode;
	}


	public void setTwoDimensionCode(String twoDimensionCode) {
		this.twoDimensionCode = twoDimensionCode;
	}


	/**
	 * @hibernate.property type="timestamp" column="registration_date"
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}


	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}


	/**
	 * @hibernate.property type="string" column="wechat_nickname"
	 */
	public String getWechatNickname() {
		return wechatNickname;
	}


	public void setWechatNickname(String wechatNickname) {
		this.wechatNickname = wechatNickname;
	}

	/**
	 * @hibernate.property type="string" column="wechat_id"
	 */
	public String getWechatId() {
		return wechatId;
	}


	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	/**
	 * @hibernate.property type="int" column="userType"
	 */
	public int getUserType() {
		return userType;
	}


	public void setUserType(int userType) {
		this.userType = userType;
	}

	/**
	 * @hibernate.property type="string" column="cityLevelCode"
	 */
	public String getCityLevelCode() {
		return cityLevelCode;
	}


	public void setCityLevelCode(String cityLevelCode) {
		this.cityLevelCode = cityLevelCode;
	}

	/**
	 * @hibernate.property type="int" column="inviter_id"
	 */
	public Integer getInviterId() {
		return inviterId;
	}


	public void setInviterId(Integer inviterId) {
		this.inviterId = inviterId;
	}


	/**
	 * @hibernate.property type="string" column="qr_code_url"
	 */
	public String getQr_code_url() {
		return qr_code_url;
	}


	public void setQr_code_url(String qr_code_url) {
		this.qr_code_url = qr_code_url;
	}

	/**
	 * @hibernate.property type="timestamp" column="qr_code_time"
	 */
	public Date getQr_code_time() {
		return qr_code_time;
	}


	public void setQr_code_time(Date qr_code_time) {
		this.qr_code_time = qr_code_time;
	}
	/**
	 * @hibernate.property type="string" column="hospital"
	 */
	public String getHospital() {
		return hospital;
	}


	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	/**
	 * @hibernate.property type="string" column="department"
	 */
	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @hibernate.property type="integer" column="points"
	 */
	
	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public Integer getPoints() {
		return points;
	}


	public void setPoints(Integer points) {
		this.points = points;
	}

	
}