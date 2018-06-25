package com.wfsc.common.bo.system;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_syslog" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class SystemLog implements Serializable {

	private static final long serialVersionUID = 6684848744882311343L;
	
	public final static String MODULE_PRODUCT = "商品管理";
	
	public final static String MODULE_PRODUCT_CAT = "分类管理";
	
	public final static String MODULE_RECOMMEND = "特惠新品";
	
	public final static String MODULE_ADV = "广告维护";
	
	public final static String MODULE_COMMENT = "评价管理";
	
	public final static String MODULE_ORDER = "订单管理";
	
	public final static String MODULE_ACCOUNT = "会员管理";
	
	public final static String MODULE_PERMISSION = "权限管理";
	
	public final static String MODULE_AREA = "区域管理";
	
	public final static String MODULE_LOGIN = "后台登录";
	
	public final static String MODULE_SYSTEM = "系统管理";
	
	private long id;
	
	/**
	 * 模块
	 */
	private String module;
	
	/**
	 * 操作人
	 */
	private String operater;
	
	/**
	 * 操作时间
	 */
	private Date opDate;
	
	/**
	 * 操作描述
	 */
	private String opdesc;
	
	public SystemLog(){}
	
	public SystemLog(String module, String operater, String opdesc){
		this.module = module;
		this.operater = operater;
		this.opDate = new Date();
		this.opdesc = opdesc;
	}
	
	public SystemLog(String module, String operater, Date opDate, String opdesc){
		this.module = module;
		this.operater = operater;
		this.opDate = opDate;
		this.opdesc = opdesc;
	}

	/**
	 * @return
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return
	 * @hibernate.property type="string" column="opmodule"
	 */
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="thedate"
	 */
	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getOpdesc() {
		return opdesc;
	}

	public void setOpdesc(String opdesc) {
		this.opdesc = opdesc;
	}

}
