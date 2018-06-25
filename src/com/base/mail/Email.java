/******************************************************************************** 
 * Create Author   : Andy Cui
 * Create Date     : Oct 14, 2009
 * File Name       : Email.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.base.mail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 代表邮件对象，其仅仅是一个封装，并不是将它自身作为邮件正文发送出去，而是将其封装的对象作为邮件内容发送。
 * <p>
 * 在APEX-OSSWorks系统中，工单变化后需要发送邮件，新的知识库条目被加入需要发送邮件，新的公告产生需要发送邮件， 因此封装的数据有相应的3种类型。
 * 
 * @author Andy Cui
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
public class Email {

	/**
	 * 可以是工单号，也可以是公告号，也可以是知识库记录号。
	 */
	private String code;

	/**
	 * 动作名称，表示引起工作流流程状态发生变化的动作，见JPDL定义文件
	 * <p>
	 * 如果是编辑修改工单数据发送邮件，则action为"编辑"
	 * <p>
	 * 如果是新的知识库发布，则填入"新的知识库记录被加入"
	 * <p>
	 * 如果是新的公告发布，则填入"新公告"
	 */
	private String action;

	/**
	 * 附加的邮件接收者列表 因为流程驱动时，邮件的接收者都一样，但也有些驱动如变更实施时，需要给另外的人员 发送邮件
	 */
	private List<String> additionalAddresses = new ArrayList<String>();

	/**
	 * 参数
	 */
	private String param;

	/**
	 * 构造一个待发送的邮件对象
	 * 
	 * @param code
	 * @param action
	 */
	public Email(String code, String action) {
		if (StringUtils.isBlank(code)) {
			throw new IllegalArgumentException("code is illegal.");
		}
		if (StringUtils.isBlank(action)) {
			throw new IllegalArgumentException("action is illegal.");
		}
		this.code = code;
		this.action = action;
	}
	/**
	 * 构造一个待发送的邮件对象
	 * 
	 * @param code
	 * @param action
	 */
	public Email(String code, String action, List<String> additionalAddresses) {
		if (StringUtils.isBlank(code)) {
			throw new IllegalArgumentException("code is illegal.");
		}
		if (StringUtils.isBlank(action)) {
			throw new IllegalArgumentException("action is illegal.");
		}
		this.code = code;
		this.action = action;
		this.additionalAddresses = additionalAddresses;
	}

	/**
	 * 构造一个待发送的邮件对象
	 * 
	 * @param code
	 * @param action
	 * @param additionalAddresses
	 * @param - 有时候需要传递一些参数
	 */
	public Email(String code, String action, List<String> additionalAddresses, String param) {
		if (StringUtils.isBlank(code)) {
			throw new IllegalArgumentException("code is illegal.");
		}
		if (StringUtils.isBlank(action)) {
			throw new IllegalArgumentException("action is illegal.");
		}
		this.code = code;
		this.action = action;
		this.param = param;
		this.additionalAddresses = additionalAddresses;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	public List<String> getAdditionalAddresses() {
		return additionalAddresses;
	}

	public void setAdditionalAddresses(List<String> additionalAddresses) {
		this.additionalAddresses = additionalAddresses;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}