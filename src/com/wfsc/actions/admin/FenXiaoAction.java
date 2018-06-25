package com.wfsc.actions.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.Paging;
import model.bo.auth.Org;
import model.bo.fenxiao.CashApply;
import model.bo.fenxiao.FxApplyConfig;
import model.bo.fenxiao.PrdSpec;
import model.bo.food.ConfigParam;
import model.vo.OrgObj;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.intf.AdminService;
import service.intf.IFenxiaoService;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.base.tools.Version;
import com.constants.CupidStrutsConstants;
import com.wfsc.actions.vo.YjInfo;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;
import com.wfsc.util.DateUtil;
import com.wfsc.util.SysUtil;

/**
 * 分销相关ACTION
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("FxAction")
@Scope("prototype")
public class FenXiaoAction extends DispatchPagerAction {

	private static final long serialVersionUID = 1633235637979099552L;

	private Logger logger = Logger.getLogger(AdminAction.class);
	
	
	private File[] myFile;

	private String[] myFileFileName;

	private String imageFileName;

	private Org org;
	
	@Resource(name = "userService")
	private IUserService userService;

	// ------------------------- 统计 0---------------------------
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * 后台：分销商管理
	 * @return
	 */
	public String fxMember(){
		request.setAttribute("isAdmin", this.getCurrentAdminUser().isSuperAdmin() );
		request.setAttribute("isNormalAdmin",  this.getCurrentAdminUser().isAdmin());
		if(!this.getCurrentAdminUser().isSuperAdmin() && !this.getCurrentAdminUser().isAdmin()){
			request.setAttribute("isFenXiao", true);
		}
		return "index";
	}
	/**
	 * 后台：分销客管理 
	 * @return
	 */
	public String fxPerson(){
		request.setAttribute("isAdmin", this.getCurrentAdminUser().isSuperAdmin() );
		request.setAttribute("isNormalAdmin",  this.getCurrentAdminUser().isAdmin());
		if(!this.getCurrentAdminUser().isSuperAdmin() && !this.getCurrentAdminUser().isAdmin()){
			request.setAttribute("isFenXiao", true);
		}
		return "fxperson";
	}
	/**
	 * 后台：分销佣金
	 * @return
	 */
	public String fxYj(){
		//计算当前分销商的佣金
		IFenxiaoService fxService = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
		//获得分销佣金相关信息:订单总数，  佣金比例：    总佣金
		YjInfo info =  fxService.getMyYjInfo(this.getCurrentOrgCode());
		request.setAttribute("yj", info);
		return "ok";
	}
	
	/**
	 * 后台：微商城设置
	 * 主要是微商城首页的管理
	 * @return
	 */
	public String wxMallConfig(){
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String orgCode = this.getCurrentOrgCode();
		Org orgBycode = service.getOrgBycode(orgCode);
		request.setAttribute("org", orgBycode);
		return "ok";
	}
	
	
	
	/**
	 * JSTREE 接收格式
	 * { "id" : "ajson1", "parent" : "#", "text" : "IBM" }, { "id" : "ajson2", "parent" : "#", "text" : "联想" }, { "id" :
	 * "ajson3", "parent" : "ajson2", "text" : "华东区" },
	 * 
	 * 
	 * 见： http://www.jstree.com/docs/json/
	 * <br>-------------------------------------------------------------------
	 * // Alternative format of the node (id & parent are required)
			{
			  id          : "string" // required
			  parent      : "string" // required
			  text        : "string" // node text
			  icon        : "string" // string for custom
			  state       : {
			    opened    : boolean  // is the node open
			    disabled  : boolean  // is the node disabled
			    selected  : boolean  // is the node selected
			  },
			  li_attr     : {}  // attributes for the generated LI node
			  a_attr      : {}  // attributes for the generated A node
			}
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String loadOrg() throws IOException {
		response.setCharacterEncoding("utf-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Long userId = this.getCurrentAdminUser().getId();
		if(this.getCurrentAdminUser().isSuperAdmin()){
			userId = 0L;
		}
		if(this.getCurrentAdminUser().isAdmin()){
			userId = -1L;
		}
		List<OrgObj> list = service.loadOrg(userId, 1);
		logger.info(JSONArray.fromObject(list).toString());
		response.getWriter().write(JSONArray.fromObject(list).toString());
		return null;
	}
	/**
	 * 加载菜单结构 
	 * @return
	 * @throws IOException
	 */
	public String loadMenu() throws IOException {
		response.setCharacterEncoding("utf-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Long orgId = super.getCurrentOrgAdmin().getId();
		List<OrgObj> list = service.loadMenu(orgId);
//		logger.info(JSONArray.fromObject(list).toString());
		response.getWriter().write(JSONArray.fromObject(list).toString());
		return null;
	}
	
	/**
	 * 调用微信模板消息发送给指定用户
	 * @return
	 * @throws IOException
	 */
	public String sendBless() throws IOException {
		response.setCharacterEncoding("utf-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Long orgId = super.getCurrentOrgAdmin().getId();
		String openId = request.getParameter("openId");
		int flag = service.sendBless(orgId, openId);
		if(flag == 1){
			response.getWriter().write("");
		}else{
			response.getWriter().write("");
		}
		return null;
	}
	
	
	
	/**
	 * JSTREE 接收格式
	 * { "id" : "ajson1", "parent" : "#", "text" : "IBM" }, { "id" : "ajson2", "parent" : "#", "text" : "联想" }, { "id" :
	 * "ajson3", "parent" : "ajson2", "text" : "华东区" },
	 * 
	 * 
	 * 见： http://www.jstree.com/docs/json/
	 * <br>-------------------------------------------------------------------
	 * // Alternative format of the node (id & parent are required)
			{
			  id          : "string" // required
			  parent      : "string" // required
			  text        : "string" // node text
			  icon        : "string" // string for custom
			  state       : {
			    opened    : boolean  // is the node open
			    disabled  : boolean  // is the node disabled
			    selected  : boolean  // is the node selected
			  },
			  li_attr     : {}  // attributes for the generated LI node
			  a_attr      : {}  // attributes for the generated A node
			}
	 * 加载产品分类
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String loadPrdCat() throws IOException {
		response.setCharacterEncoding("utf-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Long userId = this.getCurrentAdminUser().getId();
		if(this.getCurrentAdminUser().isSuperAdmin()){
			userId = 0L;
		}
		if(this.getCurrentAdminUser().isAdmin()){
			userId = -1L;
		}
		List<OrgObj> list = service.loadPrdCat(this.getCurrentOrgCode());
		logger.info(JSONArray.fromObject(list).toString());
		response.getWriter().write(JSONArray.fromObject(list).toString());
		return null;
	}
	/**
	 * 加载代理商 
	 * @return
	 * @throws IOException
	 */
	public String loadDali() throws IOException {
		response.setCharacterEncoding("utf-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		List<OrgObj> list = service.loadDaili(this.getCurrentAdminUser().getId());
		logger.info(JSONArray.fromObject(list).toString());
		response.getWriter().write(JSONArray.fromObject(list).toString());
		return null;
	}
	
	
	/**
	 * 加载微信菜单 
	 * @return
	 * @throws IOException
	 */
	public String loadWxMenu() throws IOException {
		response.setCharacterEncoding("utf-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Long userId = this.getCurrentAdminUser().getId();
		if(this.getCurrentAdminUser().isSuperAdmin()){
			userId = 0L;
		}
		if(this.getCurrentAdminUser().isAdmin()){
			userId = -1L;
		}
		List<OrgObj> list = service.loadPrdCat(this.getCurrentOrgCode());
		logger.info(JSONArray.fromObject(list).toString());
		response.getWriter().write(JSONArray.fromObject(list).toString());
		return null;
	}

	// 只加载第二层，即加载到厂商级别
	public String load2LevelOrg() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		List<OrgObj> list = service.load2LevelOrg();
		response.getWriter().write(JSONArray.fromObject(list).toString());
		return null;
	}
	
	/**
	 * 加载待审核经销商
	 * @return
	 * @throws IOException
	 */
	public String fxAuditList() throws IOException {
		return "fxAuditList";
	}
	
	/**
	 * 加载待审核下级经销商, 根据当前分销商的编号进行过滤
	 * @return
	 * @throws IOException
	 */
	public String loadFxAuditList() throws IOException {
		response.setCharacterEncoding("UTF-8");
//		String orgCode = this.getCurrentAdminUser().getLevelcode();
		Long userId = this.getCurrentAdminUser().getId();
		if(this.getCurrentAdminUser().isSuperAdmin()){
			userId = 0L;
		}
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		List<OrgObj> list = service.loadOrg(userId, 0);
		response.getWriter().write(JSONArray.fromObject(list).toString());
		return null;
	}
	/**
	 * 管理员后台;加载红包
	 * @return
	 * @throws IOException
	 */
	public String loadHongBaoList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("orgId", this.getCurrentOrgAdmin().getId().toString());
		Paging pp = service.loadHongbao(Integer.valueOf(page), Integer.valueOf(limit), params);
		response.getWriter().write(JSONObject.fromObject(pp).toString());
		return null;
	}
	
	
	/**
	 * 后台：生成红包的推广链接， 主要用来放到自定义菜单上 
	 * @return
	 * @throws IOException 
	 */
	public String createHongBaoLink() throws IOException{
		response.setCharacterEncoding("UTF-8");
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfa261c6dad08e42c&redirect_uri=http%3A%2F%2Fwww.91lot.com%2Ffenxiao%2Fwx%2Fwx_wxMall.Q&response_type=code&scope=snsapi_userinfo&state=84#wechat_redirect
		String appId = this.getCurrentOrgAdmin().getAppid();
		String serverUrl = Version.getInstance().getNewProperty("wxServer");
		logger.info("createhongbaoLink---serverUrl==" + serverUrl);
		if(appId == null || StringUtils.isEmpty(appId)){
			response.getWriter().write("{\"msg\":\"appId\"}");
			return null;
		}
//		"uuid": uuid, //红包的批次代码
//		"hbid": hbid	红包的数据库ID
		String param = request.getParameter("uuid")+","+request.getParameter("orgId");//通过state传递的参数
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId +"&redirect_uri=http%3A%2F%2F"+ serverUrl +"%2Fhotel%2Fwx%2Fwx_wxHongbao.Q&response_type=code&scope=snsapi_base&state="+ param +"#wechat_redirect";
		response.getWriter().write("{\"msg\":\"ok\",\"result\":\""+ url +"\"}");
		return null;
	}
	
	

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String addSubOrg() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String pid = request.getParameter("pid");
			String name = request.getParameter("name");
			boolean b = service.addSubOrg(Long.valueOf(pid), name);
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
	
	/**
	 * 新增红包
	 * @return
	 * @throws IOException
	 */
	public String addHongbao() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String thetype = request.getParameter("thetype");
			Map<String, String> params = new HashMap<String, String>();
			if("0".equals(thetype)){//红包
				String hbvalue = request.getParameter("hbvalue");
				String num = request.getParameter("num");
				String expireDate = request.getParameter("expireDate");
				params.put("hbvalue", hbvalue);
				params.put("num", num);
				params.put("expireDate",expireDate);
			}else{//优惠券
				String deduct = request.getParameter("deduct");
				String expireDate = request.getParameter("expireDate");
				params.put("deduct", deduct);
				params.put("expireDate",expireDate);
			}
			params.put("thetype",thetype);
			
			boolean b = service.addHongbao(this.getCurrentOrgAdmin().getId(), params);
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
	
	/**
	 * 删除红包记录 
	 * @return
	 * @throws IOException 
	 */
	public String delHongbao() throws IOException{
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String hbid = request.getParameter("hbId");
			if(hbid == null){
				response.getWriter().write("{\"result\":\"fail\"}");
				return null;
			}
			boolean b = service.delHongbao(Long.valueOf(hbid));
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
	
	
	
	
	//增加微信子菜单
	public String addSubMenu() throws IOException {
		try {
//			/"pid=" + pid + "&name=" + name + "&mtype="+mtype + "&key="+key+"&url="+url,
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String pid = request.getParameter("pid");
			String orgId = this.getCurrentOrgAdmin().getId().toString();
			String name = request.getParameter("name");
			String mtype = request.getParameter("mtype");
			String key = request.getParameter("key");
			String url = request.getParameter("url");
			Map<String, String> params = new HashMap<String, String>();
			params.put("orgId", orgId);
			params.put("name", name);
			params.put("mtype", mtype);
			params.put("key", key);
			params.put("url", url);
			boolean b = service.addSubMenu(Long.valueOf(pid), params);
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
	/**
	 * 新增子分类
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String addSubCat() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String pid = request.getParameter("pid");
			String name = request.getParameter("name");
			long cid = service.addSubCat(Long.valueOf(pid), name, this.getCurrentOrgCode());
			if (cid != -1) {
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
	
	/**
	 * 增加总公司 
	 * @return
	 * @throws IOException
	 */
	public String AddTopOrg() throws IOException {
		try {
			if(this.getCurrentAdminUser().getRoleString().indexOf("超级管理员") == -1){
				//不是超级管理员
				response.getWriter().write("{\"result\":\"auth\"}");
				return null;
			}
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			try{
				//查看是否已经达到25家
				int num = service.countOrg();
				int max = Integer.valueOf(Version.getInstance().getNewProperty("max"));
				if(num >= max){
					response.getWriter().write("{\"result\":\"max\",\"max\":\""+ max +"\"}");
					return null;
				}
			}catch(Exception e){
				response.getWriter().write("{\"result\":\"config\"}");
				return null;
			}
			
			String name = request.getParameter("topOrgName");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			String city = request.getParameter("city");
			
			boolean b = service.addTopOrg(name, telephone, email, city);
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
	/**
	 * 增加一级菜单 
	 * @return
	 * @throws IOException
	 */
	public String AddTopMenu() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			Long orgId = this.getCurrentOrgAdmin().getId();
//			"menuName" : $("#menuName").val(),
//			"mtype" : mtype,
//			"key" : $("#key").val(),
//			"url" : $("#url").val()
			String menuName = request.getParameter("menuName");
			String mtype = request.getParameter("mtype");
			String key = request.getParameter("key");
			String url = request.getParameter("url");
			boolean b = service.addTopMenu(orgId, menuName, mtype, key, url);
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
	
	/**
	 * 增加一级分类
	 * @return
	 * @throws IOException
	 */
	public String AddTopPrdcat() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String name = request.getParameter("topCatName");
			Long id = service.addTopPrdcat(name, this.getCurrentOrgCode());
			if (id > 0) {
				response.getWriter().write("{\"result\":\"ok\", \"id\": "+ id +"}");
			} else {
				response.getWriter().write("{\"result\":\"fail\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	
	/**
	 * 经销商审核，通过 
	 * @return
	 * @throws IOException 
	 */
	public String passAudit() throws IOException{
		PrintWriter writer = response.getWriter();
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String orgId = request.getParameter("orgId");
			boolean b = service.auditOrgStatus(Long.valueOf(orgId), 1);
			if (b) {
				writer.write("{\"result\":\"ok\"}");
			} else {
				writer.write("{\"result\":\"fail\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"result\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 删除审核记录
	 * @return
	 * @throws IOException 
	 */
	public String delAudit() throws IOException{
		PrintWriter writer = response.getWriter();
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String orgId = request.getParameter("orgId");
			boolean b = service.delAudit(Long.valueOf(orgId));
			if (b) {
				writer.write("{\"result\":\"ok\"}");
			} else {
				writer.write("{\"result\":\"fail\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 删除指定节点以及子节点
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String delOrg() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String orgId = request.getParameter("orgId");
			boolean b = service.delOrg(Long.valueOf(orgId));
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
	/**
	 * 删除微信菜单 
	 * @return
	 * @throws IOException
	 */
	public String delWxMenu() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String dbId = request.getParameter("dbId");
			boolean b = service.delWxMenu(Long.valueOf(dbId));
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
	/**
	 * 删除分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String delCat() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String orgId = request.getParameter("orgId");
			boolean b = service.delCat(Long.valueOf(orgId));
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

	/**
	 * 修改节点
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String modifyOrg() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String orgId = request.getParameter("orgId");
			String orgName = request.getParameter("orgName");
			boolean b = service.modOrg(Long.valueOf(orgId), orgName);
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
	public String modifyWxMenu() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			
//			"orgId" : $("#orgId").val(),
//			"menuName" : $("#curMenuName").val(),
//			"mtype" : $("#cmType").val(),
//			"key" : $("#curKey").val(),
//			"url" : $("#curUrl").val()
			
			String orgId = request.getParameter("orgId");
			String dbId = request.getParameter("dbId");
			String menuName = request.getParameter("menuName");
			String mtype = request.getParameter("mtype");
			String key = request.getParameter("key");
			String url = request.getParameter("url");
			
			Map<String, String> mparams = new HashMap<String, String>();
			mparams.put("menuName", menuName);
			mparams.put("mtype", mtype);
			mparams.put("key", key);
			mparams.put("url", url);
			mparams.put("dbId", dbId);
			logger.info("更新菜单....." + mparams.toString());
			//更新菜单
			boolean b = service.modWxMenu(Long.valueOf(orgId), mparams);
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
	/**
	 * 修改分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String modifyCat() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String orgId = request.getParameter("orgId");
			String orgName = request.getParameter("orgName");
			boolean b = service.modCat(Long.valueOf(orgId), orgName);
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

	/**
	 * 加载负责人,佣金， appid等信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String loadCharger() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String orgId = request.getParameter("id");
		if (orgId == null) {
			response.getWriter().write("{\"result\":\"fail\"}");
			return null;
		}
		Org org = service.getCharger(Long.valueOf(orgId));
		JSONObject jo = new JSONObject();
		if (org.getCharger() == null) {
			jo.put("result", "null");
		} else {
			jo.put("result", "ok");
			jo.put("id", org.getCharger().getId());
			jo.put("name", org.getCharger().getUsername());
		}
		//分销商佣金
		jo.put("commission", org.getCommission());
		//分销客佣金
		jo.put("personCommission", org.getPersonCommission());
		jo.put("appid", org.getAppid());
		jo.put("appsecret", org.getAppsecret());
		jo.put("wxID", org.getWxID());
		jo.put("mallexpire", org.getWxmallexpire());
		jo.put("kfAccount", org.getKfAccount());
		//所属地区
		jo.put("city", org.getCity());
		
		jo.put("attHintUrl", org.getAttHintUrl());
		
		jo.put("shareLogo", org.getShareLogo());
		jo.put("shareTitle", org.getShareTitle());
		jo.put("shareDesc", org.getShareDesc());
		jo.put("code", org.getCode());
		jo.put("baoyou", org.getBaoyou());//包邮设置
//		if(org.getCode().length() <= CupidStrutsConstants.LV_CODE_LENGTH){//只有总销商才会加载运费
//			//运费, 
//			jo.put("deleverFee", org.getDeleverFee());
//		}
		
		response.getWriter().write(jo.toString());
		return null;
	}

	/**
	 * 更新
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String updateCharger() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String orgId = request.getParameter("orgId");
		String chargerId = request.getParameter("chargerId");
		if (orgId == null || chargerId == null) {
			response.getWriter().write("{\"result\":\"fail\"}");
			return null;
		}
		boolean b = service.updateCharger(Long.valueOf(orgId), Long.valueOf(chargerId));
		if (!b) {
			response.getWriter().write("{\"result\":\"fail\"}");
		} else {
			response.getWriter().write("{\"result\":\"ok\"}");
		}
		return null;
	}
	
	/**
	 * 更新佣金 
	 * @return
	 * @throws IOException
	 */
	public String updateCommission() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		try{
			String orgId = request.getParameter("orgId");
			String commission = request.getParameter("commission");
			String personCommission = request.getParameter("personCommission");
			if (orgId == null || commission == null) {
				response.getWriter().write("{\"result\":\"fail\"}");
				return null;
			}
			boolean b = service.updateCommission(Long.valueOf(orgId), Integer.valueOf(commission), Integer.valueOf(personCommission));
			if (!b) {
				response.getWriter().write("{\"result\":\"fail\"}");
			} else {
				response.getWriter().write("{\"result\":\"ok\"}");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 更新微信的appid， appsecret等信息
	 * "orgId=" + orgId + "&appid=" + appId + "&appsecret=" + appsecret,
	 * @return
	 * @throws IOException
	 */
	public String updateWxconfig() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String orgId = request.getParameter("orgId");
		String appid = request.getParameter("appid");
		String appsecret = request.getParameter("appsecret");
		String wxID = request.getParameter("wxID");
		//多客服
		String kfAccount = request.getParameter("kfAccount");
		
		if (orgId == null  ) {
			response.getWriter().write("{\"result\":\"fail\"}");
			return null;
		}
		boolean b = service.updateWxconfig(Long.valueOf(orgId), appid, appsecret, wxID, kfAccount);
		if (!b) {
			response.getWriter().write("{\"result\":\"fail\"}");
		} else {
			response.getWriter().write("{\"result\":\"ok\"}");
		}
		return null;
	}
	/**
	 * 更新分销客转发时的配置信息，分享标题，分享说明，图片等
	 * "shareLogo=" + shareLogo + "&shareTitle="+ shareTitle +"&shareDesc=" + shareDesc,
	 * @return
	 * @throws IOException
	 */
	public String updateShareConfig() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String shareLogo = request.getParameter("shareLogo");
		String shareTitle = request.getParameter("shareTitle");
		String shareDesc = request.getParameter("shareDesc");
		String attHintUrl = request.getParameter("attHintUrl");
		Map<String, String> params = new HashMap<String, String>();
		params.put("shareLogo", shareLogo);
		params.put("shareTitle", shareTitle);
		params.put("shareDesc", shareDesc);
		params.put("attHintUrl", attHintUrl);
		boolean b = service.updateShareConfig(params, this.getCurrentOrgCode());
		if (!b) {
			response.getWriter().write("{\"result\":\"fail\"}");
		} else {
			response.getWriter().write("{\"result\":\"ok\"}");
		}
		return null;
	}
	
	/**
	 * 更新微信商城有效期
	 * @return
	 * @throws IOException
	 */
	public String updateMallExpire() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String orgId = request.getParameter("orgId");
		String mallexpire = request.getParameter("mallexpire");
		if (orgId == null  ) {
			response.getWriter().write("{\"result\":\"fail\"}");
			return null;
		}
		boolean b = service.updateWxMallexpire(Long.valueOf(orgId), mallexpire);
		if (!b) {
			response.getWriter().write("{\"result\":\"fail\"}");
		} else {
			response.getWriter().write("{\"result\":\"ok\"}");
		}
		return null;
	}
	
	
	/**
	 * 更新运费
	 * @return
	 * @throws IOException
	 */
	public String updateDeliverFee() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String orgId = request.getParameter("orgId");
		String deliverFee = request.getParameter("deliverFee");
		String baoyou = request.getParameter("baoyou");
		if (orgId == null  ) {
			response.getWriter().write("{\"result\":\"fail\"}");
			return null;
		}
		if(deliverFee == null || StringUtils.isEmpty(deliverFee)){
			deliverFee = "10";//默认设置为10
		}
		boolean b = service.updateDeliverFee(Long.valueOf(orgId), Integer.valueOf(deliverFee), Integer.valueOf(baoyou));
		if (!b) {
			response.getWriter().write("{\"result\":\"fail\"}");
		} else {
			response.getWriter().write("{\"result\":\"ok\"}");
		}
		return null;
	}
	
	/**
	 * 获取消息，用来在左侧菜单显示
	 * @return
	 * @throws IOException
	 */
	public String getMsg() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		//-------------附加功能， 增加左侧菜单栏的提示
////	1)待审核分销商条目  <span class="label label-important">12</span>
		Map<String, String> param = new HashMap<String, String>();
		param.put("status", "0");
		param.put("orgCode", this.getCurrentOrgCode());
		Long userId = this.getCurrentAdminUser().getId();
		if(this.getCurrentAdminUser().isSuperAdmin() || this.getCurrentAdminUser().isAdmin()){
			userId = 0L;
		}
		List<OrgObj> list = service.loadOrg(userId, 0);
		int total = list.size();
		JSONObject jo = new JSONObject();
		jo.put("auditCnt", total);
		response.getWriter().write(jo.toString());
		return null;
	}
	
	
	/**
	 * 显示当前分销商的微商城URL
	 * 
	 *  1） 各分销商自己的菜单上挂接指定URL（分销系统提供，会附带上分销商编号），之后，该分销商编号就存放到服务器session中，供后续逻辑使用
		2） 入口是： http://localhost:88/fenxiao/public/pub_index.Q?c=yRbLyYSGTXyADMxADMwADM
   
   		为了区分不同用户的openId，这个url必须通过微信授权url来引导。这样做的好处是可以直接获取到当前用户的openId，并保存到session，为后续功能所使用、
   		https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8275aa5fa86fb7c6&redirect_uri=http%3A%2F%2Fjms.mycloudexpo.com%2Fjiams%2Fwx%2Fweixin.do%3Fmethod%3DgoPage&response_type=code&scope=snsapi_base&state=U_12#wechat_redirect
   		这个URL需要通过snsapi_base方式的网页授权
	 * @return
	 * @throws IOException
	 */
	public String showWxMallUrl() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String orgId = request.getParameter("orgId");
		if (orgId == null ) {
			response.getWriter().write("{\"result\":\"fail\"}");
			return null;
		}
		//该分销商的APPID
		Org orgById = service.getOrgById(Long.valueOf(orgId));
		String appId = "";
		if(orgById != null){
			appId = orgById.getAppid();
		}
		//传递的参数:分销商ID
		String state = orgId;
		
		//服务器的上下文,经过urlencode
		String redirectUrl =  service.getConfigParam(ConfigParam.WX_SERVER_CTX);
//		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId +"&redirect_uri="+ URLEncoder.encode(redirectUrl, "UTF-8") +"&response_type=code&scope=snsapi_base&state="+ state +"#wechat_redirect";
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId +"&redirect_uri="+ URLEncoder.encode(redirectUrl, "UTF-8") +"&response_type=code&scope=snsapi_userinfo&state="+ state +"#wechat_redirect";
		
		response.getWriter().write(url);
		return null;
	}
	
	
	/**
	 * 用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String userList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 当前登录用户的权限
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword", request.getParameter("keyword"));
		param.put("orgId", request.getParameter("orgId"));
		try {
			Map<String, Object> map = service.getUserList(start, Integer.valueOf(limit).intValue(), param);
			List<User> resumeList = (List<User>) map.get("list");
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
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[] { "org" });
			out.print(JSONObject.fromObject(pp, jc).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}
	
	/**
	 * 车险： 订单列表 
	 * @return
	 * @throws IOException
	 */
	public String orderList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 当前登录用户的权限
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		Map<String, String> param = new HashMap<String, String>();
		//当前代理商管理员ID
		param.put("dailiAdminId", request.getParameter("adminId"));
		//根据代理商管理员ID查询所属区域
		Admin adminById = service.getAdminById(request.getParameter("adminId"));
		if(adminById != null){
			param.put("area", adminById.getArea());
			param.put("province", adminById.getProvince());
		}
		try {
			Paging pp = service.getAreaOrderList(Integer.valueOf(page), Integer.valueOf(limit).intValue(), param);
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[]{"org"});
			out.print(JSONObject.fromObject(pp, jc).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 后台管理： 分销客列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String fxPersonList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 当前登录用户的权限
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword", request.getParameter("keyword"));
		param.put("orgId", request.getParameter("orgId"));
		param.put("flag", "1");//分销客标志
		try {
			Map<String, Object> map = service.getUserList(start, Integer.valueOf(limit).intValue(), param);
			List<User> resumeList = (List<User>) map.get("list");
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
				
				
				//计算佣金
				for (User user : resumeList) {
					//找出该分销客的所有订单，然后分别计算佣金。
					user.setYj(service.getYj(user, 0));
				}
				
				pp.setDatas(resumeList);
			}
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[] { "org" });
			out.print(JSONObject.fromObject(pp, jc).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}
	/**
	 * 待审核经销商用户列表
	 * 根据当前管理员的code，查询该code级别下的待审核经销商。 每个经销商在申请时，都有一个上级经销商编码进行关联。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String auditFxList() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 当前登录用户的权限
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		
		// 分页对象
		model.Paging pp = new model.Paging();
		Map<String, String> param = new HashMap<String, String>();
//		param.put("orgCode", this.getCurrentAdminUser().getLevelcode());
		param.put("status", "0");
		try {
			pp = service.getAuditOrgList(Integer.valueOf(page), Integer.valueOf(limit).intValue(), param);
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[] { "org" });
			out.print(JSONObject.fromObject(pp, jc).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}
	
	
	
	/**
	 * 支付参数设置 
	 * @return
	 */
	public String payParam(){
		try{
			String orgId = Version.getInstance().getNewProperty("testOrgId");
			
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			if("view".equals(request.getParameter("f"))){//点击菜单进入
				Org orgBycode = service.getOrgById(Long.valueOf(orgId));
				request.setAttribute("org", orgBycode);
			}else{
				String op = request.getParameter("op");
				//商户号
				String mchId = request.getParameter("mchId");
				//支付秘钥
				String payKey = request.getParameter("payKey");
				if("update".equals(op)){//说明是更新操作
					service.updatePayParam(orgId, mchId, payKey);
					request.setAttribute("info", "更新成功！");
				}
				//更新session中对象
				Org currentOrgAdmin = this.getCurrentOrgAdmin();
				currentOrgAdmin.setMchId(mchId);
				currentOrgAdmin.setPayKey(payKey);
				request.setAttribute("org", currentOrgAdmin);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ok";
	}
	/**
	 * 微信参数设置 
	 * @return
	 */
	public String wxParam(){
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		if("view".equals(request.getParameter("f"))){//点击菜单进入
			Org orgBycode = service.getOrgBycode(this.getCurrentOrgAdmin().getCode());
			request.setAttribute("org", orgBycode);
		}else{
			String op = request.getParameter("op");
			String orgId = request.getParameter("orgId");
			//商户号
			String mchId = request.getParameter("mchId");
			//支付秘钥
			String payKey = request.getParameter("payKey");
			if("update".equals(op)){//说明是更新操作
				service.updatePayParam(orgId, mchId, payKey);
				request.setAttribute("info", "更新成功！");
			}
			//更新session中对象
			Org currentOrgAdmin = this.getCurrentOrgAdmin();
			currentOrgAdmin.setMchId(mchId);
			currentOrgAdmin.setPayKey(payKey);
			request.setAttribute("org", currentOrgAdmin);
		}
		return "ok";
	}
	
	
	
	/**
	 * 支付参数设置 
	 * @return
	 */
	public String payParamSave(){
		
		return "ok";
	}
	
	public String payConfig(){
		return "ok";
	}
	
	
	public String cashApply(){
		return "ok";
	}
	
	
	/**
	 * 下级分销商提交提现申请 
	 * @return
	 * @throws IOException
	 */
	public String submitCashApply() throws IOException{
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		String applyFee = request.getParameter("applyFee");
		try{
			//判断余额 submitCashApply
			Long yj = service.getFxYj(this.getCurrentOrgAdmin(), 0);
			if(Long.valueOf(applyFee).longValue() > yj.longValue()){
				out.print("{\"result\":\"nofee\"}");//余额不足
				return null;
			}
			
			CashApply ca = new CashApply();
			ca.setAtype(1);// 0 - 分销客， 1-分销商
			ca.setUserId(this.getCurrentAdminUser().getId());
			ca.setOrgCode(this.getCurrentOrgAdmin().getCode());
			ca.setTopOrgCode(this.getCurrentOrgAdmin().getCode().substring(0, CupidStrutsConstants.LV_CODE_LENGTH));
			ca.setFlag(0);//状态：0-申请中  1-已处理  2-已拒绝 
			ca.setApplyDate(DateUtil.getLongCurrentDate());
			ca.setApplyFee(Long.valueOf(applyFee) * 100);
			ca.setNickName(this.getCurrentOrgAdmin().getOrgname());//就是分销商的名称
//			ca.setOrgCode(this.getCurrentFxCode());
			//根据当前分销商代码获取顶级，取前3位即可
//			ca.setTopOrgCode(this.getCurrentFxCode().substring(0,3));
			service.cashApply(ca);
			
			out.print("{\"result\":\"ok\"}");
		}catch(Exception e){
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	
	/**
	 * 分销商： 提现记录 
	 * @return
	 */
	public String cashApllyRecord(){
		logger.info("............");
		return "ok";
	}
	
	/**
	 * 积分商城 
	 * @return
	 */
	public String giftList(){
		return "ok";
	}
	
	/**
	 * 兑换管理 
	 * @return
	 */
	public String exchangeList(){
		return "ok";
	}
	
	
	
	
	/**
	 * 总销商： 提现记录 
	 * @return
	 */
	public String cashApllyMgt(){
		logger.info("............1111111111");
		try{
			return "ok";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ok";
	}
	
	
	/**
	 * 分销申请设置
	 * @return
	 */
	public String fxApplyConfig(){
		try{
			//根据当前分销商管理员ID，获取属于该分销商的配置fxApplyConfigDao
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			FxApplyConfig fac =  service.getFxApplyConfig(this.getCurrentOrgCode());
			if(fac == null){//还未设置过
				fac = new FxApplyConfig();
			}
			request.setAttribute("ac", fac);
			return "ok";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ok";
	}
	
	
	private File fxsFile;
	private String fxsPic;
	private String fxkPic;
	private File fxkFile;
	
	private FxApplyConfig fac;
	
	/**
	 * 分销申请设置保存或更新
	 * @return
	 */
	public String saveOrUpdateFxApplyConfig(){
		try{
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			Org orgBycode = this.getCurrentOrgAdmin();
			if(StringUtils.isNotBlank(fxsPic)){
				String fxsPic = "/upload/org-"+orgBycode.getId()+"/fx/fxsPic." + this.fxsPic.split("\\.")[1];
				fac.setFxsDescPic(fxsPic);
			}
			if(StringUtils.isNotBlank(fxkPic)){
				String fxkPic = "/upload/org-"+orgBycode.getId()+"/fx/fxkPic." + this.fxkPic.split("\\.")[1];
				fac.setFxkDescPic(fxkPic);
			}
			fac.setOrgCode(this.getCurrentOrgCode());
			
			
			//判断是否清除图片
			String clearFxsPic = request.getParameter("clearFxsPic");
			String clearFxkPic = request.getParameter("clearFxkPic");
			if(clearFxkPic != null){
				fac.setClearFxkPic(Integer.valueOf(clearFxkPic));
			}
			if(clearFxsPic != null){
				fac.setClearFxsPic(Integer.valueOf(clearFxsPic));
			}
			boolean b = service.saveOrUpdateFxApplyConfig(fac);
			
			//保存成功后，再将图片上传到服务器上面
			if(b && StringUtils.isNotEmpty(fxsPic) && fxsFile != null){
				uploadPic("fxsPic."+this.fxsPic.split("\\.")[1], this.getCurrentOrgAdmin(), fxsFile);
			}
			
			if(b && StringUtils.isNotEmpty(fxkPic) && fxkFile != null){
				uploadPic("fxkPic." + this.fxkPic.split("\\.")[1], this.getCurrentOrgAdmin(), fxkFile);
			}
			//返回到配置首页
			FxApplyConfig fac =  service.getFxApplyConfig(this.getCurrentOrgCode());
			if(fac == null){//还未设置过
				fac = new FxApplyConfig();
			}
			request.setAttribute("ac", fac);
			request.setAttribute("info", "修改成功！");
		}catch(Exception e){
			e.printStackTrace();
			return "info";
		}
		return "facIndex";
	}
	
	/**
	 * 上传banner照片 
	 * @param f 
	 * @param user 
	 */
	private void uploadPic(String fileName, Org org, File f) {
		// 为该用户创建一个文件夹
		String userDir = this.getUserPicPath(request, "org-"+org.getId()+"/fx/");
		File dir = new File(userDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		//目标文件名
		File destFile = new File(userDir + "/" + fileName);
		SysUtil.copy(f, destFile);
		logger.info("上传"+ f.getName() +"结束////");
	}
	
	/**
	 * 总销商后台确认放款 
	 * @return
	 * @throws IOException 
	 */
	public String cashApplyDone() throws IOException{
		String id = request.getParameter("id");
		if(id != null){
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			if(service.handleCashApply(Long.valueOf(id), 1)){
				response.getWriter().write("{\"result\":\"ok\"}");
			}else{
				response.getWriter().write("{\"result\":\"fail\"}");
			}
		}
		return null;
	}
	/**
	 * 分销商 取消提现 
	 * @return
	 * @throws IOException 
	 */
	public String cancelApply() throws IOException{
		String id = request.getParameter("id");
		if(id != null){
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			if(service.cancelApply(Long.valueOf(id))){
				response.getWriter().write("{\"result\":\"ok\"}");
			}else{
				response.getWriter().write("{\"result\":\"fail\"}");
			}
		}
		return null;
	}
	
	/**
	 * 清除爆款图片
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public String clearOnePrdPic() throws NumberFormatException, Exception{
		String id = request.getParameter("id");
		String index = request.getParameter("index");
		if(id != null){
			IFenxiaoService service = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
			if(service.clearOnePrdPic(Long.valueOf(id), Integer.valueOf(index))){
				response.getWriter().write("{\"result\":\"ok\"}");
			}else{
				response.getWriter().write("{\"result\":\"fail\"}");
			}
		}
		return null;
	}
	
	/**
	 * 清除产品图片
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public String clearPrdPic() throws NumberFormatException, Exception{
		String id = request.getParameter("id");
		String index = request.getParameter("index");
		if(id != null){
			IFenxiaoService service = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
			if(service.clearPrdPic(Long.valueOf(id), Integer.valueOf(index))){
				response.getWriter().write("{\"result\":\"ok\"}");
			}else{
				response.getWriter().write("{\"result\":\"fail\"}");
			}
		}
		return null;
	}
	
	
	
	
	/**
	 * 删除规格 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String delPrdSpec() throws NumberFormatException, Exception{
		String specId = request.getParameter("specId");
		if(specId != null){
			IFenxiaoService service = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
			if(service.delPrdSpec(Long.valueOf(specId))){
				response.getWriter().write("{\"result\":\"ok\"}");
			}else{
				response.getWriter().write("{\"result\":\"fail\"}");
			}
		}
		return null;
	}
	/**
	 * 加载产品规格， 根据页面上传递过来得分类ID 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String loadPrdSpec() throws NumberFormatException, Exception{
		try{
			response.setCharacterEncoding("UTF-8");
			String prdCatId = request.getParameter("prdCatId");
			if(prdCatId != null){
				IFenxiaoService service = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
				List<PrdSpec> specList = service.loadPrdSpec(prdCatId);
				if(specList.size() > 0){
					JSONObject jo = new JSONObject();
					jo.put("result", "ok");
					jo.put("datas", specList);
					response.getWriter().write(jo.toString());
				}else{
					//暂无规格
					response.getWriter().write("{\"result\":\"null\"}");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		
		return null;
	}
	
	
	/**
	 * add prdspec
	 * @return
	 * @throws IOException 
	 */
	public String addPrdSpec() throws IOException {
		String catId = request.getParameter("catId");
		String specName = request.getParameter("specName");
		if(StringUtils.isEmpty(catId) || StringUtils.isEmpty(specName)){
			response.getWriter().write("{\"result\":\"param\"}");
			return null;
		}
		IFenxiaoService service = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
		long id = service.addPrdSpec(catId, specName);
		if(id > 0){
			response.getWriter().write("{\"result\":\"ok\",\"id\":\""+ id +"\"}");
		}else{
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		
		return null;
	}
	
	
	
	
	
	/**
	 * 分销商：提现记录
	 * @return
	 * @throws IOException 
	 */
	public String getCashApplyRecord() throws IOException{
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 当前登录用户的权限
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		Map<String, String> param = new HashMap<String, String>();
		param.put("orgCode", this.getCurrentOrgAdmin().getCode());
		param.put("flag", "1");//分销商标志
		try {
			Map<String, Object> map = service.getCashApplyRecordList(start, Integer.valueOf(limit).intValue(), param);
			List<CashApply> resumeList = (List<CashApply>) map.get("list");
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
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[] { "org" });
			out.print(JSONObject.fromObject(pp, jc).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}
	/**
	 * =======================总销商：提现记录====================
	 * @return
	 * @throws IOException 
	 */
	public String allCashApplyRecord() throws IOException{
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 当前登录用户的权限
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		Map<String, String> param = new HashMap<String, String>();
		param.put("topOrgCode", this.getCurrentOrgAdmin().getCode());
//		param.put("atype", "1");//提现类型： 0 - 分销客， 1-分销商
		try {
			Map<String, Object> map = service.getCashApplyRecordList(start, Integer.valueOf(limit).intValue(), param);
			List<CashApply> resumeList = (List<CashApply>) map.get("list");
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
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[] { "org" });
			out.print(JSONObject.fromObject(pp, jc).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}
	
	/**
	 * 根据商家查询产品列表 
	 * @return
	 * @throws IOException
	 */
	public String productListByOrg() throws IOException{
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 当前登录用户的权限
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		Map<String, String> param = new HashMap<String, String>();
		param.put("orgCode", request.getParameter("orgCode"));
//		param.put("atype", "1");//提现类型： 0 - 分销客， 1-分销商
		try {
			Map<String, Object> map = service.getProductList(start, Integer.valueOf(limit).intValue(), param);
			List<Products> resumeList = (List<Products>) map.get("list");
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
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[] { "org" });
			out.print(JSONObject.fromObject(pp, jc).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		return null;
	}
	
	
	
	//------------  红包、优惠券 ----------------
	public String hongbaoList(){
		return "hongbaoLlist";
	}
	
	
	
	public File[] getMyFile() {
		return myFile;
	}

	
	public void setMyFile(File[] myFile) {
		this.myFile = myFile;
	}

	
	public String[] getMyFileFileName() {
		return myFileFileName;
	}

	
	public void setMyFileFileName(String[] myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	
	public String getImageFileName() {
		return imageFileName;
	}

	
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	
	public void setOrg(Org org) {
		this.org = org;
	}

	
	public File getFxsFile() {
		return fxsFile;
	}

	
	public void setFxsFile(File fxsFile) {
		this.fxsFile = fxsFile;
	}

	
	public File getFxkFile() {
		return fxkFile;
	}

	
	public void setFxkFile(File fxkFile) {
		this.fxkFile = fxkFile;
	}

	
	public String getFxsPic() {
		return fxsPic;
	}

	
	public void setFxsPic(String fxsPic) {
		this.fxsPic = fxsPic;
	}

	
	public String getFxkPic() {
		return fxkPic;
	}

	
	public void setFxkPic(String fxkPic) {
		this.fxkPic = fxkPic;
	}

	
	public FxApplyConfig getFac() {
		return fac;
	}

	
	public void setFac(FxApplyConfig fac) {
		this.fac = fac;
	}
	
	
	
}
