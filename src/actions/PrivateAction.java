package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Paging;
import model.bo.OnlineTests;
import model.bo.auth.Org;
import model.bo.car.CarInfo;
import model.bo.fenxiao.CashApply;
import model.bo.fenxiao.FxApplyConfig;
import model.bo.food.ShoppingCart;
import model.bo.wxmall.Pj;
import model.vo.WxPayParamn;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.intf.AdminService;
import service.intf.ICarInfoService;
import service.intf.IFenxiaoService;
import service.intf.PublicService;
import util.WxPayUtil;
import actions.integ.weixin.WeiXinUtil;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.constants.CupidStrutsConstants;
import com.wfsc.annotation.Login;
import com.wfsc.common.bo.account.Address;
import com.wfsc.common.bo.account.Coupon;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IAccountService;
import com.wfsc.util.DateUtil;

import constants.MarkConstants;
@Login
@Controller("PrivateXAction")
@Scope("prototype")
public class PrivateAction extends DispatchPagerAction {

	private static final long serialVersionUID = 1522594488482570959L;

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	private Logger payLogger = LogUtil.getLogger(LogUtil.PAY);

	/**
	 * 考卷列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getAllTestList() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		List<OnlineTests> tests = service.getAllTests();
		PrintWriter out = response.getWriter();
		if (!tests.isEmpty()) {
			out.write(JSONArray.fromObject(tests).toString());
		} else {
			out.write("{\"result\":\"empty\"}");
		}
		return null;
	}

	/**
	 * 订单管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getMyOrders() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		// 查询参数
		// Map<String, String> paramMap = getParamMap(request);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", this.getCurrentUser().getId().toString());
		//flag是前台对应的订单的状态
		paramMap.put("flag", request.getParameter("flag"));
		paramMap.put("category", request.getParameter("category"));//订单分类，普通订单还是车险订单
		try {
			Map<String, Object> map = service.getMyOrders(start, Integer.valueOf(limit).intValue(), paramMap);
			List<Orders> dataList = (List<Orders>) map.get("list");
			int total = Integer.valueOf(map.get("total").toString());
			pp.setPage(Integer.valueOf(page));// 当前页码
			pp.setLimit(Integer.valueOf(limit));// 每页显示条数
			if (!dataList.isEmpty()) {
				pp.setTotal(total);
				// 总页数
				if (total % pp.getLimit() != 0) {
					pp.setTotalPage(total / pp.getLimit() + 1);
				} else {
					pp.setTotalPage(total / pp.getLimit());
				}
				logger.info("数据页数：" + pp.getTotalPage());
				pp.setDatas(dataList);
			}
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[]{"user"});
			out.print(JSONObject.fromObject(pp, jc).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 我的车险 
	 * @return
	 * @throws IOException
	 */
	public String getMyCarInsurence() throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
			// 查询参数
			// Map<String, String> paramMap = getParamMap(request);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("openId", this.getCurrentUser().getOpenId());
			Paging pp = service.getMyCarInsurence(Integer.valueOf(page), Integer.valueOf(limit), paramMap);
			 if(!pp.getDatas().isEmpty()){
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"msg\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"msg\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 我的优惠券
	 * @return
	 * @throws IOException
	 */
	public String getMyDiyongquanList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
			// 查询参数
			// Map<String, String> paramMap = getParamMap(request);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("openId", this.getCurrentUser().getOpenId());
			paramMap.put("userId", this.getCurrentUser().getId().toString());
			paramMap.put("status", "0");
			if(request.getParameter("pid") != null){//传入优惠券分类的ID
				paramMap.put("pid", request.getParameter("pid"));
			}
			Paging pp = service.getMyDiyongquanList(Integer.valueOf(page), Integer.valueOf(limit), paramMap);
			if(!pp.getDatas().isEmpty()){
				JSONObject jo = new JSONObject();
				jo.put("msg", "ok");
				jo.put("result", pp);
				response.getWriter().write(JSONObject.fromObject(jo).toString());
			} else {
				out.print("{\"msg\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"msg\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 我的洗车卡
	 * @return
	 * @throws IOException
	 */
	public String getMyCleaningCardsList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
			// 查询参数
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("openId", this.getCurrentUser().getOpenId());
			Paging pp = service.getMyCleaningCardsList(Integer.valueOf(page), Integer.valueOf(limit), paramMap);
			if(!pp.getDatas().isEmpty()){
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"msg\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"msg\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 洗车记录 
	 * @return
	 * @throws IOException 
	 */
	public String getMyCleaningLogs() throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
			// 查询参数
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("openId", this.getCurrentUser().getOpenId());
			Paging pp = service.getMyWashLogs(Integer.valueOf(page), Integer.valueOf(limit), paramMap);
			if(!pp.getDatas().isEmpty()){
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"msg\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"msg\":\"fail\"}");
		}
		return null;
	}
	
	
	/**
	 * 查询当前用户的红包列表
	 * @return
	 * @throws IOException
	 */
	public String getMyHongBaoList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 查询参数
		// Map<String, String> paramMap = getParamMap(request);
		Map<String, String> paramMap = new HashMap<String, String>();
//		if (request.getParameter("keyword") != null) {
//			paramMap.put("keyword", request.getParameter("keyword"));
//		}
		//当前用户ID
		paramMap.put("openId", this.getCurrentUser().getOpenId());
		
		try {
			Paging pp = service.getMyCarInsurence(Integer.valueOf(page), Integer.valueOf(limit).intValue(), paramMap);
			out.print(JSONObject.fromObject(pp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 购物车---加载红包信息 
	 * @return
	 * @throws IOException 
	 */
	public String loadHongbao() throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Map<String, String> paramMap = new HashMap<String, String>();
		//当前用户ID
		paramMap.put("userId", this.getCurrentUser().getId().toString());
		Paging pp = service.getMyHongBaoList(1, 9999, paramMap);
		out.print(JSONObject.fromObject(pp).toString());
		return null;
	}
	
	
	/**
	 * 分销客订单管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getMyFxkOrders() throws IOException {
		IFenxiaoService service = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		// 查询参数
		// Map<String, String> paramMap = getParamMap(request);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("fxkUserId", this.getCurrentUser().getId().toString());
		try {
			Map<String, Object> map = service.getMyFxkOrders(start, Integer.valueOf(limit).intValue(), paramMap);
			List<Orders> dataList = (List<Orders>) map.get("list");
			int total = Integer.valueOf(map.get("total").toString());
			pp.setPage(Integer.valueOf(page));// 当前页码
			pp.setLimit(Integer.valueOf(limit));// 每页显示条数
			if (!dataList.isEmpty()) {
				pp.setTotal(total);
				// 总页数
				if (total % pp.getLimit() != 0) {
					pp.setTotalPage(total / pp.getLimit() + 1);
				} else {
					pp.setTotalPage(total / pp.getLimit());
				}
				logger.info("数据页数：" + pp.getTotalPage());
				pp.setDatas(dataList);
			}
			JsonConfig jc = new JsonConfig();
			jc.setExcludes(new String[]{"user"});
			out.print(JSONObject.fromObject(pp, jc).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 分销客户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getMyFxkCustomers() throws IOException {
		response.setCharacterEncoding("UTF-8");
		IFenxiaoService service = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		// 查询参数
		// Map<String, String> paramMap = getParamMap(request);
		Map<String, String> paramMap = new HashMap<String, String>();
		//
		paramMap.put("pid", this.getCurrentUser().getId().toString());
		try {
			Map<String, Object> map = service.getMyFxkCustomers(start, Integer.valueOf(limit).intValue(), paramMap);
			List<User> dataList = (List<User>) map.get("list");
			int total = Integer.valueOf(map.get("total").toString());
			pp.setPage(Integer.valueOf(page));// 当前页码
			pp.setLimit(Integer.valueOf(limit));// 每页显示条数
			if (!dataList.isEmpty()) {
				pp.setTotal(total);
				// 总页数
				if (total % pp.getLimit() != 0) {
					pp.setTotalPage(total / pp.getLimit() + 1);
				} else {
					pp.setTotalPage(total / pp.getLimit());
				}
				logger.info("数据页数：" + pp.getTotalPage());
				pp.setDatas(dataList);
			}
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
	 * 新增地址
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String updateAddress() throws IOException {
		// flag =0 新增， flag=1 修改
		String flag = request.getParameter("flag");
		// openId
		String openId = request.getParameter("openId");
		String userName = request.getParameter("userName");
		String telephone = request.getParameter("telephone");
		String address = request.getParameter("address");
		String uaId = request.getParameter("uaId");
		PrintWriter out = null;
		try {
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			Map<String, String> param = new HashMap<String, String>();
			param.put("flag", flag);
			param.put("openId", openId);
			param.put("userName", userName);
			param.put("telephone", telephone);
			param.put("address", address);
			param.put("uaId", uaId);
			service.updateAddress(param);
			out = response.getWriter();
			out.write("ok");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("fail");
		}
		return null;
	}

	/**
	 * 添加到购物车
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String add2Cart() throws IOException {
		String userId = this.getCurrentUser().getId().toString();
		String prdId = request.getParameter("prdId");
		String count = request.getParameter("count");
		PrintWriter out = response.getWriter();
		try {
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			Map<String, String> param = new HashMap<String, String>();
			param.put("userId", userId);
			param.put("prdId", prdId);
			param.put("count", count);
			long[] res = service.add2Cart(param);
			request.getSession().setAttribute("cartCount", res[0]);
			// 返回购物车里的条目数量
			out.write("{\"result\":\"" + res[0] + "\", \"scId\":\""+ res[1] +"\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	
	/**
	 * 直接购买
	 * @return
	 */
	public String directBuy() throws IOException {
		response.setCharacterEncoding("UTF-8");
		String userId = this.getCurrentUser().getId().toString();
		String scId = request.getParameter("scId");
		PrintWriter out = response.getWriter();
		try {
			//为了兼容后续的代码， 直接将这个商品添加到购物车
			//直接根据当前商品，生成订单，并跳转到订单确认页 
			Map<String, String> oparam = new HashMap<String, String>();
			//不需跳转的时候，购物车里勾选的条目ID就需要通过getAttribute方式来获取
			//request.setAttribute("scIds", scIds);
			oparam.put("scIds", scId + ",");
			if(this.getCurrentUser() != null){
				oparam.put("userId", userId);
			}
		
			//把分销商code传递过去
			oparam.put("fxCode", request.getSession().getAttribute(CupidStrutsConstants.FXCODE).toString());
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			Orders saveOrder = service.saveOrder(oparam);
			request.setAttribute("order", saveOrder);
		
			request.setAttribute("baoyou", "y");
			
			return ("car-confirmOrder");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	
	/**
	 * 加载购物车里的商品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String loadShoppingCart() throws IOException {
		response.setCharacterEncoding("UTF-8");
		PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
		AdminService adminService = (AdminService) ServerBeanFactory.getBean("adminService");
		List<ShoppingCart> list = service.getShoppingCartPrdList(this.getCurrentUser().getId());
		PrintWriter out = response.getWriter();
		try{
			if (!list.isEmpty()) {
				JsonConfig jc = new JsonConfig();
				JSONObject jo = new JSONObject();
				jc.setExcludes(new String[]{"user"});
				jo.put("list", JSONArray.fromObject(list, jc).toString());
				
				Org orgBycode = adminService.getOrgBycode(this.getCurrentFxCode());
				jo.put("baoyou", orgBycode.getBaoyou());
				
				out.write(jo.toString());
			} else {
				out.write("{\"result\":\"empty\"}");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 购物车里条目统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getCount4Cart() throws IOException {
		PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
		List<ShoppingCart> list = service.getShoppingCartPrdList(this.getCurrentUser().getId());
		PrintWriter out = response.getWriter();
		if (!list.isEmpty()) {
			out.write("{\"result\":\"" + list.size() + "\"}");
		} else {
			out.write("{\"result\":\"empty\"}");
		}
		return null;
	}

	/**
	 * 从购物车删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String delfromCart() throws IOException {
		PrintWriter out = response.getWriter();
		try {
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			String ids = request.getParameter("ids");
			List<Long> idList = new ArrayList<Long>();
			String[] split = StringUtils.split(ids, "|");
			for (String string : split) {
				idList.add(Long.valueOf(string));
			}
			// 删除购物车，并返回购物车里剩余数量
			int num = service.delfromCart(idList, this.getCurrentUser().getId());
			request.getSession().setAttribute("cartCount", num);
			out.write("{\"result\":\"" + num + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 查询用户配送地址
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getUserAdd() throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Long userId = this.getCurrentUser().getId();
			IAccountService service = (IAccountService) ServerBeanFactory.getBean("accountService");
			List<Address> list = service.findAddressByUserid(userId);
			out.write(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 删除抵用券
	 * @return
	 * @throws IOException
	 */
	public String delCoupon() throws IOException {
		PrintWriter out = response.getWriter();
		try {
			String id = request.getParameter("id");
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			// 删除地址
			service.delCoupon(Long.valueOf(id));
			
			out.write("{\"msg\":\"ok\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"msg\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 删除用户地址
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String delUa() throws IOException {
		PrintWriter out = response.getWriter();
		try {
			String id = request.getParameter("id");
			IAccountService service = (IAccountService) ServerBeanFactory.getBean("accountService");
			// 删除地址
			service.deleteAddressById(Long.valueOf(id));
			
			out.write("{\"result\":\"ok\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 根据条目ID,加载用户地址
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String loadUa() throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String id = request.getParameter("id");
			IAccountService service = (IAccountService) ServerBeanFactory.getBean("accountService");
			Address findAddressById = service.findAddressById(Long.valueOf(id));
			out.write(JSONObject.fromObject(findAddressById).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 查看订单详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String viewOrder() throws IOException {
		try {
			String orderId = request.getParameter("orderId");
			logger.info("查看我的订单--------------orderId===" + orderId);
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			ICarInfoService carService = (ICarInfoService) ServerBeanFactory.getBean("carService");
			Orders order = service.getOrder(Long.valueOf(orderId));
			if(order == null){
				request.setAttribute("info","尚未生成订单， 请耐心等待核保专员反馈。");
				return "info";
			}
			//是否包邮
//			if(order.getCategory() == 0){//只有普通订单才涉及包邮问题，车险订单不涉及
//				if((order.getFeePrice() - order.getTransFeePrice()) >= this.getCurrentFenXiao().getBaoyou()){
//					request.setAttribute("baoyou", "y");
//				}else{
//					request.setAttribute("baoyou", "n");
//				}
//			}else{
//				request.setAttribute("baoyou", "n");
//			}
			request.setAttribute("baoyou", "y");
			
			request.setAttribute("order", order);
//			request.setAttribute("imgServer", Version.getInstance().getImgServer());
			//查询出此订单对应的车险保单信息,就是车主通过公众号填写的信息
			CarInfo carInfo = carService.getCarInfoByOrderNo(order.getId());
			request.setAttribute("carInfo", carInfo);
			
			// 到订单确认页面
//			return ("confirmOrder");
			return ("car-confirmOrder");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查看车险订单详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String viewCarInsurenceOrder() throws IOException {
		try {
			String dbId = request.getParameter("dbId");
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			CarInfo carInfo = service.viewCarInsurenceOrder(Long.valueOf(dbId));
			request.setAttribute("order", carInfo);
			// 到车险订单确认页面，根据普通订单确认页面改造
//			return ("confirmOrder");
			return ("car-insurenceConfirmOrder");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String saveOrder() throws IOException {
		//非法路径
		if(request.getSession().getAttribute(CupidStrutsConstants.FXCODE) == null){
			request.setAttribute("info", "对不起，系统检测到您的请求非法，请重新从菜单进入。");
			return "info";
		}
		
		try {
			Map<String, String> param = new HashMap<String, String>();
			//如果是需要跳转
			if("1".equals(Version.getInstance().getNewProperty("needOrderAddress"))){
				
				// 搜集地址数据
				String accepter = request.getParameter("accepter");
				String address = request.getParameter("address");
				String telephone = request.getParameter("telephone");
				String postcode = request.getParameter("postcode");
				String saveflag = request.getParameter("saveadd");
				String invoiceTitle = request.getParameter("invoiceTitle");
				String buyerMsg = request.getParameter("buyerMsg");
				param.put("accepter", accepter);
				param.put("address", address);
				param.put("telephone", telephone);
				param.put("postcode", postcode);
				param.put("saveflag", saveflag);// 是否作为新地址保存标记
				param.put("invoiceTitle", invoiceTitle);
				param.put("buyerMsg", buyerMsg);
				param.put("destlocation", accepter + "|" + telephone + "|" + address + "|" + postcode);
				
				//需要结账的购物车条目
				if(request.getParameter("scId") != null){
					param.put("scIds", request.getParameter("scId"));
				}
			}else{
				//不需跳转的时候，购物车里勾选的条目ID就需要通过getAttribute方式来获取
				//request.setAttribute("scIds", scIds);
				param.put("scIds", request.getAttribute("scIds").toString());
			}
			if(this.getCurrentUser() != null){
				param.put("userId", this.getCurrentUser().getId().toString());
			}
			
			//是否包含抵扣的红包
			//request.getSession().setAttribute("hbvalue", hb);
			if(request.getSession().getAttribute("hbvalue") != null){
				logger.info("保存订单，有抵扣红包...." + request.getSession().getAttribute("hbvalue"));
				param.put("hbvalue", request.getSession().getAttribute("hbvalue").toString() );
			}
			
			//把分销商code传递过去
			param.put("fxCode", request.getSession().getAttribute(CupidStrutsConstants.FXCODE).toString());
			//如果是从分销客转发的URL进入的，session中会有agentId
			if(request.getSession().getAttribute("agentId") != null){
				param.put("agentId", request.getSession().getAttribute("agentId").toString());
			}
			
			
			
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			Orders saveOrder = service.saveOrder(param);
			request.setAttribute("order", saveOrder);
			
			//是否包邮
//			if((saveOrder.getFeePrice() - saveOrder.getTransFeePrice()) >= this.getCurrentFenXiao().getBaoyou()){
//				request.setAttribute("baoyou", "y");
//			}else{
//				request.setAttribute("baoyou", "n");
//			}
			request.setAttribute("baoyou", "y");
			
			// 重置购物车cartCount
			request.getSession().setAttribute(MarkConstants.CART_COUNT, "0");
			logger.info("saveOrder end...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 到订单确认页面
//		return ("confirmOrder");
		return ("car-confirmOrder");
	}

	/**
	 * 会员中心
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String MemCenter() throws IOException {
		String openId = request.getParameter("openId");
		if (openId == null) {
			if (this.getCurrentUser() != null) {
				openId = this.getCurrentUser().getOpenId();
			}
		}
		logger.info("进入会员中心....openId=" + openId);
		if (openId != null) {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			PublicService srv = (PublicService) ServerBeanFactory.getBean("publicService");
			User user = service.getUserByOpenId(openId);
			if (user != null) {
				// 绑定过了，正常访问
				request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, user);
				// 加载购物车的
				request.getSession().setAttribute("cartCount", srv.getShoppingCartPrdList(user.getId()).size());
			} else {
				// 跳转到会员注册页面
//				return new String("/jms/userReg.jsp?openId=" + openId);
				return  "userReg";
			}
			request.setAttribute("user", user);
		} else {
			if (this.getCurrentUser() != null) {// session里有
				request.setAttribute("user", this.getCurrentUser());
			} else {
				// 到登录页面
				request.setAttribute("info", "会话超时，请重新登录！");
				return "wxlogin";
			}
		}
//		return "userCenter";
		return "userCenter-car";
	}

	/**
	 * 保存个人信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String saveUserInfo() throws IOException {
		try {
			// 搜集用户个人信息
			String username = request.getParameter("username");
			String oldpwd = request.getParameter("oldpwd");
			String newpwd = request.getParameter("newpwd");
			String confirmpwd = request.getParameter("confirmpwd");
			String telephone = request.getParameter("telephone");
			String birthday = request.getParameter("birthday");
			if (!newpwd.equals(confirmpwd)) {
				request.setAttribute("info", "对不起，两次密码输入不一致。");
				return ("info");
			}
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			User u = service.getUserByNameAndPwd(username, oldpwd);
			if (u != null) {
				u.setPassword(newpwd);
				u.setTelephone(telephone);
				// u.setBirthday(birthday);
				service.updateUser(u);
			} else {
				request.setAttribute("info", "对不起，用户名或密码错误。");
				return ("info");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 到会员中心页面
		return this.MemCenter();
	}

	

	/**
	 * 加载所有用户地址
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String loadAllUserAddress() throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			// 删除地址
			List<Address> list = service.loadAllUserAddress(this.getCurrentUser().getId());
			out.write(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 取消订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String cancelOrder() throws IOException {
		PrintWriter out = response.getWriter();
		try {
			String orderId = request.getParameter("orderId");
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			service.cancelOrder(Long.valueOf(orderId));
			out.write("{\"result\":\"ok\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 取消车险订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String cancelCarOrder() throws IOException {
		PrintWriter out = response.getWriter();
		try {
			String orderId = request.getParameter("orderId");
			ICarInfoService service= (ICarInfoService) ServerBeanFactory.getBean("carService");
			service.cancelCarInsurenceOrder(Long.valueOf(orderId));
			out.write("{\"result\":\"ok\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 订单失败
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String orderFail() throws IOException {
		PrintWriter out = response.getWriter();
		try {
			String orderNum = request.getParameter("orderNum");
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			service.orderFail(orderNum);
			out.write("{\"result\":\"ok\"}");
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 后台生成支付相关的参数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getWxPayParam() throws IOException {
		PrintWriter out = response.getWriter();
		try {
			WxPayParamn wpp = new WxPayParamn();
			String openId = this.getCurrentUser().getOpenId();
			payLogger.info("注册会员openId：openId=" + openId);
			//当前分销商
			Org org = this.getCurrentFenXiao();
			
			// 订单号
			String orderNum = request.getParameter("orderNum");
			// 第一步：通过统一支付URL获取prepay_id
			String prepay_id = WxPayUtil.getPrePayId(org, openId, orderNum);
			payLogger.info("final get prepay_id==" + prepay_id);
			if(prepay_id == null){
				out.write("{\"result\":\"fail\"}");
				return null;
			}
			payLogger.info("org.getAppid()==" + org.getAppid());
			wpp.setAppId(org.getAppid());
			wpp.setNonceStr(WxPayUtil.nonceStr);
			wpp.setPckage("prepay_id=" + prepay_id);
			wpp.setSignType("MD5");
			String timeStr = new Date().getTime()/1000 + "";
			wpp.setTimeStamp(timeStr);
			String[] keys = { "appId", "timeStamp", "nonceStr", "package", "signType" };
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("appId", org.getAppid());
			map.put("timeStamp", timeStr);
			map.put("nonceStr", WxPayUtil.nonceStr);
			map.put("package", "prepay_id=" + prepay_id);
			map.put("signType", "MD5");
			wpp.setPaySign(WxPayUtil.wxpaySign(keys, map,  org.getPayKey()));
			out.write(JSONObject.fromObject(wpp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 用户确认收货 
	 * @return
	 * @throws IOException 
	 */
	public String confirmGet() throws IOException{
		PrintWriter out = response.getWriter();
		try {
			// 订单号
			String orderId = request.getParameter("orderId");
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			boolean b = service.confirmGet(orderId);
			if(b){
				out.write("{\"result\":\"ok\"}");
			}else{
				out.write("{\"result\":\"fail\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	
	/**
	 * 拿到微信JS接口的参数 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String  getWxJsParam() throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		long t1 = System.currentTimeMillis();
		logger.info("in getWxJsParam------------");
		try{
			WxPayParamn wpp = new WxPayParamn();
			String apiTicket = WeiXinUtil.getJSApiTicket(this.getCurrentFxCode());
			//-----------------计算签名----------------------------------
//			签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。
//			对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
//			这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
//			即signature=sha1(string1)。 示例：
//			    noncestr=Wm3WZYTPz0wzccnW
//			    jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
//			    timestamp=1414587457
//			    url=http://mp.weixin.qq.com?params=value 
//			步骤1. 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1：
//			jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value
//
//			步骤2. 对string1进行sha1签名，得到signature：
//			0f9de62fce790f9a083d5c99e95740ceb90c27ed
//----------------------------------------------------------------------------------------------------
			String nonceStr = "lookingfordrug";
			String timeStr = new Date().getTime()+"";
			String[] keys = {"jsapi_ticket", "timestamp", "noncestr", "url"};
			Map<String, String> map = new HashMap<String, String>();
			map.put("jsapi_ticket", apiTicket);
			String timestamp = timeStr.substring(0, timeStr.length()-3);
			map.put("timestamp", timestamp);
			map.put("noncestr", nonceStr);
			map.put("url",  request.getParameter("url") );
			//微信JS签名算法
			wpp.setPaySign(WxPayUtil.wxJsSign(keys, map));
			//当前分销商的appid
			wpp.setAppId(this.getCurrentFenXiao().getAppid());
			wpp.setTimeStamp(timestamp);
			wpp.setNonceStr(nonceStr);
			logger.info("url=" + request.getParameter("url"));
			logger.info("wpp.jsapi_ticket()=" + apiTicket);
			logger.info("wpp.getPaySign()=" + wpp.getPaySign());
			logger.info("wpp.getTimeStamp()=" + wpp.getTimeStamp());
			logger.info("wpp.getAppId()=" + wpp.getAppId());
			String string = JSONObject.fromObject(wpp).toString();
			logger.info(string);
			out.write(string);
		}catch(Exception e){
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		logger.info("in getWxJsParam------------end---" + (System.currentTimeMillis() - t1) + "ms");
		return null;
	}

	/**
	 * 保存收货地址
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String saveUserAddress() throws IOException {
		String op = request.getParameter("op");
		try {
			Map<String, String> param = new HashMap<String, String>();
			String label = request.getParameter("label");
			String accepter = request.getParameter("accepter");
			String telephone = request.getParameter("telephone");
			String address = request.getParameter("address");
			String postcode = request.getParameter("postcode");
			String isdefault = request.getParameter("isdefault");
			// 处理省市县
			String pcc = request.getParameter("pcc");
			// 当前登录用户ID
			String userId = this.getCurrentUser().getId().toString();
			logger.info("label=" + label);
			logger.info("accepter=" + accepter);
			logger.info("telephone=" + telephone);
			logger.info("address=" + pcc + "|" + address);
			logger.info("postcode=" + postcode);
			logger.info("isdefault=" + isdefault);
			param.put("label", label);
			param.put("accepter", accepter);
			param.put("telephone", telephone);
			param.put("address", pcc + address);
			param.put("postcode", postcode);
			param.put("isdefault", isdefault);
			param.put("userId", userId);
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			if ("save".equals(op)) {
				service.saveUserAddress(param);
			} else if ("update".equals(op)) {// 编辑
				logger.info("更新收货地址..........................");
				String uaId = request.getParameter("uaId");
				param.put("uaId", uaId);
				service.updateAddress(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String("/jms/useradd.jsp?op=" + op);
	}

	/**
	 * 在结账前 更新购物车 这个方法只需要更新数量即可，因为其他操作在页面上都通过ajax做掉了
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String updateCart() throws IOException {
		String scIds = "";//用来传递到下个页面的shippingCart Id
		try {
			// 购物车里的条目ID
			Map<String, String> param = new HashMap<String, String>();
			String[] ids = request.getParameterValues("cks");
			for (String id : ids) {
				param.put(id, request.getParameter("buycount" + id));
				scIds += (id + ",");
			}
			
			//加入红包的抵扣,放入session中
			String hb = request.getParameter("hb");
			if(hb != null){
				logger.info("hb--抵扣的红包---" + hb);
				request.getSession().setAttribute("hbvalue", hb);
			}
			
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			service.updateCart4Count(param);
			
			request.setAttribute("scIds", scIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 到配送地址确认页
//		return ("confirmAddress");
		//是否需要收货地址，不需要的话，不跳转
		if("1".equals(Version.getInstance().getNewProperty("needOrderAddress"))){
			return ("car-confirmAddress");
		}else{
			return this.saveOrder();
		}
		
	}

	/**
	 * 商品评价
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String pj() throws IOException {
		try {
			// orderId
			String orderId = request.getParameter("orderId");
			String prdCode = request.getParameter("prdCode");
			String odId = request.getParameter("odId");
			if (orderId == null || prdCode == null || odId == null) {
				request.setAttribute("info", "对不起, 参数不正确。");
				return ("info");
			}
			// 评分
			String pjscore = request.getParameter("pjscore");
			// 评价内容
			String pjContent = request.getParameter("pjContent");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orgId", this.getCurrentFenXiao().getId()+"");
			paramMap.put("orderId", orderId);
			paramMap.put("prdCode", prdCode);
			paramMap.put("odId", odId);
			paramMap.put("pjscore", pjscore);
			paramMap.put("pjContent", pjContent);
			paramMap.put("userId", this.getCurrentUser().getId().toString());
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			service.pj(paramMap);
			// 到订单页
			Orders order = service.getOrder(Long.valueOf(orderId));
			request.setAttribute("order", order);
			return ("confirmOrder");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "对不起，出错了：" + e.getMessage());
			return ("info");
		}
	}

	/**
	 * 加载评价列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String loadPj() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		// 查询参数
		// Map<String, String> paramMap = getParamMap(request);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prdId", request.getParameter("prdId"));
		try {
			Map<String, Object> map = service.loadPj(start, Integer.valueOf(limit).intValue(), paramMap);
			List<Pj> dataList = (List<Pj>) map.get("list");
			int total = Integer.valueOf(map.get("total").toString());
			pp.setPage(Integer.valueOf(page));// 当前页码
			pp.setLimit(Integer.valueOf(limit));// 每页显示条数
			if (!dataList.isEmpty()) {
				pp.setTotal(total);
				// 总页数
				if (total % pp.getLimit() != 0) {
					pp.setTotalPage(total / pp.getLimit() + 1);
				} else {
					pp.setTotalPage(total / pp.getLimit());
				}
				logger.info("数据页数：" + pp.getTotalPage());
				pp.setDatas(dataList);
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
	
	
	public String loadMyCoupon() throws IOException{
		response.setCharacterEncoding("UTF-8");
		ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
		List<Coupon> couponList = service.getMyCoupon(this.getCurrentAdminUser().getOpenId());
		PrintWriter out = response.getWriter();
		if (!couponList.isEmpty()) {
			out.write(JSONArray.fromObject(couponList).toString());
		} else {
			out.write("{\"result\":\"empty\"}");
		}
		return null;
	
	}
	
	

	public String loadDeliverFee() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		float fee = service.getDeliverFee(this.getCurrentFxCode());
		out.print("{\"result\":\"" + fee + "\"}");
		return null;
	}
	
	/**
	 * 我的分销首页
	 * @return
	 */
	public String myFenXiao(){
		request.setAttribute("user", this.getCurrentUser());
		return "myfenxiao";
	}
	
	/**
	 * 分销订单首页
	 * 查找当前用户的所有分销订单
	 * @return
	 */
	public String fxdd(){
		IFenxiaoService service = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
		List<Orders> fxkOrders = service.getFxkOrders(this.getCurrentUser());
		
		return "fxyj";
	}
	
	/**
	 * 分销团队首页
	 * @return
	 */
	public String fxtd(){
		request.setAttribute("user", this.getCurrentUser());
		return "fxyj";
	}
	/**
	 * 分销商首页， 需要读取配置 
	 * @return
	 */
	public String joinUs(){
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		FxApplyConfig fac =  service.getFxApplyConfig(this.getCurrentFxCode());
		if(fac == null){
			fac = new FxApplyConfig();
		}
		request.setAttribute("fac", fac);
		return "joinus";
	}
	
	/**
	 * 加载分销客信息 
	 * @return
	 * @throws IOException 
	 */
	public String loadFxkInfo() throws IOException{
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		if(this.getCurrentUser().getFlag() == 0){
			//还不是分销客
			out.print("{\"result\":\"fail\"}");
		}else{
			//去后台计算佣金等信息
			Map<String, String> res = service.getFxkInfo(this.getCurrentUser());
			JSONObject jo = new JSONObject();
			jo.put("result", "ok");//ok
			jo.put("yj", res.get("yj"));//佣金，单位：分
			jo.put("allyj", res.get("allyj"));//累计佣金，单位：分
			jo.put("orderSize", res.get("orderSize"));//分销订单数
			jo.put("subcnt", res.get("subcnt"));//下线数，直接下线。
			jo.put("customers", res.get("customers"));//我的客户
			out.print(jo.toString());
		}
		return null;
	}
	
	
	/**
	 * 分销客提交提现申请 
	 * @return
	 * @throws IOException
	 */
	public String cashApply() throws IOException{
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		String applyFee = request.getParameter("applyFee");
		try{
			//判断余额
			Long yj = service.getYj(this.getCurrentUser(), 0);
			if(Long.valueOf(applyFee).longValue() > yj.longValue()){
				out.print("{\"result\":\"nofee\"}");//余额不足
				return null;
			}
			
			CashApply ca = new CashApply();
			ca.setAtype(0);//分销客
			ca.setUserId(this.getCurrentUser().getId());
			ca.setFlag(0);//状态：0-申请中  1-已处理  2-已拒绝 
			ca.setApplyDate(DateUtil.getLongCurrentDate());
			ca.setApplyFee(Long.valueOf(applyFee) * 100);
			ca.setNickName(this.getCurrentUser().getNickName());
			ca.setOrgCode(this.getCurrentFxCode());
			//根据当前分销商代码获取顶级，取前3位即可
			ca.setTopOrgCode(this.getCurrentFxCode().substring(0,3));
			service.cashApply(ca);
			
			out.print("{\"result\":\"ok\"}");
		}catch(Exception e){
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	

	/**
	 * 加载汽车品牌 
	 * @return
	 * @throws IOException
	 */
	public String loadCarBrands() throws IOException{
		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		try{
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			List<String> allCarBrands = service.getAllCarBrands();
			jo.put("msg", "ok");
			jo.put("result", allCarBrands);
			out.print(jo.toString());
		}catch(Exception e){
			e.printStackTrace();
			jo.put("msg", "fail");
			out.print(jo.toString());
		}
		
		return null;
	}
	
	
	/**
	 * 加载车辆型号
	 * @return
	 * @throws IOException 
	 */
	public String loadCarTypes() throws IOException{
		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		try{
			String brandName = request.getParameter("brandName");
			ICarInfoService service = (ICarInfoService) ServerBeanFactory.getBean("carService");
			List<String> allCarTypes = service.loadAllCarTypes(brandName);
			jo.put("msg", "ok");
			jo.put("result", allCarTypes);
			out.print(jo.toString());
		}catch(Exception e){
			e.printStackTrace();
			jo.put("msg", "fail");
			out.print(jo.toString());
		}
		
		return null;
	}
	
	private CarInfo carInfo = new CarInfo();

	public CarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
	}
	/**
	 * 提交车辆信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String submitCarInfo() throws IOException {
		response.setCharacterEncoding("UTF-8");
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			service.saveCarInfo(carInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//表示成功
		request.setAttribute("flag", "ok");
		return "submitCarInfoFinish";
	}
	
	
	public static void main(String[] args) {
		// DecimalFormat df = new DecimalFormat("00000");
		// Number parse = df.parse("03010");
		// System.out.println("parse=" + parse.intValue());
//		UUID uuid = UUID.randomUUID();
//		String snum = uuid.toString();
		String timeStr = new Date().getTime()+"";
		
		System.out.println(timeStr.substring(0, timeStr.length()-3));
	}
}
