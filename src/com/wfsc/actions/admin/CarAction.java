package com.wfsc.actions.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.Paging;
import model.bo.auth.Org;
import model.bo.car.CarInfo;
import model.bo.hotel.BookRecord;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.intf.AdminService;
import service.intf.ICarInfoService;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;
import com.wfsc.services.product.IHotelService;

/**
 * 行业相关ACTION
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("CarAction")
@Scope("prototype")
public class CarAction extends DispatchPagerAction {

	private static final long serialVersionUID = 8293457585142562281L;

	private Logger logger = Logger.getLogger(AdminAction.class);

	private Org org;


	@Resource(name = "userService")
	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	/**
	 * 房间预定列表
	 * 
	 * @return
	 * @throws IOException
	 */
	public String bookList() {
		return "ok";
	}
	
	
	public String areaUsers() {
		request.setAttribute("area", this.getCurrentAdminUser().getArea());
		return "ok";
	}
	
	public String areaUserList() throws IOException{
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
		//根据当前管理员所在地区查询
		param.put("area", this.getCurrentAdminUser().getArea());
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
				
				
				for (User user : resumeList) {
					//找出该分销客的所有订单金额 。
//					user.setYj(service.getYj(user, 0));
					user.setOrderFee(service.getOrdersFee(user.getId()));
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
	 * 进入代理商管理首页 
	 * @return
	 */
	public String dailiManage(){
		logger.info("dali....");
		return "ok";
	}
	

	public String cancelBook() throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			IHotelService service = (IHotelService) ServerBeanFactory.getBean("hotelService");
			String id = request.getParameter("id");
			service.updateBookRecord(Long.valueOf(id), BookRecord.STATUS_IDLE);
			out.write("{\"msg\":\"ok\"}");
		} catch (Exception e) {
			out.write("{\"msg\":\"fail\"}");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 救援管理列表
	 * @return
	 */
	public String rescueManageList(){
		//加载当地服务商信息
		ICarInfoService s = (ICarInfoService) ServerBeanFactory.getBean("carService");
		List<Admin> list=s.getLocalServiceNames(this.getCurrentAdminUser().getId());
		request.setAttribute("serviceList", list);
		return "ok";
	}
	
	public String cleaningManageList(){
		//加载当地服务商信息
		ICarInfoService s = (ICarInfoService) ServerBeanFactory.getBean("carService");
		List<Admin> list=s.getLocalServiceNames(this.getCurrentAdminUser().getId());
		request.setAttribute("serviceList", list);
		return "ok";
	}
	
	public String couponManageList(){
		return "ok";
	}
	
	public String cleaningCardsManageList(){
		return "ok";
	}
	public String cleaningLogList(){
		request.setAttribute("cardCode",request.getParameter("cardCode"));
		return "ok";
	}
	

	public String loadRescueList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("area", this.getCurrentAdminUser().getArea());
		
		try {
			Paging pp = service.loadRescueList(Integer.valueOf(page), Integer.valueOf(limit).intValue(), paramMap);
			if (!pp.getDatas().isEmpty()) {
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 后台管理：洗车卡列表 
	 * @return
	 * @throws IOException
	 */
	public String cleaningCardsList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("serviceId", this.getCurrentAdminUser().getId().toString());
		if(request.getParameter("cardCode") != null && StringUtils.isNotEmpty(request.getParameter("cardCode"))){//卡号
			paramMap.put("cardCode", request.getParameter("cardCode"));
		}
		try {
			Paging pp = service.cleaningCardsList(Integer.valueOf(page), Integer.valueOf(limit).intValue(), paramMap);
			if (!pp.getDatas().isEmpty()) {
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 后台管理：优惠券列表 
	 * @return
	 * @throws IOException
	 */
	public String couponList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("serviceId", this.getCurrentAdminUser().getId().toString());
		if(request.getParameter("couponCode") != null){
			paramMap.put("couponCode", request.getParameter("couponCode"));
		}
		//优惠券的状态
		if(request.getParameter("status") != null){
			paramMap.put("status", request.getParameter("status"));
		}
		try {
			Paging pp = service.loadCouponList(Integer.valueOf(page), Integer.valueOf(limit).intValue(), paramMap);
			if (!pp.getDatas().isEmpty()) {
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	
	/**
	 * 后台管理：优惠券列表 
	 * @return
	 * @throws IOException
	 */
	public String loadCleaningLogList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		if(request.getParameter("cardCode") != null){
			paramMap.put("cardCode", request.getParameter("cardCode"));
		}
		try {
			Paging pp = service.loadCleaningLogList(Integer.valueOf(page), Integer.valueOf(limit).intValue(), paramMap);
			if (!pp.getDatas().isEmpty()) {
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	/** 核销 
	 * @throws IOException */
	public String consume() throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try{
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			String cid = request.getParameter("dbId");
			if(cid == null){
				out.print("{\"msg\":\"fail\"}");
				return null;
			}
			service.consumeCoupon(Long.valueOf(cid));
			out.print("{\"msg\":\"ok\"}");
		}catch(Exception e){
			e.printStackTrace();
			out.print("{\"msg\":\"fail\"}");
		}
		return null;
	}
	/** 核销 洗车卡
	 * @throws IOException 
	 **/
	public String consumeCleaning() throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try{
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			String cid = request.getParameter("dbId");
			if(cid == null){
				out.print("{\"msg\":\"fail\"}");
				return null;
			}
			int flag = service.consumeCleaning(Long.valueOf(cid));
			if(flag == 0){
				out.print("{\"msg\":\"null\"}");
			}else if(flag == 1){
				out.print("{\"msg\":\"ok\"}");
			}else{
				out.print("{\"msg\":\"ex\"}");
			}
		}catch(Exception e){
			e.printStackTrace();
			out.print("{\"msg\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 加载洗车服务列表 
	 * @return
	 * @throws IOException
	 */
	public String loadCleaningList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("area", this.getCurrentAdminUser().getArea());
		logger.info("area---" + this.getCurrentAdminUser().getArea());
		try {
			Paging pp = service.loadCleaningList(Integer.valueOf(page), Integer.valueOf(limit).intValue(), paramMap);
			if (!pp.getDatas().isEmpty()) {
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 发布救援信息 
	 * @return
	 * @throws IOException 
	 */
	public String addCleaning() throws IOException{
		try {
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			Map<String, String> params = new HashMap<String, String>();
			String facName = request.getParameter("facName");
			String serviceContent = request.getParameter("serviceContent");
			String price = request.getParameter("price");
			String telephone = request.getParameter("telephone");
			String address = request.getParameter("address");
			String charger = request.getParameter("charger");
			params.put("facName", facName);
			params.put("serviceContent",serviceContent);
			params.put("price",price);
			params.put("telephone",telephone);
			params.put("address",address);
			params.put("charger",charger);
			//所属区域
			params.put("area",this.getCurrentAdminUser().getArea());
			
			boolean b = service.addCleaningInfo(params);
			if (b) {
				response.getWriter().write("{\"msg\":\"ok\"}");
			} else {
				response.getWriter().write("{\"msg\":\"fail\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"msg\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 发布救援信息 
	 * @return
	 * @throws IOException 
	 */
	public String addRescue() throws IOException{
		try {
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			Map<String, String> params = new HashMap<String, String>();
			String facName = request.getParameter("facName");
			String serviceContent = request.getParameter("serviceContent");
			String price = request.getParameter("price");
			String telephone = request.getParameter("telephone");
			String address = request.getParameter("address");
			String charger = request.getParameter("charger");
			params.put("facName", facName);
			params.put("serviceContent",serviceContent);
			params.put("price",price);
			params.put("telephone",telephone);
			params.put("address",address);
			params.put("charger",charger);
			//所属区域
			params.put("area",this.getCurrentAdminUser().getArea());
			
			boolean b = service.addRescueInfo(params);
			if (b) {
				response.getWriter().write("{\"msg\":\"ok\"}");
			} else {
				response.getWriter().write("{\"msg\":\"fail\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"msg\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 删除救援信息 
	 * @return
	 * @throws IOException 
	 */
	public String delRescue() throws IOException{
		try{
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			Long id = Long.valueOf(request.getParameter("dbId"));
			service.delRescue(id);
			response.getWriter().write("{\"msg\":\"ok\"}");
		}catch(Exception e){
			response.getWriter().write("{\"msg\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 删除核保废弃记录 
	 * @return
	 * @throws IOException 
	 */
	public String delHebaoRecord() throws IOException{
		try{
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			Long id = Long.valueOf(request.getParameter("dbId"));
			service.delHebao(id);
			response.getWriter().write("{\"msg\":\"ok\"}");
		}catch(Exception e){
			response.getWriter().write("{\"msg\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 反馈核保价格 
	 * @return
	 * @throws IOException
	 */
	public String submitHebaoPrice() throws IOException{
		try{
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			String price = request.getParameter("price");
			String dbId =  request.getParameter("dbId");
			String orgCode = this.getCurrentOrgAdmin().getCode();
			boolean b = service.submitHebaoPrice(dbId, price,orgCode);
			if(b){
				response.getWriter().write("{\"msg\":\"ok\"}");
			}else{
				response.getWriter().write("{\"msg\":\"fail\"}");
			}
		}catch(Exception e){
			response.getWriter().write("{\"msg\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 核保详情 
	 * @return
	 */
	public String hebaoDetail(){
		String id = request.getParameter("id");
		if(id == null || StringUtils.isEmpty(id)){
			request.setAttribute("info", "参数不正确。");
			return "info";
		}
		try{
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			CarInfo ci = service.getHebaoDetail(Long.valueOf(id));
			request.setAttribute("carInfo", ci);
			return "ok";
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("info", "系统发生错误："+e.getMessage());
			return "info";
		}
	}
	
	
	/**
	 * 删除
	 * @return
	 * @throws IOException
	 */
	public String delCleaning() throws IOException{
		try{
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			Long id = Long.valueOf(request.getParameter("dbId"));
			service.delCleaning(id);
			response.getWriter().write("{\"msg\":\"ok\"}");
		}catch(Exception e){
			response.getWriter().write("{\"msg\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 后台核保 
	 * @return
	 */
	public String hebaoIndex(){
		return "ok";
	}
	
	/**
	 * ajax加载用户提交的核保记录
	 * @return
	 * @throws IOException
	 */
	public String loadHebaoList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		if(request.getParameter("flag") != null){
			paramMap.put("flag", request.getParameter("flag"));
		}
		try {
			Paging pp = service.loadHebaoList(Integer.valueOf(page), Integer.valueOf(limit).intValue(), paramMap);
			if (!pp.getDatas().isEmpty()) {
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	
}
