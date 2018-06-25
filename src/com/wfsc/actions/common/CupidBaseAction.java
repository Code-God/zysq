package com.wfsc.actions.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bo.auth.Org;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import service.intf.AdminService;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.base.util.Page;
import com.constants.CupidStrutsConstants;
import com.opensymphony.xwork2.ActionSupport;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.User;

import dao.OrgDao;

/**
 * 基类action,一些公共方法定义在这里
 * @author Administrator
 * @version 1.0
 * @param <T>
 * @since cupid 1.0
 */
public class CupidBaseAction<T> extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {
	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	private static final long serialVersionUID = -3286145746001535749L;
	protected String sessionID = ""; 
	
	public HttpServletRequest request;
	
	public HttpServletResponse response;
	
	public static Map<String, Object> session = new ConcurrentHashMap<String, Object>();
	
	/**
	 * 设置请求来源 
	 */
	public void setRefererUrl(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String header = request.getHeader("referer");
		if(header != null){
			request.getSession().setAttribute(CupidStrutsConstants.SES_REFERER, header);
		}
	}
	/**
	 * 从session里获得当前会员用户 
	 * @param request2 
	 * @return
	 */
	public User getCurrentUser(){
		if(request.getSession().getAttribute(CupidStrutsConstants.SESSION_USER) != null){
			logger.info("getCurrentUser---------1");
			User user = (User) request.getSession().getAttribute(CupidStrutsConstants.SESSION_USER);
			return user;
		}else{
			logger.info("getCurrentUser---------2--session.size()---" + session.size());
			if(session.get(CupidStrutsConstants.SESSION_USER) != null){
				logger.info("getCurrentUser---------3");
				return (User) session.get(CupidStrutsConstants.SESSION_USER);
			}
			return null;
		}
	}
	/**
	 * 从session里获得当前管理员用户 
	 * @return
	 */
	public Admin getCurrentAdminUser(){
		if(request.getSession().getAttribute(CupidStrutsConstants.SESSION_ADMIN) != null){
			Admin admin = (Admin) request.getSession().getAttribute(CupidStrutsConstants.SESSION_ADMIN);
			return admin;
		}else{
			return null;
		}
	}
	/**
	 * 前台：：：：获得当前分销商编码 
	 * @return
	 */
	public String getCurrentFxCode(){
		if("y".equals(Version.getInstance().getNewProperty("wxtest"))){
			return "000001";
		}
		if(request.getSession().getAttribute(CupidStrutsConstants.FXCODE) != null){
			return request.getSession().getAttribute(CupidStrutsConstants.FXCODE).toString();
		}else{
			return null;
		}
	}
	/**
	 * 后台：：：获得当前登录的分销商管理员所属分销商编码 
	 * @return
	 */
	public String getCurrentOrgCode(){
		if(request.getSession().getAttribute("orgCode") != null){
			return request.getSession().getAttribute("orgCode").toString();
		}else{
			//根据此用户的父ID来查询,因为父ID一定是平台管理员， 平台管理员属于且仅属于一个ORG
			if(this.getCurrentAdminUser().getPid() != null){
				AdminService adminService = (AdminService) ServerBeanFactory.getBean("adminService");
				Org orgByUserId = adminService.getOrgByUserId(this.getCurrentAdminUser().getPid());
				if(orgByUserId != null){
					return orgByUserId.getCode();
				}
			}
			return null;
		}
	}
	
	/**
	 * 后台：：：返回当前登录的管理员所对应的分销商对象
	 * @return
	 */
	public Org getCurrentOrgAdmin(){
		if("y".equals(Version.getInstance().getNewProperty("wxtest"))){
			OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
			Org org = dao.getEntityById(79L);
			return org;
		}
		if(request.getSession().getAttribute("adminOrg") != null){
			return (Org) request.getSession().getAttribute("adminOrg");
		}else{
			return null;
		}
	}
	
	
	/**
	 * 更新session里的user对象 
	 * @param user2
	 */
	@SuppressWarnings("unchecked")
	protected void updateSessionUser(User user) {
		session.put(CupidStrutsConstants.SESSION_USER, user);
		request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, user);
	}
	
	protected void setMsg(String msg, Object[] param){
		String str = "";
		if(param == null){
			request.setAttribute("msg", msg);
			return;
		}
		if(msg.indexOf("{0}") != -1){
			str = msg.replaceAll("{0}", param[0].toString());
		}
		if(msg.indexOf("{1}") != -1){
			str = msg.replaceAll("{1}", param[1].toString());
		}
		if(msg.indexOf("{2}") != -1){
			str = msg.replaceAll("{2}", param[2].toString());
		}
		if(msg.indexOf("{3}") != -1){
			str = msg.replaceAll("{3}", param[3].toString());
		}
		request.setAttribute("msg", str);
	}
	
	/**
	 * 支持分页的方法，设置参数 
	 * @param request
	 * @param page
	 */
	protected void setPageParams(Page<T> page){
		String pageNoStr =  (request.getParameter("currPageNo") == null) ? "1" : request.getParameter("currPageNo");
		page.setCurrPageNo(Integer.valueOf(pageNoStr));
	
		String pageSizeStr = (request.getParameter("pageSize") == null) ? "10" : request.getParameter("pageSize");
		page.setPageSize(Integer.valueOf(pageSizeStr));
	
	}
	
	/**
	 * 获取带参数的分页对象
	 * @return
	 */
	protected Page<T> getPage(){
		Page<T> page = new Page<T>();
		setPageParams(page);
		return page;
	}
	
	protected String gotoError(String msg) {
		this.setMsg(msg, null);
		return "error";
	}
	
	protected String gotoError(String msg, Object[] param) {
		this.setMsg(msg, param);
		return "error";
	}
	
	/**
	 * 保存sessionId 
	 */
	public void saveSessionId(){
		this.sessionID = ServletActionContext.getRequest().getSession().getId();
	}

	
	public String getSessionID() {
		return sessionID;
	}

	
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		 this.request = request;  
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		 this.response = response;  
	}
	
	public void setAttr(String name, Object o) {
		request.setAttribute(name, o);
	}
	
	public String getPara(String name){
		return request.getParameter(name);
	}
	
	public Integer getParaToInt(String name){
		return toInt(getPara(name), null);
	}
	
	public Long getParaToLong(String name){
		return toLong(getPara(name), null);
	}
	
	private Integer toInt(String value, Integer defaultValue) {
		if (value == null || "".equals(value.trim()))
			return defaultValue;
		if (value.startsWith("N") || value.startsWith("n"))
			return -Integer.parseInt(value.substring(1));
		return Integer.parseInt(value);
	}
	
	private Long toLong(String value, Long defaultValue) {
		if (value == null || "".equals(value.trim()))
			return defaultValue;
		if (value.startsWith("N") || value.startsWith("n"))
			return -Long.parseLong(value.substring(1));
		return Long.parseLong(value);
	}

	/**
	 * 返回JSON
	 * @param json
	 */
	public void renderJson(String json) {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

