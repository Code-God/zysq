package model.bo.auth;

import java.io.Serializable;

import com.wfsc.common.bo.user.Admin;

/**
 * 无限分销商组织机构
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="comorg" lazy="false"
 */
public class Org implements Serializable, Comparable<Org> {

	private static final long serialVersionUID = -3070247527750095665L;

	private Long id;

	/** 父级 */
	private Org parent;

	/** 组织名称 */
	private String orgname;

	/** 代码 */
	private String code;

	/** 负责人 */
	private Admin charger;

	/** 总分:非持久化 */
	private int totalScore;

	/** 分销商状态： 0 - 待审核 1-审核通过 */
	private int status = 0;

	private String applyDate;

	private String telephone;

	private String email;

	/** 佣金比例，百分比 */
	private int commission = 10;
	
	/** 分销客佣金比例，百分比 */
	private int personCommission = 2;

	/** 分销商微信公众号账号相关设置 */
	private String appid;

	private String appsecret;

	/** 商城有效期 */
	private String wxmallexpire;
	
	/** 微信原始ID，用来区分不同的分销商 */
	private String wxID;
	
	//城市
	private String city;
	
	//----------分销客相关设置-------------------
	/** 分享时的图片 */
	private String shareLogo;
	
	/** 分享时标题 */
	private String shareTitle;
	
	/** 分享时的描述 */
	private String shareDesc;
	
	/** 提示关注的url */
	private String attHintUrl;
	
	
	//微商城首页设置
	private String wxMallIndexBanner;
	//首页商家logo
	private String wxMallLogo;
	
	/** 红包首页的背景 */
	private String hongbaoPic;
	
	
	/** 微商城首页显示方式 */
	private int indexShow = 0;
	
	/** 商户号 */
	private String mchId;
	
	/** 支付秘钥 */
	private String payKey;
	
	/** 运费，单位：分 , 默认10元*/
	private Integer deleverFee = new Integer(1000);
	/** 包邮设置，单位：分 , 默认100元*/
	private Integer baoyou = new Integer(10000);
	
	/** 模板消息ID */
	private String templateMsgId;
	
	/** 首次关注文本设置 */
	private String welcomeMsg = "";
	
	//------------ 2015-08-14新增多客服相关 -------------
	/** 默认多客服账号 */
	private String kfAccount;
	
	// 非持久化
	private String level;

	public String getLevel() {
		if (code.length() == 3) {
			return "总经销";
		} else if (code.length() == 6) {
			return "一级经销";
		} else if (code.length() == 9) {
			return "二级经销";
		} else {
			return "三级经销";
		}
	}

	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	 * @hibernate.many-to-one class="model.bo.auth.Org" column="pid" not-null="false"
	 */
	public Org getParent() {
		return parent;
	}

	public void setParent(Org parent) {
		this.parent = parent;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
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
	 * @hibernate.many-to-one class="com.wfsc.common.bo.user.Admin" column="userId" not-null="false"
	 */
	public Admin getCharger() {
		return charger;
	}

	public void setCharger(Admin charger) {
		this.charger = charger;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	@Override
	public int compareTo(Org o) {
		return o.getTotalScore() - this.totalScore; // 分数高的排前面
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
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
	public int getCommission() {
		return commission;
	}

	public void setCommission(int commission) {
		this.commission = commission;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getWxmallexpire() {
		return wxmallexpire;
	}

	public void setWxmallexpire(String wxmallexpire) {
		this.wxmallexpire = wxmallexpire;
	}


	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getWxID() {
		return wxID;
	}

	
	public void setWxID(String wxID) {
		this.wxID = wxID;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getPersonCommission() {
		return personCommission;
	}

	
	public void setPersonCommission(int personCommission) {
		this.personCommission = personCommission;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getShareLogo() {
		return shareLogo;
	}

	
	public void setShareLogo(String shareLogo) {
		this.shareLogo = shareLogo;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getShareTitle() {
		return shareTitle;
	}

	
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getShareDesc() {
		return shareDesc;
	}

	
	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getWxMallIndexBanner() {
		return wxMallIndexBanner;
	}

	
	public void setWxMallIndexBanner(String wxMallIndexBanner) {
		this.wxMallIndexBanner = wxMallIndexBanner;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getWxMallLogo() {
		return wxMallLogo;
	}

	
	public void setWxMallLogo(String wxMallLogo) {
		this.wxMallLogo = wxMallLogo;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getMchId() {
		return mchId;
	}

	
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPayKey() {
		return payKey;
	}

	
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getIndexShow() {
		return indexShow;
	}

	
	public void setIndexShow(int indexShow) {
		this.indexShow = indexShow;
	}

	
	public Integer getDeleverFee() {
		return deleverFee;
	}


	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public void setDeleverFee(Integer deleverFee) {
		this.deleverFee = deleverFee;
	}

	
	public String getKfAccount() {
		return kfAccount;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	
	public String getAttHintUrl() {
		return attHintUrl;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public void setAttHintUrl(String attHintUrl) {
		this.attHintUrl = attHintUrl;
	}

	
	public Integer getBaoyou() {
		return baoyou;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public void setBaoyou(Integer baoyou) {
		this.baoyou = baoyou;
	}

	
	public String getWelcomeMsg() {
		return welcomeMsg;
	}

	/**
	 * 首次关注反馈消息
	 * @hibernate.property type="string"
	 * @return
	 */
	public void setWelcomeMsg(String welcomeMsg) {
		this.welcomeMsg = welcomeMsg;
	}

	/**
	 * 首次关注反馈消息
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTemplateMsgId() {
		return templateMsgId;
	}

	/**  */
	public void setTemplateMsgId(String templateMsgId) {
		this.templateMsgId = templateMsgId;
	}

	/**
	 * 红包背景图片
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getHongbaoPic() {
		return hongbaoPic;
	}

	
	public void setHongbaoPic(String hongbaoPic) {
		this.hongbaoPic = hongbaoPic;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCity() {
		return city;
	}

	
	public void setCity(String city) {
		this.city = city;
	}
}
