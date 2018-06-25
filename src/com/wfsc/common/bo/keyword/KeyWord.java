package com.wfsc.common.bo.keyword;

import java.io.Serializable;
import java.util.Date;

/**
 * 热门搜索关键字
 * 
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_keyword" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class KeyWord implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据库ID，自增长
	 */
	private long id;

	/**
	 * 关键字
	 */
	private String keyword;

	/**
	 * 被搜索次数
	 */
	private int searchCount;

	/**
	 * 最后搜索时间
	 */
	private Date activeDate;

	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return
	 */
	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}

}
