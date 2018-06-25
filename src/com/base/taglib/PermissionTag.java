/******************************************************************************** 
 * Create Author   : Administrator
 * Create Date     : Nov 19, 2009
 * File Name       : PermissionTag.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.base.taglib;

import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.constants.CupidStrutsConstants;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.Role;

/**
 * //modify by wjdeng at 2010/06/30 增加多个权限并集处理
	(用法,如:
		<oss:permission permissionId="<%=PermissionId.DELETE_INC_ISSUE+"||"+PermissionId.DELETE_PRO_ISSUE%>">
	如要求有其它表达式运算 再写方法完善
 *
 * @author Jacky.Wang
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
public class PermissionTag extends TagSupport{
	private static final long serialVersionUID = -7620007732524832615L;
	private String permissionId;

	@Override
	public int doStartTag() throws JspException {
		if (!checkPermission()) {
			return Tag.SKIP_BODY;
		} else {
			return Tag.EVAL_BODY_INCLUDE;
		}
	}
	protected boolean checkPermission(){
		Admin admin = (Admin) this.pageContext.getSession().getAttribute(CupidStrutsConstants.SESSION_ADMIN);
		//如果是超级管理员，直接返回true
		Set<Role> roles = admin.getRoles();
		for (Role role : roles) {
			if(role.getId() == 1){
				return true;
			}
		}
		
		if(admin != null) {
			//modify by wjdeng at 2010/06/30 增加多个权限并集处理
			//(用法,如:PemissionFilter(pemissionIds=PermissionId.DELETE_INC_ISSUE+"||"+PermissionId.DELETE_PRO_ISSUE))
			//如要求有其它表达式运算 再写方法完善
			String[] pas = StringUtils.split(getPermissionId(),"||");
			boolean tf = false;
			for(String pemt :pas){
				tf= tf || admin.hasPermission(pemt);
			}
			return tf;
		} else {
			return false;
		}
	}
	public String getPermissionId() {
		return permissionId;
	}

	
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
		try {
			Object obj = ExpressionEvaluatorManager.evaluate("permissionId", permissionId,
					String.class, pageContext);
			this.permissionId = String.valueOf(obj);
		} catch (JspException e) {
			this.permissionId = "";
		} 
	}
}

