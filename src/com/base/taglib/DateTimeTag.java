/******************************************************************************** 
 * Create Author   : Administrator
 * Create Date     : Oct 22, 2009
 * File Name       : DateTimeTag.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.base.taglib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.wfsc.util.SysUtil;

public class DateTimeTag extends TagSupport {
	/**
	 * 日期格式化Tag
	 */
	private static final long serialVersionUID = 2188932596795770535L;

	private String format;
	
	private String  value;

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
	    try{
	    	if("0".equals(value) || "".equals(value)){//第一次登陆,就显示当前时间.
	            out.write("  无.");
	    	}else if(!SysUtil.isInt(value)){
	    		out.write(value);
	    	}else{
		    	if(null == format)
					format = "yyyy-MM-dd HH:mm:ss";
		    	SimpleDateFormat formatter = new SimpleDateFormat(format);
		    	Long time = Long.parseLong(value);
	            Date date = new Date(time);
	            out.write(formatter.format(date));
	    	}
	    }catch(IOException e){
	    	e.printStackTrace();
	    }
		return EVAL_PAGE;
	}
	
	public String getFormat() {
		return format;
	}

	
	public void setFormat(String format) {
		this.format = format;
	}

	
	public String getValue() {
		return value;
	}

	
	public void setValue(String value) {
		this.value = value;
		
		try {
			Object obj = ExpressionEvaluatorManager.evaluate("value", value,
					String.class, pageContext);
			this.value = String.valueOf(obj);
		} catch (JspException e) {
			this.value = "0";
		} 
	}
	
	
}

