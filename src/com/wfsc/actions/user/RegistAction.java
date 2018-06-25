package com.wfsc.actions.user;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.exception.CupidRuntimeException;
import com.constants.CupidStrutsConstants;
import com.wfsc.actions.common.CupidBaseAction;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;

@SuppressWarnings("unchecked")
@Controller("RegistAction")
@Scope("prototype")
public class RegistAction extends CupidBaseAction {

	private static final long serialVersionUID = 6576495751771379117L;

	@Resource(name = "userService")
	private IUserService userService;
	
	private User user;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 到注册页面 
	 * @return
	 */
	public String gotoreg(){
		return "gotoreg";
	}
	
	/**
	 * 激活账号 
	 * @return
	 */
	public String active(){
		String email = request.getParameter("userName");
		String activeCode = request.getParameter("activeCode");
		
		try{
			userService.activeUser(email, activeCode);
			request.setAttribute("msg", "恭喜您，激活成功");
			request.setAttribute("flag", "success");
		}catch(CupidRuntimeException ex){
			request.setAttribute("msg", ex.getMessage());
			request.setAttribute("flag", "failed");
		}
		request.setAttribute("email", email);
		return SUCCESS;
	}
	
	/**
	 * 会员注册 
	 * @return
	 */
	public String regist() {
		if(user == null)
			return "gotoreg";
		//检查验证码
		String imgCode = request.getParameter("verifyCode");
		//系统生成的验证码
		String sysImgCode = (String) request.getSession().getAttribute(CupidStrutsConstants.CODE_IMAGE_REGIST);
		request.setAttribute("email", user.getEmail());
		request.setAttribute("nickName", user.getNickName());
		if(!StringUtils.equalsIgnoreCase(imgCode, sysImgCode)){
			request.setAttribute("vcodemsg", "对不起,请输入正确的验证码.");
			return "gotoreg";
		}
		try {
			userService.registUser(user);
			request.setAttribute("flag", "active");
			return SUCCESS;
		} catch (CupidRuntimeException e) {
			if(StringUtils.equals(e.getMessage(), "emailExists")){
				request.setAttribute("emailMsg", "对不起,该邮箱已被注册");
			}
			if(StringUtils.equals(e.getMessage(), "nickexists")){
				request.setAttribute("nickMsg", "对不起,该昵称已被使用");
			}
			return "gotoreg";
		}
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	
}
