package com.wfsc.actions.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.food.ConfigParam;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.intf.AdminService;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.wfsc.common.bo.system.EmailServerConfig;
import com.wfsc.common.bo.system.SeoConfig;
import com.wfsc.services.system.IEmailService;
import com.wfsc.services.system.ISeoConfigService;

@Controller("SystemAction")
@Scope("prototype")
public class SystemAction extends DispatchPagerAction {
	
	private static final long serialVersionUID = -9178287533525680929L;

	@Autowired
	private ISeoConfigService seoConfigService;
	
	@Autowired
	private IEmailService emailService;
	
	@Autowired
	private AdminService adminService;
	
	private SeoConfig seoConfig;
	
	private EmailServerConfig emailConfig;
	
	public String preSeo(){
		SeoConfig seoConf = seoConfigService.getSeoConfig();
		request.setAttribute("seoConfig", seoConf);
		return "seoConfig";
	}
	
	public String saveSeo(){
		seoConfigService.saveSeoConfig(seoConfig);
		return "seo";
	}
	
	public String preMail(){
		EmailServerConfig mailConfig = emailService.getEmailServerConfig();
		request.setAttribute("mailConfig", mailConfig);
		return "mailConfig";
	}
	
	public String saveMail(){
		emailConfig.setFromAddress(emailConfig.getUserName());
		emailService.saveEmailServerConfig(emailConfig);
		request.setAttribute("success", 1);
		return "mail";
	}
	
	public void testMail() throws IOException{
		emailConfig.setFromAddress(emailConfig.getUserName());
		boolean result = emailService.testEmailServer(emailConfig);
		JSONObject json = new JSONObject();
		json.put("result", result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		response.getWriter().flush();
	}
	
	/**
	 * 参数设置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String paramConfig() throws IOException {
		List<ConfigParam> list = adminService.getConfigParams();
		request.setAttribute("list", list);
		return "sysconfig";
	}
	/**
	 * 更新参数设置, ajax调用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String updateParam() throws IOException {
		JSONObject json = new JSONObject();
		response.setCharacterEncoding("UTF-8");
		try{
			//要更新的key
			String updateKey = request.getParameter("key");
			//要更新的值
			String updateVal = request.getParameter("cvalue");
			
			Map<String,String> map = new HashMap<String, String>();
			map.put(updateKey, updateVal);
			adminService.updateConfigParam(map);
			json.put("result", "ok");
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		}catch(Exception e){
			e.printStackTrace();
			json.put("result", "fail");
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		}
		return null;
	}
	

	public SeoConfig getSeoConfig() {
		return seoConfig;
	}

	public void setSeoConfig(SeoConfig seoConfig) {
		this.seoConfig = seoConfig;
	}

	public EmailServerConfig getEmailConfig() {
		return emailConfig;
	}

	public void setEmailConfig(EmailServerConfig emailConfig) {
		this.emailConfig = emailConfig;
	}

	
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	
}
