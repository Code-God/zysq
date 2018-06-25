package com.wfsc.common.bo.system;
/*****************************************************************************************************************************
 * COMPANY:            TEKVIEW
 * CLASS NAME:         GenOrderNum
 * CRATE BY:           JONIM
 * CREATE DATE:        2009-10-12
 * LAST UPDATE DATE:
 * LAST UPDATE BY:
 * DESCRIPTION:        BO of order generator
 * ****************************************************************************************************************************
 */

import java.io.Serializable;
/**
 * @hibernate.class dynamic-insert="true" dynamic-update="true"
 *                  table="wfsc_gen_order_num" lazy="false"
 */
public class GenOrderNum implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected Long num;
	protected String type;
	protected boolean enabledFlag;

	/**
	 * @hibernate.id column="id" generator-class="native" type="long"
	 *               unsaved-value="0"
	 */
	public Long getId() {
		return this.id;
	}

	
	public void setId(Long aValue) {
		this.id = aValue;
	}

	/**
	 * @hibernate.property type="long" column="genNum"
	 */
	public Long getNum() {

		return this.num;
	}


	public void setNum(Long aValue) {
		this.num = aValue;
	}

	/**
	 * @hibernate.property type="string" column="genType"
	 */
	public String getType() {

		return this.type;
	}

	public void setType(String aValue) {
		this.type = aValue;
	}

	/**
	 * @hibernate.property type="boolean" column="enabledFlag"
	 */
	public boolean getEnabledFlag() {

		return this.enabledFlag;
	}

	public void setEnabledFlag(boolean aValue) {
		this.enabledFlag = aValue;
	}

}
