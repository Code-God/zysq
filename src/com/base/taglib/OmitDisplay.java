/******************************************************************************** 
 * Create Author   : LevyLiu
 * Create Date     : Jan 30, 2010
 * File Name       : OmitDisplay.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.base.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.wfsc.util.SysUtil;

public class OmitDisplay extends TagSupport{
	private static final long serialVersionUID = -4863062209046896930L;
	private String content;
	private String length;
	
	private String local="N";//省略位置Y为考前省略,N靠后省略
	
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		StringBuffer sb = new StringBuffer("");
		int len = 30;
		if(NumberUtils.isDigits(length)){
			if(Integer.parseInt(length)>0){
				len = Integer.parseInt(length);
			} else {
				len = content.length();
			}
		}
		String dispalyContent="";
		content = StringUtils.trimToEmpty(content);
		if(content != null){
			content = content.trim();
		}
		if(!"Y".equals(local)){
			if(content.length()>len){
				dispalyContent = content.substring(0, len)+"...";
			}else{
				dispalyContent = content;
			}
		}else{
			if(content.length()>(len+1)){
				dispalyContent = "..."+content.substring(content.length()-len);
			}else{
				dispalyContent = content;
			}
		}
		String newContent=SysUtil.TextToHtml(dispalyContent);
		sb.append("<span title=\"");
		//如截取显示，则加上title提示
		if(!dispalyContent.equals(content)&&null !=content ){
			sb.append(SysUtil.TextToHtmlTitle(content));
		}
		sb.append("\" style=\"display:inline;\">");
		sb.append(newContent);
		sb.append("</span>");
		try {
			out.write(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	public void setContent(String content) {
		try {
			Object obj = ExpressionEvaluatorManager.evaluate("content", content, String.class, pageContext);
			this.content = String.valueOf(obj);
		} catch (JspException e) {
			this.content = "";
		} 
	}
	
	public void setLength(String length) {
		try {
			Object obj = ExpressionEvaluatorManager.evaluate("length", length, String.class, pageContext);
			this.length = String.valueOf(obj);
		} catch (JspException e) {
			this.length = "30";
		} 
	}
}

