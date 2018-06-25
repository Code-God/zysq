package model.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 调查问卷表
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="vote" lazy="false"
 */
public class Vote implements Serializable {

	private static final long serialVersionUID = 2542433639029222498L;

	private Long id;
	/** 调研标题 */
	private String vtitle;
	
	/** 调研正文描述 */
	private String vdesc;
	
	private String vdescshort;

	/** 发布日期 */
	private String publishdate;
	
	/** 问卷状态：1-启用， 0-关闭 */
	private int vstatus = 1;
	
	private String vstatusStr;
	/** 该问卷里的题目 */
	private List<VoteItem> items = new ArrayList<VoteItem>();
	
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
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getVstatus() {
		return vstatus;
	}

	
	public void setVstatus(int vstatus) {
		this.vstatus = vstatus;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getVtitle() {
		return vtitle;
	}

	
	public void setVtitle(String vtitle) {
		this.vtitle = vtitle;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getVdesc() {
		return vdesc;
	}

	
	public void setVdesc(String vdesc) {
		this.vdesc = vdesc;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPublishdate() {
		return publishdate;
	}

	
	public void setPublishdate(String publishdate) {
		this.publishdate = publishdate;
	}

	
	/**
	 * @return the vdescshort
	 */
	public String getVdescshort() {
		if(this.vdesc.length() > 30){
			return this.vdesc.substring(0, 30) + "...";
		}
		return this.vdesc;
	}

	
	/**
	 * @return the vstatusStr
	 */
	public String getVstatusStr() {
		if(this.vstatus == 0){
			return "<font color=gray>关闭</font>";
		}else{
			return "<font color=green>已发布</font>";
		}
	}

	
	/**
	 * @return the items
	 */
	public List<VoteItem> getItems() {
		return items;
	}

	
	/**
	 * @param items the items to set
	 */
	public void setItems(List<VoteItem> items) {
		this.items = items;
	}

 
}
