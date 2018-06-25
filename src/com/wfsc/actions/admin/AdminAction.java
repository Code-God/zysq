package com.wfsc.actions.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import model.Paging;
import model.bo.Columns;
import model.bo.Contacts;
import model.bo.Gift;
import model.bo.GiftLog;
import model.bo.ShowCase;
import model.bo.WxRules;
import model.bo.WxRulesImage;
import model.bo.auth.Org;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.intf.AdminService;
import sun.misc.BASE64Encoder;
import actions.integ.weixin.WeiXinUtil;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.constants.CupidStrutsConstants;
import com.exttool.MarkConfig;
import com.exttool.MarkTool;
import com.wfsc.actions.vo.SearchParam;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.Role;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.security.ISecurityService;
import com.wfsc.services.system.ISystemLogService;
import com.wfsc.util.SysUtil;

/**
 * 后台管理,维护会员信息,系统信息
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("AdminAction")
@Scope("prototype")
public class AdminAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840817897299260353L;

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);

	// @Resource(name = "userService")
	// private IUserService userService;
	@Resource(name = "securityService")
	private ISecurityService securityService;

	@Resource(name = "adminService")
	private AdminService adminService;

	@Autowired
	private ISystemLogService systemLogService;

	private File[] myFile;

	private static final int BUFFER_SIZE = 16 * 1024;

	private String[] myFileContentType;

	private String[] myFileFileName;

	private String imageFileName;

	private Admin admin;

	private User user;

	/** 搜索会员的参数对象 */
	private SearchParam param;

	public Admin getAdmin() {
		return admin;
	}

	/**
	 * 到管理员登陆界面
	 * 
	 * @return
	 */
	public String gologin() {
		if (session.get(CupidStrutsConstants.SESSION_ADMIN) != null)
			return gotoAdminIndex();
		return "login";
	}

	public String gotoAdminIndex() {
		return SUCCESS;
	}

	public String login() {
		String CODE_IMAGE = this.session.get(CupidStrutsConstants.CODE_IMAGE_ADMIN) == null ? "" : this.session.get(
				CupidStrutsConstants.CODE_IMAGE_ADMIN).toString();
		String verifyCode = this.request.getParameter("verifyCode");
		if (!CODE_IMAGE.equalsIgnoreCase(verifyCode)) {
			request.setAttribute("errorMsg", "验证码错误");
			return "login";
		}
		String encrptPassword = new BASE64Encoder().encode(admin.getPassword().getBytes());
		Admin user = securityService.getUserWithPermissionByName(admin.getUsername());
		if (user != null) {
			if (user.getPassword().trim().equals(encrptPassword.trim())) {
				if (user.getStatus() == 0) {
					request.setAttribute("errorMsg", "该账号已被禁用");
					return "login";
				}
				session.put(CupidStrutsConstants.SESSION_ADMIN, user);
				session.put(CupidStrutsConstants.SESSION_ADMIN_ROLE, user.getRoleString());// 角色名
				session.put(CupidStrutsConstants.SESSION_SUPER_ADMIN, user.isSuperAdmin());// 是否是超级管理员
				if (user.isSuperAdmin()) {
					request.getSession().setAttribute("orgCode", "0");
					request.getSession().setAttribute("adminLevel", "超级管理员");
				} else if (user.isAdmin()) {//平台管理员
					// 根据admin Id去查找管辖的分销机构，通过机构代码从而确定是什么角色
					Org org = adminService.getOrgByUserId(user.getId());
					if (org != null) {
						request.getSession().setAttribute("adminOrg", org);
						request.getSession().setAttribute("orgCode", org.getCode());
					}
					request.getSession().setAttribute("adminLevel", "平台管理员");
				} else {
					// 根据admin Id去查找管辖的分销机构，通过机构代码从而确定是什么角色
					Org org = adminService.getOrgByUserId(user.getId());
					if (org != null) {
						request.getSession().setAttribute("adminOrg", org);
						request.getSession().setAttribute("orgCode", org.getCode());
						request.getSession().setAttribute("adminLevel", org.getLevel());
					} else {
						// 根据角色配置
						if (user.getRoleString().indexOf("门店管理员") != -1) {
							request.getSession().setAttribute("adminLevel", "门店");
						} else if (user.getRoleString().indexOf("代理商") != -1) {
							request.getSession().setAttribute("adminLevel", "代理商");
						} else {
							// 显示默认菜单
							request.setAttribute("errorMsg", "此用户未授权，无法登陆。");
							return "login";
						}
					}
				}
				user.setOnline(true);
				user.setLastLoginDate(new Date());
				user.setLogCount(user.getLogCount() + 1);
				securityService.updateAdminUser(user);
				SystemLog systemLog = new SystemLog(SystemLog.MODULE_LOGIN, user.getUsername(), "登录后台管理系统");
				systemLogService.saveSystemLog(systemLog);
				return SUCCESS;
			} else {
				request.setAttribute("errorMsg", "密码错误");
				return "login";
			}
		} else {
			request.setAttribute("errorMsg", "用户名错误");
			return "login";
		}
	}

	public String index() {
		return SUCCESS;
	}

	public String manager() {
		list();
		// -------这段代码仅仅是当页面作为弹出时，为了控制左侧菜单栏的。
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		// ------------
		return "adminManager";
	}

	public String list() {
		Page<Admin> page = new Page<Admin>();
		Map<String, Object> paramap = new HashMap<String, Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String username = request.getParameter("username");
		String status = request.getParameter("status");
		String rolename = request.getParameter("rolename");
		if (StringUtils.isNotEmpty(username)) {
			paramap.put("username", username);
			request.setAttribute("username", username);
		}
		if (StringUtils.isNotEmpty(status)) {
			paramap.put("status", Integer.valueOf(status));
		}
		if (StringUtils.isNotEmpty(rolename)) {
			paramap.put("rolename", rolename);
			request.setAttribute("rolename", rolename);
		}
		// 说明是弹出框里的请求，需要显示是否已经被设置为分销管理员
		if (request.getParameter("pop") != null) {
			paramap.put("pop", request.getParameter("pop"));
		}
		if (!this.getCurrentAdminUser().isSuperAdmin()) {
			// 需要把当前登录的管理员ID传递到后台，进行过滤
			paramap.put("parentAdminId", this.getCurrentAdminUser().getId());
		}
		page = securityService.findPageForAdmin(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/admin_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("adminList", page.getData());
		request.setAttribute("pop", request.getParameter("pop"));
		return "adminList";
	}

	/**
	 * 管理员退出
	 * 
	 * @return
	 */
	public String logout() {
		System.out.println("管理员退出....");
		Object uObj = session.get(CupidStrutsConstants.SESSION_ADMIN);
		if (uObj != null) {
			Admin user = (Admin) uObj;
			user.setOnline(false);
			securityService.updateAdminUser(user);
			// session.remove(CupidStrutsConstants.SESSION_ADMIN);
			// session.remove(CupidStrutsConstants.SESSION_ADMIN_ROLE);
			// session.remove(CupidStrutsConstants.SESSION_SUPER_ADMIN);
			session.clear();
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_LOGIN, user.getUsername(), "安全退出后台管理系统");
			systemLogService.saveSystemLog(systemLog);
		}
		request.setAttribute("msg", "您已经成功退出!");
		return "login";
	}

	/**
	 * 后台管理员:录入用户页面
	 * 
	 * @return
	 */
	public String inputAdmin() {
		try{
			String username = request.getParameter("username");
			//是否隐藏地址，平台管理员登陆系统并创建门店管理员时，需隐藏
			String hideAddress = "n";
			if (StringUtils.isEmpty(username)) {
				admin = new Admin();
			} else {
				admin = this.securityService.getUserWithPermissionByName(username);
			}
			Set<Role> hasRoles = admin.getRoles();
			List<Role> roles = this.securityService.getAllRoles();
			List<Role> list = new ArrayList<Role>();
			List<Role> roleList = new ArrayList<Role>();
			if (!this.getCurrentAdminUser().isSuperAdmin()) {
				// 如果不是管理员，则需要将超级管理员角色过滤掉
				for (Role r : roles) {
					if (!"超级管理员".equals(r.getRoleName())) {
						list.add(r);
					}
				}
			} else {
				list = roles;
			}
			if (this.getCurrentAdminUser().isAdmin()) {
				// 如果自己是平台管理员，则过滤掉平台管理员角色
				for (Role r : roles) {
					if (!"平台管理员".equals(r.getRoleName()) && !"超级管理员".equals(r.getRoleName())) {
						roleList.add(r);
					}
				}
			}
			
			if (this.getCurrentAdminUser().isSuperAdmin()) {
				hideAddress = "y";
				// 如果自己是超级管理员，则过滤掉超级管理员和门店管理员角色
				for (Role r : roles) {
					if (!"门店管理员".equals(r.getRoleName()) && !"超级管理员".equals(r.getRoleName())) {
						roleList.add(r);
					}
				}
			}
			
			
			//这一段是汽车平台的
//			if (this.getCurrentAdminUser().isDaili()) {
//				hideArea = "y";
//				// 过滤掉平台管理员和代理商，只能添加服务商账号
//				for (Role r : roles) {
//					if ("平台管理员".equals(r.getRoleName()) || "代理商".equals(r.getRoleName()) || "门店管理员".equals(r.getRoleName())) {
//						list.remove(r);
//					}
//				}
//			}
			for (Role role : roleList) {
				for (Role hr : hasRoles) {
					if (role.getId() == hr.getId()) {
						role.setCk("1");// 设置选中的角色
					}
				}
			}
			request.setAttribute("roles", roleList);
			request.setAttribute("hideAddress", hideAddress);
			request.setAttribute("admin", admin);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "input";
	}

	/**
	 * 一个分销商只能有一个管理员。
	 * 
	 * @return
	 */
	public String saveAdmin() {
		String roleIds = request.getParameter("roleNames");
		String[] rids = roleIds.split(",");
		Set<Role> roleset = new HashSet<Role>();
		for (String rid : rids) {
			Role role = securityService.getRoleByRoleId(rid);
			roleset.add(role);
		}
		Admin user = getCurrentAdminUser();
		if (admin.getId() == null || admin.getId() < 1) { // 新增
			admin.setPassword("MTExMTExMTE=");
			admin.setStatus(1);
			admin.setRoles(roleset);
			// 车险平台：：这种情况是代理商增加的服务商账号，因为界面上隐藏了地区属性（逻辑上应该自动继承，所以UI上不该显示）
			if (admin.getArea() == null && admin.getProvince() == null) {
				// 直接设置父级的城市和省份属性
				admin.setArea(this.getCurrentAdminUser().getArea());
				admin.setProvince(this.getCurrentAdminUser().getProvince());
			}
			if (!this.getCurrentAdminUser().isSuperAdmin()) {
				// 设置上级
				admin.setPid(this.getCurrentAdminUser().getId());
			} else {
				admin.setPid(0L);
			}
			// //新增编码
			// // 查询目前已有的管理员数量,数量+1后继续编码
			// String code = user.getLevelcode() + CommonUtil.formateCode(securityService.getAllAdmin().size() + 1, 3);
			// admin.setLevelcode(code);
			securityService.addAdminUser(admin);
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_PERMISSION, user.getUsername(), "新增管理员【"
					+ admin.getUsername() + "】");
			systemLogService.saveSystemLog(systemLog);
		} else {
			Admin a = securityService.getAdminInfo(admin.getId());
			a.setEmail(admin.getEmail());
			a.setPhone(admin.getPhone());
			a.setCompany(admin.getCompany());
			a.setRoles(roleset);
			securityService.updateAdminUser(a);
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_PERMISSION, user.getUsername(), "修改管理员【" + a.getUsername()
					+ "】");
			systemLogService.saveSystemLog(systemLog);
		}
		return "ok";
	}

	public String isExitAdmin() {
		try{
			
			String msg = "";
			String userName = request.getParameter("username");
			String city = request.getParameter("city");
			String roleId = request.getParameter("roleId");
			//
			boolean b = securityService.checkAreaCharger(city, Long.valueOf(roleId));
			
			if(!b){//地区只允许有一个管理员
				msg = "fail";
			}else{
				Admin a = securityService.getUserWithPermissionByName(userName);
				
				if (a == null) {
					msg = "ok";
				} else {
					msg = "no";
				}
			}
			
			response.getWriter().write(msg);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String delAdmin() {
		String adminIds = request.getParameter("adminIds");
		String[] ids = adminIds.split(",");
		securityService.delSelectAdmin(ids);
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PERMISSION, user.getUsername(), "删除管理员");
		systemLogService.saveSystemLog(systemLog);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String enableAccount() {
		String adminIds = request.getParameter("adminIds");
		String[] ids = adminIds.split(",");
		securityService.enableUsers(ids);
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PERMISSION, user.getUsername(), "启用管理员");
		systemLogService.saveSystemLog(systemLog);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String disableAccount() {
		String adminIds = request.getParameter("adminIds");
		String[] ids = adminIds.split(",");
		securityService.disableUsers(ids);
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PERMISSION, user.getUsername(), "禁用管理员");
		systemLogService.saveSystemLog(systemLog);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String modifyPwd() {
		// String id = request.getParameter("adminId");
		// request.setAttribute("adminId", id);
		return "modifyPwd";
	}

	/**
	 * 保存密码
	 * 
	 * @return
	 */
	public String savePwd() {
		// String username = request.getParameter("adminId");
		// Admin adminInfo = securityService.getAdminInfo(Long.valueOf(adminId));
		Admin adminInfo = (Admin) session.get(CupidStrutsConstants.SESSION_ADMIN);
		// 保存密码
		String newPwd = request.getParameter("newPwd");
		adminInfo.setPassword(SysUtil.encodeBase64(newPwd));
		securityService.updateAdminUser(adminInfo);
		return SUCCESS;
	}

	/**
	 * 后台管理员:录入用户
	 * 
	 * @return
	 */
	public String addUser() {
		return manager(); // 录入成功后,返回会员列表页.
	}

	/**
	 * 生成会员略图并使大图带上水印，大幅提高浏览速度
	 * 
	 * @return
	 */
	public String reducePic() {
		String bigUrl = this.getAbsoluteRootPath() + "/private/UploadImages";
		// String bigUrl = this.getAbsoluteRootPath() + "/private/test";
		MarkConfig config = new MarkConfig();
		config.setAlpha(0.5f);
		config.setSrcImgType("1");// 1-本地 ，2 -网络
		config.setColor("#FF69B4");
		config.setMarkText("吴方商城");// 水印文字
		// config.setFontSize(200);
		// config.setOutputImageDir("d:/test/output3");
		config.setRootPath(bigUrl);
		try {
			MarkTool.batchMarkImageByText(config);
			// 缩小图片88*100 自动在该会员目录下生成一个thunmb.jpg缩略图，
			MarkTool.reduceImage(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tool";
	}

	public String tool() {
		return "tool";
	}

	/**
	 * 查询微信菜单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String queryMenu() throws Exception {
		logger.info("菜单查询...");
		Org orgBycode = this.adminService.getOrgBycode(this.getCurrentOrgCode());
		// 先确保对接微信平台的信息都填写哈哦了
		if (orgBycode.getAppid() == null || orgBycode.getAppsecret() == null || orgBycode.getWxID() == null) {
			request.setAttribute("info", "您还没设置微信公众号的信息，请到<a href='" + request.getContextPath()
					+ "/admin/fx_fxMember.Q'>[分销商管理]</a>菜单进行设置。");
			return "info";
		}
		String token = WeiXinUtil.getToken(orgBycode.getAppid(), orgBycode.getAppsecret());
		// 查询最新的菜单
		request.setAttribute("menu", WeiXinUtil.getLastMenu(token));
		return "menu";
	}

	// 进入微信自定义菜单管理
	public String wxmenu() {
		return "xmenu";
	}

	public String keywords() {
		Org orgBycode = this.adminService.getOrgBycode(this.getCurrentOrgCode());
		// 先确保对接微信平台的信息都填写哈哦了
		if (orgBycode.getAppid() == null || orgBycode.getAppsecret() == null || orgBycode.getWxID() == null) {
			request.setAttribute("info", "您还没设置微信公众号的信息，请到<a href='" + request.getContextPath()
					+ "/admin/fx_fxMember.Q'>[分销商管理]</a>菜单进行设置。");
			return "info";
		}
		return "keywords";
	}

	public String welcome() {
		Org orgBycode = this.adminService.getOrgBycode(this.getCurrentOrgCode());
		// 先确保对接微信平台的信息都填写好了
		if (orgBycode.getAppid() == null || orgBycode.getAppsecret() == null || orgBycode.getWxID() == null) {
			request.setAttribute("info", "您还没设置微信公众号的信息，请到<a href='" + request.getContextPath()
					+ "/admin/fx_fxMember.Q'>[分销商管理]</a>菜单进行设置。");
			return "info";
		}
		return "welcome";
	}

	/**
	 * 联系我们
	 * 
	 * @return
	 */
	public String contact() {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Long orgId = this.getCurrentOrgAdmin().getId();
		request.setAttribute("orgId", orgId);
		return "contact";
	}

	/**
	 * 加载某个位置的联系方式
	 * 
	 * @return
	 * @throws IOException
	 */
	public String loadContact() throws IOException {
		response.setCharacterEncoding("UTF-8");
		String locId = request.getParameter("locId");
		Long orgId = this.getCurrentOrgAdmin().getId();
		PrintWriter out = null;
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			Contacts contacts = service.getContacts(orgId, Integer.valueOf(locId));
			out = response.getWriter();
			if (contacts != null) {
				JSONObject jo = new JSONObject();
				jo.put("msg", "ok");
				jo.put("result", contacts);
				out.write(jo.toString());
			} else {
				out.write("{\"msg\":\"null\",\"result\":\"\"}");
			}
		} catch (Exception e) {
			out.write("{\"msg\":\"fail\",\"result\":\"\"}");
		}
		return null;
	}

	/**
	 * 更新联系方式
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateContact() throws IOException {
		response.setCharacterEncoding("UTF-8");
		String locId = request.getParameter("locId");
		Long orgId = this.getCurrentOrgAdmin().getId();
		PrintWriter out = null;
		try {
			String ctitle = request.getParameter("ctitle");
			String areaCode = request.getParameter("areaCode");
			String telCode = request.getParameter("telCode");
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			Map<String, String> param = new HashMap<String, String>();
			param.put("ctitle", ctitle);
			param.put("areaCode", areaCode);
			param.put("telCode", telCode);
			param.put("locId", locId);
			boolean b = service.updateContacts(orgId, param);
			out = response.getWriter();
			if (b) {
				JSONObject jo = new JSONObject();
				jo.put("msg", "ok");
				out.write(jo.toString());
			} else {
				out.write("{\"msg\":\"fail\",\"result\":\"\"}");
			}
		} catch (Exception e) {
			out.write("{\"msg\":\"fail\",\"result\":\"\"}");
		}
		return null;
	}

	/**
	 * 获得数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public String getWelcome() throws IOException {
		response.setCharacterEncoding("UTF-8");
		Org orgBycode = this.adminService.getOrgBycode(this.getCurrentOrgCode());
		PrintWriter out = response.getWriter();
		if (orgBycode != null) {
			out.write(orgBycode.getWelcomeMsg());
		} else {
			out.write("fail");
		}
		return null;
	}

	/**
	 * 更新微信关注文本消息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateWelcome() throws IOException {
		Org orgBycode = this.adminService.getOrgBycode(this.getCurrentOrgCode());
		String msg = request.getParameter("msg");
		PrintWriter out = response.getWriter();
		try {
			orgBycode.setWelcomeMsg(msg);
			adminService.updateOrg(orgBycode);
			out.write("{\"msg\":\"ok\"}");
		} catch (Exception e1) {
			e1.printStackTrace();
			out.write("{\"msg\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 更新微信菜单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateMenu() throws Exception {
		// 第一步，获取token, APPID=wxc572b8215150b75a APPSec = 5d7a1109d4ef780c91076d632f5bc572
		// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
		// 根据当前分销商code找到对应公众号appid,appsecret，然后拿到菜单信息
		Org orgBycode = this.adminService.getOrgBycode(this.getCurrentOrgCode());
		String token = WeiXinUtil.getToken(orgBycode.getAppid(), orgBycode.getAppsecret());
		// POST菜单https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
		if (token != null) {
			String menuJson = request.getParameter("menu");
			boolean postMenu = WeiXinUtil.postMenu(menuJson, token);
			if (postMenu) {
				request.setAttribute("info", "<font color=green>菜单更新成功！</font>");
			} else {
				request.setAttribute("info", "<font color=red>菜单更新失败！</font>");
			}
			// 查询最新的菜单
			request.setAttribute("menu", WeiXinUtil.getLastMenu(token));
			return "menu";
		}
		return null;
	}

	// 发布到微信平台
	public String publish2wx() throws Exception {
		// 根据orgid获取菜单配置，再通过微信接口更新到公众号
		Org org = this.getCurrentOrgAdmin();
		boolean b = adminService.publish2Wx(org);
		if (b) {
			response.getWriter().write("{\"result\":\"ok\"}");
		} else {
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 获得单个keyword规则
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getRule() throws Exception {
		response.setCharacterEncoding("UTF-8");
		String id = request.getParameter("kid");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		WxRules rule = service.getRule(id);
		JSONObject fromObject = JSONObject.fromObject(rule);
		// 返回json数据
		response.getWriter().write(fromObject.toString());
		return null;
	}

	/**
	 * 查询参数
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> getParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String t = request.getParameter("t");
		if (StringUtils.isNotBlank(t) && t != null && !"null".equals(t)) {
			paramMap.put("t", t);
		}
		String startDate = request.getParameter("startDate");
		if (StringUtils.isNotBlank(startDate) && startDate != null && !"null".equals(startDate)) {
			paramMap.put("startDate", startDate);
		}
		String endDate = request.getParameter("endDate");
		if (StringUtils.isNotBlank(endDate) && endDate != null && !"null".equals(endDate)) {
			paramMap.put("endDate", endDate);
		}
		String stat = request.getParameter("stat");
		if (StringUtils.isNotBlank(stat) && stat != null && !"null".equals(stat)) {
			paramMap.put("stat", stat);
		}
		String keyword = request.getParameter("keyword");
		if (StringUtils.isNotBlank(keyword) && keyword != null && !"null".equals(keyword)) {
			paramMap.put("keyword", keyword);
		}
		// 所属模块
		String model = request.getParameter("model");
		if (StringUtils.isNotBlank(model) && keyword != null && !"null".equals(model)) {
			paramMap.put("model", model);
		}
		return paramMap;
	}

	/**
	 * 关键字回复
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getKeyWords() throws Exception {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 当前登录用户的权限
		Admin currentUser = this.getCurrentAdminUser();
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		// 查询参数
		Map<String, String> paramMap = getParamMap(request);
		try {
			Map<String, Object> map = service.getKeywords(start, Integer.valueOf(limit).intValue(), paramMap);
			List<WxRules> resumeList = (List<WxRules>) map.get("list");
			int total = Integer.valueOf(map.get("total").toString());
			pp.setPage(Integer.valueOf(page));// 当前页码
			pp.setLimit(Integer.valueOf(limit));// 每页显示条数
			if (!resumeList.isEmpty()) {
				pp.setTotal(total);
				// 总页数
				if (total % pp.getLimit() != 0) {
					pp.setTotalPage(total / pp.getLimit() + 1);
				} else {
					pp.setTotalPage(total / pp.getLimit());
				}
				logger.info("数据页数：" + pp.getTotalPage());
				pp.setDatas(resumeList);
			}
			out.print(JSONObject.fromObject(pp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}

	/**
	 * 更新或新增关键字设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdateKeyWords() throws Exception {
		String op = request.getParameter("op");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		if ("new".equals(op)) {// 新增
			// 保存到数据库
			boolean addKeyword = service.addKeyword(request);
			if (addKeyword) {
				request.setAttribute("info", "新增成功！");
			} else {
				request.setAttribute("info", "新增失败！");
			}
			return ("keywords");
		} else if ("edit".equals(op)) {// 修改
			// 保存到数据库
			service.updateKeyWord(request);
			return ("keywords");
		} else if ("del".equals(op)) {
			try {
				String id = request.getParameter("id");
				service.deleteRule(Long.valueOf(id));
				request.setAttribute("info", "删除成功！");
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("info", "删除失败！");
			}
			return ("keywords");
		}
		return null;
	}

	public String photo() {
		System.out.println("照片管理....");
		return SUCCESS;
	}

	/**
	 * 生成评价 输入
	 * 
	 * @return
	 */
	public String generatePjInput() {
		return "generatePj";
	}

	/**
	 * 生成评价，因为评价表关联用户表和订单表以及商品ID 所以，先生成一个固定的虚拟用户（email=99999@99999.99999）和一个虚拟订单记录， 然后自动生成的评价都与之关联即可。
	 * 当然pj表中的用户ID使用同一个，但是用户名为了表现出不同，需要从随机姓名库中生成
	 * 
	 * @return
	 * @throws IOException
	 */
	public String generatePj() throws IOException {
		String count = request.getParameter("pjCount");
		String prdId = request.getParameter("prdId");
		if (count == null) {
			count = "0";
		}
		try {
			boolean b = this.adminService.generatePj(Integer.valueOf(count), prdId);
			if (b) {
				response.getWriter().write("{\"result\":\"ok\"}");
			} else {
				response.getWriter().write("{\"result\":\"fail\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	/*
	 * public void setUserService(IUserService userService) { this.userService = userService; }
	 */
	public File[] getMyFile() {
		return myFile;
	}

	public void setMyFile(File[] myFile) {
		this.myFile = myFile;
	}

	public void setMyFileContentType(String[] contentType) {
		this.myFileContentType = contentType;
	}

	public void setMyFileFileName(String[] fileName) {
		this.myFileFileName = fileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public String[] getMyFileContentType() {
		return myFileContentType;
	}

	public String[] getMyFileFileName() {
		return myFileFileName;
	}

	public SearchParam getParam() {
		return param;
	}

	public void setParam(SearchParam param) {
		this.param = param;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 获得礼品列表
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getGifts() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		List<Gift> questionsByDate = service.getGifts();
		PrintWriter out = response.getWriter();
		if (!questionsByDate.isEmpty()) {
			out.write(JSONArray.fromObject(questionsByDate).toString());
		} else {
			out.write("{\"result\":\"empty\"}");
		}
		return null;
	}

	/**
	 * 编辑积分礼品信息
	 */
	public String editGif() throws IOException {
		// ID
		String id = request.getParameter("id");
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			Gift gift = service.getGiftById(Long.valueOf(id));
			request.setAttribute("gift", gift);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", " 错误信息：" + e.getMessage());
		}
		return "addGift";
	}

	private Gift gift = new Gift();

	public String inputGift() {
		return "addGift";
	}

	/**
	 * 新增礼品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String addGift() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("giftName", gift.getGiftName());
			param.put("giftDesc", gift.getGiftDesc());
			param.put("score", gift.getScore() + "");
			if (gift.getId() != null && gift.getId() > 0) {
				// 编辑
				param.put("id", gift.getId() + "");
				Gift giftById = service.getGiftById(gift.getId());
				gift.setGiftPic(giftById.getGiftPic());// 图片
				service.updateGift(gift);
			} else {
				// 新增
				gift = service.addGift(param);
			}
			request.setAttribute("info", "修改成功！");
			logger.info("保存积分礼品照片....");
			// 如果有照片，再保存到指定目录
			if (myFileFileName != null) {
				// 为该用户创建一个文件夹
				String userDir = ServletActionContext.getServletContext().getRealPath("/private/gifts") + gift.getId();
				File f = new File(userDir);
				if (!f.exists()) {
					f.mkdirs();
				}
				File destFile = new File(userDir + "/" + myFileFileName[0]);
				SysUtil.copy(myFile[0], destFile);
				// String fileName = gift.getId() + "." + myFileFileName[0].split("\\.")[1];
				gift.setGiftPic("/private/gifts" + gift.getId() + "/" + myFileFileName[0]);
				service.updateGift(gift);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "对不起，添加失败，错误信息：" + e.getMessage());
			return "error";
		}
		return "giftList";
	}

	/**
	 * 删除礼物
	 * 
	 * @throws IOException
	 */
	public String delGift() throws IOException {
		String ids = request.getParameter("ids");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		boolean b = service.delGift(ids);
		PrintWriter out = response.getWriter();
		if (b) {
			out.write("ok");
		} else {
			out.write("fail");
		}
		return null;
	}

	/**
	 * 删除礼物
	 * 
	 * @throws IOException
	 */
	public String delPj() throws IOException {
		String id = request.getParameter("pjId");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		boolean b = service.delPj(Long.valueOf(id));
		PrintWriter out = response.getWriter();
		if (b) {
			out.write("ok");
		} else {
			out.write("fail");
		}
		return null;
	}

	/**
	 * 兑换记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String exchangeList() throws Exception {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		// 查询参数
		Map<String, String> paramMap = getParamMap(request);
		try {
			Map<String, Object> map = service.getGiftLogs(start, Integer.valueOf(limit).intValue(), paramMap);
			List<GiftLog> list = (List<GiftLog>) map.get("list");
			int total = Integer.valueOf(map.get("total").toString());
			pp.setPage(Integer.valueOf(page));// 当前页码
			pp.setLimit(Integer.valueOf(limit));// 每页显示条数
			if (!list.isEmpty()) {
				pp.setTotal(total);
				// 总页数
				if (total % pp.getLimit() != 0) {
					pp.setTotalPage(total / pp.getLimit() + 1);
				} else {
					pp.setTotalPage(total / pp.getLimit());
				}
				logger.info("数据页数：" + pp.getTotalPage());
				pp.setDatas(list);
			}
			out.print(JSONObject.fromObject(pp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}

	/**
	 * 标记为已兑换
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String dh() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String giftId = request.getParameter("id");
		boolean b = service.dhGift(giftId);
		PrintWriter out = response.getWriter();
		if (b) {
			out.write("ok");
		} else {
			out.write("fail");
		}
		return null;
	}

	/**
	 * 栏目管理首页
	 * 
	 * @return
	 */
	public String colIndex() {
		return "colIndex";
	}

	/**
	 * 初始化栏目
	 * 
	 * @return
	 * @throws IOException
	 */
	public String initColumns() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		String model = request.getParameter("model");
		try {
			List<Columns> list = service.getAllColumns(model);
			out.write(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 添加栏目
	 * 
	 * @return
	 * @throws IOException
	 */
	public String addColumn() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String title = request.getParameter("title");
		// 所属模块， 可以用来区分不同大模块
		String model = request.getParameter("model");
		PrintWriter out = response.getWriter();
		try {
			service.addColumn(title, model);
			out.write("ok");
		} catch (Exception e) {
			out.write("fail");
		}
		return null;
	}

	/**
	 * 删除栏目
	 * 
	 * @return
	 * @throws IOException
	 */
	public String delCm() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String id = request.getParameter("id");
			if (id == null) {
				response.getWriter().write("param");
				return null;
			}
			service.delColumn(id);
			response.getWriter().write("ok");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		}
		return null;
	}

	/**
	 * 更新栏目
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateColumn() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String newtitle = request.getParameter("title");
		String id = request.getParameter("id");
		PrintWriter out = response.getWriter();
		try {
			service.updateColumn(Long.valueOf(id), newtitle);
			out.write("ok");
		} catch (Exception e) {
			out.write("fail");
		}
		return null;
	}

	/**
	 * 发布列表
	 * 
	 * @return
	 */
	public String publishList() {
		return "publishList";
	}

	/**
	 * 评价列表
	 * 
	 * @return
	 */
	public String pjList() {
		return "pjList";
	}

	public String loadPjList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 查询参数
		// Map<String, String> paramMap = getParamMap(request);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orgId", this.getCurrentOrgAdmin().getId().toString());
		try {
			Paging pp = service.getPjList(Integer.valueOf(page), Integer.valueOf(limit), paramMap);
			if (!pp.getDatas().isEmpty()) {
				JsonConfig jc = new JsonConfig();
				jc.setExcludes(new String[] { "pjer" });
				out.print(JSONObject.fromObject(pp, jc).toString());
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}

	public String getShowCase() throws Exception {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		// 查询参数
		Map<String, String> paramMap = getParamMap(request);
		try {
			Map<String, Object> map = service.getShowCases(start, Integer.valueOf(limit).intValue(), paramMap);
			List<ShowCase> resumeList = (List<ShowCase>) map.get("list");
			int total = Integer.valueOf(map.get("total").toString());
			pp.setPage(Integer.valueOf(page));// 当前页码
			pp.setLimit(Integer.valueOf(limit));// 每页显示条数
			if (!resumeList.isEmpty()) {
				pp.setTotal(total);
				// 总页数
				if (total % pp.getLimit() != 0) {
					pp.setTotalPage(total / pp.getLimit() + 1);
				} else {
					pp.setTotalPage(total / pp.getLimit());
				}
				logger.info("数据页数：" + pp.getTotalPage());
				pp.setDatas(resumeList);
			}
			out.print(JSONObject.fromObject(pp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}

	public String gotoAddCase() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 所属模块：邮政咨询还是在线学习
		String model = request.getParameter("model");
		List<Columns> cols = service.getAllColumns(model);
		request.setAttribute("cols", cols);
		// return new ActionForward("/admin/addCase.jsp?t=" + request.getParameter("t"));
		return "addCase";
	}

	/**
	 * 删除文章
	 * 
	 * @return
	 * @throws IOException
	 */
	public String delCase() throws IOException {
		String ids = request.getParameter("ids");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		boolean b = service.delCase(ids);
		PrintWriter out = response.getWriter();
		if (b) {
			out.write("ok");
		} else {
			out.write("fail");
		}
		return null;
	}

	private ShowCase shCase = new ShowCase();

	/**
	 * 发布文章
	 * 
	 * @return
	 * @throws IOException
	 */
	public String addCase() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Long id = shCase.getId();
		// 文章类型 //0-邮政咨询 1-业务学习 2-生活常识
		String t = request.getParameter("t");
		// 所属模块
		String model = request.getParameter("model");
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("title", shCase.getTitle());
			// 栏目ID
			param.put("columnId", shCase.getColumns().getId().toString());
			param.put("authorName", shCase.getAuthorName());
			param.put("content", shCase.getThecontent());
			param.put("docType", "1");
			if (id != null && id.longValue() > 0) {
				param.put("zan", shCase.getZan() + "");
				param.put("id", id + ""); // 说明是更新
			} else {
				param.put("zan", "0");
			}
			// 新增或更新
			ShowCase addCase = service.addCase(param);
			request.setAttribute("info", "修改成功！");
			
			
			// 如果有照片，再保存到指定目录
			if (myFileFileName != null) {
				// 为发现模块创建一个文件夹
				String userDir = ServletActionContext.getServletContext().getRealPath("/private/faxian/") + addCase.getId();
				File f = new File(userDir);
				if(!f.exists()){
					f.mkdirs();
				}
				File destFile = new File(userDir + "/" + myFileFileName[0]);
				SysUtil.copy(myFile[0], destFile);
//							String fileName = gift.getId() + "." + myFileFileName[0].split("\\.")[1];
				addCase.setPicUrl("/private/faxian/"+ addCase.getId() +"/" + myFileFileName[0]);
				service.updateShowCase(addCase);
				logger.info("发现模块，图片上传完毕....");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "对不起，系统错误！" + e.getMessage());
			return ("error");
		}
		return "addOk";
	}

	public Gift getGift() {
		return gift;
	}

	public void setGift(Gift gift) {
		this.gift = gift;
	}

	public ShowCase getShCase() {
		return shCase;
	}

	public void setShCase(ShowCase shCase) {
		this.shCase = shCase;
	}

	/**
	 * 编辑
	 * */
	public String editCase() throws IOException {
		// 文章类型 /** 0-病例分享 1-获奖病例 2-随查随用 3-行业纵览 */
		String t = request.getParameter("t");
		// 文章ID
		String id = request.getParameter("id");
		// 所属模块
		String model = request.getParameter("model");
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			ShowCase sc = service.getArticle(t, id);
			request.setAttribute("shCase", sc);
			List<Columns> cols = service.getAllColumns(model);
			request.setAttribute("cols", cols);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", " 错误信息：" + e.getMessage());
			return ("error");
		}
		return "addCase";
	}
	
	public String imageKeywords() {
		Org orgBycode = this.adminService.getOrgBycode(this.getCurrentOrgCode());
		// 先确保对接微信平台的信息都填写哈哦了
		if (orgBycode.getAppid() == null || orgBycode.getAppsecret() == null || orgBycode.getWxID() == null) {
			request.setAttribute("info", "您还没设置微信公众号的信息，请到<a href='" + request.getContextPath()
					+ "/admin/fx_fxMember.Q'>[分销商管理]</a>菜单进行设置。");
			return "info";
		}
		return "imageKeywords";
	}
	
	/**
	 * 图片消息回复
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getImageKeyWords() throws Exception {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 当前登录用户的权限
		Admin currentUser = this.getCurrentAdminUser();
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		// 查询参数
		Map<String, String> paramMap = getParamMap(request);
		try {
			Map<String, Object> map = service.getImageKeywords(start, Integer.valueOf(limit).intValue(), paramMap);
			List<WxRules> resumeList = (List<WxRules>) map.get("list");
			int total = Integer.valueOf(map.get("total").toString());
			pp.setPage(Integer.valueOf(page));// 当前页码
			pp.setLimit(Integer.valueOf(limit));// 每页显示条数
			if (!resumeList.isEmpty()) {
				pp.setTotal(total);
				// 总页数
				if (total % pp.getLimit() != 0) {
					pp.setTotalPage(total / pp.getLimit() + 1);
				} else {
					pp.setTotalPage(total / pp.getLimit());
				}
				logger.info("数据页数：" + pp.getTotalPage());
				pp.setDatas(resumeList);
			}
			out.print(JSONObject.fromObject(pp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}
	
	/**
	 * 更新或新增图片消息设置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdateImageKeyWords() throws Exception {
		String op = request.getParameter("op");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		if ("new".equals(op)) {// 新增
			// 保存到数据库
			boolean addKeyword = service.addImageKeyword(request);
			if (addKeyword) {
				request.setAttribute("info", "新增成功！");
			} else {
				request.setAttribute("info", "新增失败！");
			}
			return ("imageKeywords");
		} else if ("edit".equals(op)) {// 修改
			// 保存到数据库
			service.updateImageKeyWord(request);
			return ("imageKeywords");
		} else if ("del".equals(op)) {
			try {
				String id = request.getParameter("id");
				service.deleteImageRule(Long.valueOf(id));
				request.setAttribute("info", "删除成功！");
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("info", "删除失败！");
			}
			return ("imageKeywords");
		}
		return null;
	}
	
	/**
	 * 获得单个keyword规则
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getImageRule() throws Exception {
		response.setCharacterEncoding("UTF-8");
		String id = request.getParameter("kid");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		WxRulesImage rule = service.getImageRule(id);
		JSONObject fromObject = JSONObject.fromObject(rule);
		// 返回json数据
		response.getWriter().write(fromObject.toString());
		return null;
	}
}
