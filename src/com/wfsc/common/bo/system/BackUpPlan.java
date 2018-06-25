package com.wfsc.common.bo.system;

/**
 * 系统备份计划
 * 
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wfsc_backupplan" lazy="false"
 */
public class BackUpPlan implements java.io.Serializable {

	private static final long serialVersionUID = -1697811778492601735L;

	// 数据库id
	private Long id;

	// 备份具体时间，为几点的int数据格式
	private int backUpTime = 24;

	// 备份文件的存放位置
	private String storeURL;

	// 备份文件保存份数
	private int storeFileNum = 1;
	// 是否启用备份 1:表示备份 0：表示不备份
	private int startBackUp = 1;

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
	 */
	public int getBackUpTime() {
		return backUpTime;
	}

	public void setBackUpTime(int backUpTime) {
		this.backUpTime = backUpTime;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getStoreURL() {
		return storeURL;
	}

	public void setStoreURL(String storeURL) {
		this.storeURL = storeURL;
	}


	/**
	 * @hibernate.property type="int"
	 */
	public int getStoreFileNum() {
		return storeFileNum;
	}

	public void setStoreFileNum(int storeFileNum) {
		this.storeFileNum = storeFileNum;
	}
	/**
	 * @hibernate.property type="int"
	 */
	public int getStartBackUp() {
		return startBackUp;
	}

	public void setStartBackUp(int startBackUp) {
		this.startBackUp = startBackUp;
	}
}
