package model.bo;

/**
 * 自动回复规则表，关键字回复
 * 
 * @author jacky
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wxrules" lazy="false"
 */
public class WxRules implements java.io.Serializable {

	private static final long serialVersionUID = -7092673204552092242L;

	public static final String NEWS = "news";

	public static final String MULTI = "multi";
	
	public static final String TEXT = "text";

	private Long id;

	/** 所属分销商 code */
	private String fxCode;
	
	/** 规则名 */
	private String ruleName;

	/** 可能命中的关键字范围，逗号间隔 */
	private String kw;

	/** 相应方式: news - 回复图文消息 text - 回复文本消息 */
	private String respType;

	/** 回复的内容，根据respType存放的内容不一样 */
	private String respContent;

	// ------------ 以下属性，单图文消息可用 --------------
	private String title;

	private String twdesc;

	private String picUrl;

	/** 图文的链接地址 */
	private String twUrl;

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
	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getRespType() {
		return respType;
	}

	public void setRespType(String respType) {
		this.respType = respType;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getRespContent() {
		return respContent;
	}

	public void setRespContent(String respContent) {
		this.respContent = respContent;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTwdesc() {
		return twdesc;
	}

	public void setTwdesc(String twdesc) {
		this.twdesc = twdesc;
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
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTwUrl() {
		return twUrl;
	}

	public void setTwUrl(String twUrl) {
		this.twUrl = twUrl;
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
}
