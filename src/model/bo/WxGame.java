package model.bo;

/**
 * 微信用户表
 * 
 * @author jacky
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wx_game" lazy="false"
 */
public class WxGame implements java.io.Serializable {

	private static final long serialVersionUID = 4523443539208746958L;

	private Long id;

	/** 微信用户的openId */
	private String openId;

	/** 转发的页面地址 */
	private String page;

	/** 该页面访问量 */
	private int visit;
	
	private String sesId;

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
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getVisit() {
		return visit;
	}

	public void setVisit(int visit) {
		this.visit = visit;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getSesId() {
		return sesId;
	}

	
	public void setSesId(String sesId) {
		this.sesId = sesId;
	}
}
