package com.wfsc.actions.admin;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.wfsc.services.account.IUserService;

/**
 * 统计ACTION
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("ChartAction")
@Scope("prototype")
public class ChartAction extends DispatchPagerAction {

	private static final long serialVersionUID = -89259246539187503L;

	@Resource(name = "userService")
	private IUserService userService;

	
	//-------------------------  统计 0---------------------------

	public IUserService getUserService() {
		return userService;
	}

	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	

}
