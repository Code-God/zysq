package com.wfsc.actions.user;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.exception.CupidRuntimeException;
import com.constants.CupidStrutsConstants;
import com.wfsc.actions.common.CupidBaseAction;
import com.wfsc.annotation.Login;
import com.wfsc.common.bo.shopcart.ShopCart;
import com.wfsc.common.bo.system.City;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;
import com.wfsc.services.city.ICityService;
import com.wfsc.services.shopcart.IShopCartService;

@SuppressWarnings("unchecked")
@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends CupidBaseAction {

	private static final long serialVersionUID = 3396416499173016001L;
	
	private String userName;
	
	private String password;
	
	private String codeImage;
	
	private String msg;
	
	@Resource(name = "userService")
	private IUserService userService;
	@Resource(name = "shopCartService")
	private IShopCartService shopCartService;
	
	@Autowired
	private ICityService cityService;
	
	/**
	 * 进入登录界面 如果用户已经登录，则直接跳转到首页
	 * @return
	 */
	public String goLogin(){
		User currentUser = this.getCurrentUser();
		if(currentUser != null){
			return "index";
		}
		return SUCCESS;
	}
	
	/**
	 * 用户登录，登录成功后，将根据用户上次访问的地址，自动跳转到对应的地址，如果登录失败，继续停留在登录页面
	 * @return
	 */
	public String login(){
		if(StringUtils.isBlank(userName)){
			request.setAttribute("msg", "请输入用户名");
			request.setAttribute("userName", userName);
			return ERROR;
		}
		if(StringUtils.isEmpty(password)){
			request.setAttribute("msg", "请输入密码");
			request.setAttribute("userName", userName);
			return ERROR;
		}
		
		if(StringUtils.isEmpty(codeImage)){
			request.setAttribute("msg", "请输入验证码");
			request.setAttribute("userName", userName);
			return ERROR;
		}
		
		String currCode = (String) session.get(CupidStrutsConstants.CODE_IMAGE_LOGIN);
		if(!StringUtils.equalsIgnoreCase(currCode, codeImage)){
			request.setAttribute("msg", "验证码错误");
			request.setAttribute("userName", userName);
			return ERROR;
		}
		
		User user = null;
		try{
			user = userService.login(userName, password, request);
		}catch(CupidRuntimeException ex){
			request.setAttribute("msg", ex.getMessage());
		}
		
		if(user != null){
			session.put(CupidStrutsConstants.SESSION_USER, user);
		}else{
			return ERROR;
		}
		
		//加载上次用户使用的地区信息
		if(user.getCityCode() != null && user.getCityCode() != 0){
			int cityCode = user.getCityCode();
			City city = cityService.getCityByCode(cityCode);
			session.put(CupidStrutsConstants.CURR_CITY, city);
		}else{
			City city = (City) session.get(CupidStrutsConstants.CURR_CITY);
			if(city != null){
				user.setCityCode(city.getCode());
				userService.updateUser(user);
			}
		}
		
		// load shopping info
		List<ShopCart> list = shopCartService.list(user.getId());
		session.put(CupidStrutsConstants.SESSION_SHOP, list);
		
		//登录成功后，直接跳转到我的吴方
		try {
			response.sendRedirect(request.getContextPath() + "/private/account_userInfo.Q");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 退出登录，退出登录后，跳转到首页
	 * @return
	 */
	@Login
	public String logout(){
		User currentUser = this.getCurrentUser();
		if(currentUser != null){
			session.remove(CupidStrutsConstants.SESSION_USER);
		}
		return SUCCESS;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCodeImage() {
		return codeImage;
	}

	public void setCodeImage(String codeImage) {
		this.codeImage = codeImage;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
