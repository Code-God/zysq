package model.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 投票
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="toupiao" lazy="false"
 */
public class TP implements Serializable {

	private Long id;

	/** 标题 */
	private String ttitle;
	
	/** 投票说明 */
	private String tdesc;
	/** 单选，多选 1-单选 2-多选 */
	private int stype = 1;
	/** 多选的情况下，最多允许选几个; 默认是1个 */
	private int slimit = 1;

	/** 发布日期 */
	private String publishdate;

	/** 状态：1-启用， 0-关闭 */
	private int vstatus = 1;

	private String vstatusStr;

	/** 该投票的选项 */
	private List<TouPiaoItem> items = new ArrayList<TouPiaoItem>();

	private List<String> options = new ArrayList<String>();
	
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
	public String getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(String publishdate) {
		this.publishdate = publishdate;
	}

	/**
	 * @return the vstatusStr
	 */
	public String getVstatusStr() {
		if (this.vstatus == 0) {
			return "<font color=gray>关闭</font>";
		} else {
			return "<font color=green>已发布</font>";
		}
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTtitle() {
		return ttitle;
	}

	
	/**
	 * @param ttitle the ttitle to set
	 */
	public void setTtitle(String ttitle) {
		this.ttitle = ttitle;
	}

	
	/**
	 * @return the items
	 */
	public List<TouPiaoItem> getItems() {
		return items;
	}

	
	/**
	 * @param items the items to set
	 */
	public void setItems(List<TouPiaoItem> items) {
		this.items = items;
	}

	
	/**
	 * @return the options
	 */
	public List<String> getOptions() {
		for (TouPiaoItem ti : this.items) {
			options.add(ti.getContent() + "|" + ti.getId());
//			options.add(ti.getContent() );
		}
		return options;
	}

	
	/**
	 * @param options the options to set
	 */
	public void setOptions(List<String> options) {
		this.options = options;
	}

	
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTdesc() {
		return tdesc;
	}

	
	/**
	 * @param tdesc the tdesc to set
	 */
	public void setTdesc(String tdesc) {
		this.tdesc = tdesc;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getStype() {
		return stype;
	}

	
	/**
	 * @param type the type to set
	 */
	public void setStype(int stype) {
		this.stype = stype;
	}

	
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getSlimit() {
		return slimit;
	}

	
	/**
	 * @param limit the limit to set
	 */
	public void setSlimit(int slimit) {
		this.slimit = slimit;
	}
}
