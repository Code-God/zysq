package model.vo;

import java.io.Serializable;

/**
 * ajax获取组织机构树结构的数据对象 { "id" : "ajson1", "parent" : "#", "text" : "IBM" }, { "id" : "ajson2", "parent" : "#", "text" : "联想" }, {
 * "id" : "ajson3", "parent" : "ajson2", "text" : "华东区" },
 * 
 * @author jacky
 * @version 1.0
 */
public class OrgObj implements Serializable {

	private static final long serialVersionUID = 3730694594090403311L;

	private Long id;

	private String parent;
	//上级分销商名称
	private String parentName;

	private String text;
	
	private String orgCode;
	
	private String orgname;
	private String telephone;
	private String applyDate;
	//佣金比例
	private int commission;
	
	//菜单树对象用到的属性
	private String key;
	private String url;
	private String mtype;
	//分类对应的图片
	private String picUrl;
	
	
	/** 节点的状态： { 'opened' : true, 'selected' : true } */
	private State state = new State(true);

	public OrgObj() {
	}

	public OrgObj(Long id, String parent, String text) {
		this.id = id;
		this.parent = parent;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	
	public String getOrgCode() {
		return orgCode;
	}

	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	public String getOrgname() {
		return orgname;
	}

	
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	
	public String getTelephone() {
		return telephone;
	}

	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	
	public String getApplyDate() {
		return applyDate;
	}

	
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	
	public String getParentName() {
		return parentName;
	}

	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	
	public int getCommission() {
		return commission;
	}

	
	public void setCommission(int commission) {
		this.commission = commission;
	}

	
	public String getKey() {
		return key;
	}

	
	public void setKey(String key) {
		this.key = key;
	}

	
	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}

	
	public String getMtype() {
		return mtype;
	}

	
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	
	public String getPicUrl() {
		return picUrl;
	}

	
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
