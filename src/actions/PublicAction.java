package actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import model.GameReport;
import model.Paging;
import model.bo.AboutUs;
import model.bo.Columns;
import model.bo.Contacts;
import model.bo.Experts;
import model.bo.Gift;
import model.bo.OnlineTests;
import model.bo.PublishedStudy;
import model.bo.PublishedTesting;
import model.bo.Question;
import model.bo.ShowCase;
import model.bo.TP;
import model.bo.Topics;
import model.bo.Training;
import model.bo.Vote;
import model.bo.auth.Org;
import model.bo.fenxiao.OneProduct;
import model.bo.food.ShoppingCart;
import model.bo.food.Spreader;
import model.bo.wxmall.Pj;
import model.vo.WxUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.intf.AdminService;
import service.intf.ICarInfoService;
import service.intf.PublicService;
import util.SysUtil;
import actions.integ.weixin.WeiXinUtil;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.constants.CupidStrutsConstants;
import com.wfsc.common.bo.ad.AdvConfig;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.ad.IAdService;
import com.wfsc.services.product.IHotelService;
import com.wfsc.services.product.IProductsService;
import com.wfsc.services.productcat.IProductCatService;
import com.wfsc.util.DateUtil;

import constants.MarkConstants;

@Controller("PublicxAction")
@Scope("prototype")
public class PublicAction extends DispatchPagerAction {

	private static final long serialVersionUID = -3503415849917552421L;

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	/**
	 * 用户登录 用户点击菜单后，会
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		// 身份证
		String idcard = request.getParameter("idcard");
		// 用户名
		String username = request.getParameter("username");
		// String city = request.getParameter("city");
		// String hospital = request.getParameter("hospital");
		String openId = request.getParameter("openId");
		//验证码
		String codeImage = request.getParameter("codeImage");
		// String openId =
		// "1234";//==============================================>>测试代码
		// String telephone = request.getParameter("telephone");
		logger.info("in LOGIN --------  codeImage=" + codeImage + " | username=" + username + "|" + openId);
		if(codeImage.equalsIgnoreCase(request.getSession().getAttribute(CupidStrutsConstants.CODE_IMAGE_LOGIN).toString())){
			request.setAttribute("info", "对不起，验证码不正确！");
			return new String("/weixin/login.jsp");
		}
		
		PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
		Map<String, String> param = new HashMap<String, String>();
		param.put("username", username);
		param.put("idcard", idcard);
		param.put("openId", openId);
		// 判断openId
		if (openId == null || "null".equals(openId) || StringUtils.isEmpty(openId)) {
			request.setAttribute("info", "对不起，非法登录！");
			return new String("/weixin/login.jsp");
		}
//		if (!SysUtil.IsIDcard(idcard)) {
//			request.setAttribute("info", "对不起，请输入正确的身份证号码！");
//			return new String("/weixin/login.jsp");
//		}
		try {
			User u = service.login(param);
			if (u != null) {
				request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, u);
				logger.info("普通用户登录成功...");
			} else {
				request.setAttribute("info", "对不起，您的登录信息不正确！");
				return new String("/weixin/login.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "对不起，登录失败，请重试！");
			return new String("/weixin/login.jsp");
		}
		// 分辨PC还是手机
		String userAgent = request.getHeader("User-Agent");
		logger.info("客户端类型：" + userAgent);
		// if (!WeiXinUtil.isFromMobile(userAgent)) {//非移动端
		// return new String("/pc/index.jsp");
		// }else{
		// return new String("/main/index.jsp?login=y&openId=" + openId, true);
		// }
		// 登录后跳转到邮政咨询页面
		// 加载邮政咨询的栏目
		return yzindex();
	}

	/**
	 * 微商城用户登录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String doLogin() throws Exception {
		String username = request.getParameter("username");
		String pwd = request.getParameter("passwd");
		logger.info(username + "===用户登录............");
		String userAgent = request.getHeader("User-Agent");
		logger.info("客户端类型：" + userAgent);
		if ("0".equals(Version.getInstance().getNewProperty("wxtest"))) {
			if (!WeiXinUtil.isFromMobile(userAgent)) {// 非移动端
				request.setAttribute("info", "请从微信登录！");
				return ("info");
			}
		}
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		User u = service.getUserByNameAndPwd(username, pwd);
		if (u != null) {
			session.put(CupidStrutsConstants.SESSION_USER, u);
			PublicService pservice = (PublicService) ServerBeanFactory.getBean("publicService");
			List<ShoppingCart> list = pservice.getShoppingCartPrdList(this.getCurrentUser().getId());
			// 购物车里的数量
			request.getSession().setAttribute(MarkConstants.CART_COUNT, list.size());
			
			//更新登录时间
			u.setLastLogin(new Date());
			service.updateUser(u);
		} else {
			request.setAttribute("info", "对不起，用户名或密码错误！");
			return ("info");
		}
		return "index";
	}

	/**
	 * 微店网关。。。。。。。。。。。。。 通过自定义菜单，给定一个入口， 这个入口经过此Action， 因为openId会带过来，所以知道是否已经绑定过
	 * <li>如果绑定过，则直接自动登录
	 * <li>如果未绑定，后续一些操作将会提示绑定。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String storeGateway() throws Exception {
		String openId = request.getParameter("openId");
		logger.info(openId + "===商城首页............");
		if (openId == null) {
			request.setAttribute("info", "对不起，非法入口，请从微信公众号访问！");
			return ("info");
		}
		// 查询是否绑定
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		User u = service.getUserByOpenId(openId);
		if (u != null) {
			request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, u);
		}
		// 微商城首页
		return new String("/jms/index.jsp");
	}

	/**
	 * 商品入口 通过自定义菜单，给定一个入口， 这个入口经过此Action， 因为openId会带过来，所以知道是否已经绑定过
	 * <li>如果绑定过，则直接自动登录
	 * <li>如果未绑定，后续一些操作将会提示绑定。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String productGateway() throws Exception {
		try{
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
//			if(!Version.getInstance().getNewProperty("wxtest").equals("y")){
//				if(request.getSession().getAttribute(CupidStrutsConstants.WXOPENID) == null){
//					request.setAttribute("info", "对不起，非法入口，请从微信公众号访问！");
//					return ("info");
//				}
//			}
			//转发的情况， 有org带过来， 需要重新设置一下session里的CupidStrutsConstants.FXCODE
			if(request.getParameter("orgId") != null){
				Org orgById = service.getOrgById(Long.valueOf(request.getParameter("orgId")));
				request.getSession().setAttribute(CupidStrutsConstants.FXCODE, orgById.getCode());
			}
			
			String openId = "";
			//测试代码
			if(Version.getInstance().getNewProperty("wxtest").equals("y")){
				openId = "1111";
			}else{
				if (this.getCurrentUser() == null) {
					openId = request.getSession().getAttribute(CupidStrutsConstants.WXOPENID).toString();
				} else {
					openId = this.getCurrentUser().getOpenId();
				}
			}
			// 商品分类
			String productCat = request.getParameter("productCat");
			// 产品ID
			String prdId = request.getParameter("prdId");
			String prdCode = request.getParameter("prdCode");
			logger.info(openId + "===商品分类..........." + productCat + "...prdId=" + prdId + "  prdCode=" + prdCode);
			if (openId == null) {
				request.setAttribute("info", "对不起，非法入口，请从微信公众号访问！");
				return ("info");
			} else {
				request.setAttribute("openId", openId);
			}
			
			User u = service.getUserByOpenId(openId);
			if (u != null) {
				request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, u);
				request.setAttribute("nobind", "0");
			} else {
				// 不是会员，第三方点击链接
				request.setAttribute("nobind", "1");
			}
			Products p = null;
			if (productCat == null) {
				if(prdId != null && !"undefined".equals(prdId) && !"null".equalsIgnoreCase(prdId)){
					p = service.getProductById(Long.valueOf(prdId));
				}else{
					p = service.getProductByCode(prdCode);
				}
			} else {
				// 显示商品信息
				p = service.getProductByGuige(productCat);
			}
			if (p == null) {
				request.setAttribute("info", "对不起，该产品不存在！");
				return ("info");
			}
			request.setAttribute("product", p);
			// 加载展示图片
			List<String> pics = new ArrayList<String>();
			String filePath = getProductPicPath(request, "/Prd" + p.getId() + "/");
			File f = new File(filePath);
			if (f.exists()) {
				File[] listFiles = f.listFiles();
				for (File file : listFiles) {
					if (file.getName().indexOf("-thumb") == -1) {
						pics.add("/private/UploadImages/Prd" + p.getId() + "/" + file.getName());
					}
				}
			}
			request.setAttribute("picList", pics);
			// 判断是不是分销客 TODO
			String agent = request.getParameter("agent");
			if(agent != null){//是分销客转发的链接
				
			}
			
			//加载优惠券所属门店信息
			Admin adminById = service.getAdminById(p.getServiceId().toString());
			if(adminById != null){
				request.setAttribute("storeAddress", adminById.getCompany());
				request.setAttribute("phone", adminById.getPhone());
			}
			
			// 产品详情页
//			return "wxProductDetail";
			//车险平台
			return "wxCarProductDetail";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 登录后首页：邮政咨询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String yzindex() throws Exception {
		String t = request.getParameter("t");
		if (t == null) {
			t = "1";
		}
		// 登录后跳转到邮政咨询页面
		// 加载邮政咨询的栏目
		AdminService as = (AdminService) ServerBeanFactory.getBean("adminService");
		List<Columns> cols = as.getAllColumns(t);
		request.setAttribute("cols", cols);
		// 统计信息
		as.updateStatistics("yzindex");
		return new String("/wx/yzinfo.jsp");
	}

	/**
	 * 查看邮政咨询详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String viewInfoDetail() throws Exception {
		String id = request.getParameter("id");
		if (id == null) {
			request.setAttribute("info", "对不起，参数不正确！");
			return ("error");
		}
		AdminService as = (AdminService) ServerBeanFactory.getBean("adminService");
		ShowCase sc = as.getYzInfoDetail(Long.valueOf(id));
		request.setAttribute("sc", sc);
		return new String("/wx/yzinfoDetail.jsp");
	}

	/**
	 * 点赞
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String zan() throws Exception {
		try {
			// 文章ID
			String targetId = request.getParameter("caseId");
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			boolean b = service.zan(request.getSession().getId(), this.getCurrentUser(), targetId);
			if (b) {
				response.getWriter().write("1");
			} else {
				// 不能连续点赞
				response.getWriter().write("-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		}
		return null;
	}

	/**
	 * 签到
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String sign() throws Exception {
		if (request.getSession().getAttribute(CupidStrutsConstants.SESSION_USER) == null) {
			// 未登录
			request.setAttribute("info", "对不起，请登录后再操作！");
			return ("login");
		}
		try {
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			int s = service.sign(this.getCurrentUser());
			response.getWriter().write(s + "");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		}
		return null;
	}

	/**
	 * 微信端统一入口方法 /wx/xxx.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String gotoPage() throws Exception {
		logger.info("in gotoPage.........................." + DateUtil.getLongCurrentDate());
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		if (request.getSession().getAttribute(CupidStrutsConstants.SESSION_USER) == null) {
			// 判断是否是绑定用户
			String openId = request.getParameter("openId");
			logger.info("openId==" + openId);
			request.getSession().setAttribute(MarkConstants.SES_OPENID, openId);
			User userByOpenId = service.getUserByOpenId(openId);
			logger.info("userByOpenId==" + userByOpenId);
			if (userByOpenId != null) {// 已经登录过
				request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, userByOpenId);
			} else {
				// 把openId传递到登录页面，非常重要
				request.setAttribute("openId", openId);
				// 未绑定过
				request.setAttribute("info", "对不起，请登录后再操作！");
				return ("login");
			}
		}
		try {
			String page = request.getParameter("page");
			logger.info("page====" + page);
			// 特殊页面处理，bizStudy
			if (page.startsWith("bizStudy")) {
				// http://60.12.228.130/yzwx/public.do?method=gotoPage&page=bizStudy.jsp#2&openId=oX4mujnnQcPASy5TrT
				String destPage = "/wx/" + StringUtils.split(page, "-")[0] + "?t=" + StringUtils.split(page, "-")[1];
				logger.info("destPage====" + destPage);
				// 统计信息
				service.updateStatistics(destPage);
				return new String(destPage);
			} else {
				if (!page.endsWith(".jsp") && !page.endsWith(".htm") && !page.endsWith(".html")) {
					request.setAttribute("info", "对不起，您请求的页面不存在！");
					return ("info");
				}
				// 统计信息
				service.updateStatistics(page);
				response.sendRedirect(request.getContextPath() + "/weixin/" + page);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 开始答题：在线测试 从在线测试引导页上点击：【开始答题】后进入此方法。<br>
	 * 针对该试卷需要生成唯一序列号。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String startTest() throws Exception {
		try {
			UUID uuid = UUID.randomUUID();
			String snum = uuid.toString();
			request.setAttribute("snum", snum);
			String testId = request.getParameter("testId");
			if (testId != null) {
				// 查出所有的在线考试题
				AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
				List<PublishedTesting> publishedTesting = service.getOnlineTests(Long.valueOf(testId));
				request.setAttribute("list", publishedTesting);
				return new String("/wx/onlineTest.jsp?testId=" + testId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 在线辅导开始
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String startDemoStudy() throws Exception {
		try {
			UUID uuid = UUID.randomUUID();
			String snum = uuid.toString();
			request.setAttribute("snum", snum);
			// 查出所有的在线辅导题
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			List<PublishedStudy> publishedStudy = service.getPublishedStudy();
			request.setAttribute("list", publishedStudy);
			return new String("/wx/onlineStudy.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 开始调查
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String startVote() throws Exception {
		try {
			// 查出所有的在线考调查
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			// 获取调查问卷
			Vote v = service.getActiveVote();
			request.setAttribute("v", v);
			return new String("/wx/onlineVote.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 提交测试结果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String submitTest() throws Exception {
		try {
			Object snum = request.getAttribute("snum");
			return new String("/wx/onlineTest.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 抢沙发 抢沙发：中午十二点到十二点零五分，期间前五名为抢沙发有效
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String sofa() throws Exception {
		if (request.getSession().getAttribute(CupidStrutsConstants.SESSION_USER) == null) {
			// 未登录
			request.setAttribute("info", "对不起，请登录后再操作！");
			return ("login");
		}
		try {
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			// 抢沙发，返回1即为第一名，成功，否则返回当前名次
			int rank = service.sofa(this.getCurrentUser());
			response.getWriter().write(rank + "");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		}
		return null;
	}

	/**
	 * 英雄榜， AJAX
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String heroList() throws Exception {
		try {
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			List<User> list = service.getHeroList();
			JSONArray fromObject = JSONArray.fromObject(list);
			String string = fromObject.toString();
			response.getWriter().write(string);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		}
		return null;
	}

	/**
	 * 根据OPENID加载用户信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String loadUserInfo() throws Exception {
		try {
			String openId = request.getParameter("openId");
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			User u = service.getUserByOpenId(openId);
			if (u != null) {
				logger.info("111-- " + openId + " u.getUsername()=" + u.getUsername() + "  u.getTelephone()= " + u.getTelephone());
				// 如果已经存在，并且手机号码和用户名已经填写，则直接进入
				if (u.getUsername() != null && u.getTelephone() != null) {
					logger.info("直接登录...");
					// 更新最后登录时间
					u.setLastLogin(new Date());
					service.updateUser(u);
					request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, u);
					logger.info("普通用户【" + u.getUsername() + "】直接登录成功...");
					JSONObject json = new JSONObject();
					json.put("result", "pass");
					json.put("openId", openId);
					response.getWriter().write(json.toString());
					return null;
				} else {
					JSONObject json = new JSONObject();
					json.put("result", "fail");
					json.put("openId", openId);
					response.getWriter().write(json.toString());
				}
			} else {
				JSONObject json = new JSONObject();
				json.put("result", "fail");
				json.put("openId", openId);
				response.getWriter().write(json.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
			return null;
		}
		return null;
	}

	/**
	 * getResumeList
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getShowCase() throws Exception {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "1" : request.getParameter("page").toString();
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

	/**
	 * 用户注册\绑定 当未注册用户访问个人中心时，需要跳转到此页面。 输入信息有： 昵称， 电话， 邮箱
	 * 
	 * 点击个人中心任何菜单，均推送一个url地址，该地址带上openId号。只有注册后的用户才能继续访问，否则将进入提示注册页面。
	 */
	public String doRegBind() throws Exception {
		try {
			// 提问
			String openId = request.getParameter("openId");
			String nickName = request.getParameter("nickName");
			String telephone = request.getParameter("telephone");
			String email = request.getParameter("email");
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("openId", openId);
			paramMap.put("nickName", nickName);
			paramMap.put("telephone", telephone);
			paramMap.put("email", email);
			service.doRegisterBind(paramMap);
			response.getWriter().write("ok");
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		}
		return null;
	}

	/**
	 * 显示专家
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getExperts() throws Exception {
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
		try {
			Map<String, Object> map = service.getExperts(start, Integer.valueOf(limit).intValue());
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

	/**
	 * 查看文章详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String viewCase() throws Exception {
		try {
			String caseId = request.getParameter("id");
			if (caseId == null) {
				request.setAttribute("info", "对不起，参数不正确！");
				return ("error");
			}
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			ShowCase sc = service.getShowCase(caseId);
			if (sc != null) {
				request.setAttribute("sc", sc);
			}
			request.setAttribute("sc", sc);
			return new String("/wx/yzinfoDetail.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "对不起，出错了！");
			return ("error");
		}
		// //分辨PC还是手机
		// String userAgent = request.getHeader("User-Agent");
		// logger.info("客户端类型：" + userAgent);
		// if (!WeiXinUtil.isFromMobile(userAgent)) {//非移动端
		// return ("articlePc");
		// }else{
		// return ("article");
		// }
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String viewExpert() throws Exception {
		try {
			String id = request.getParameter("id");
			if (id == null) {
				request.setAttribute("info", "对不起，参数不正确！");
				return ("error");
			}
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			Experts expert = service.getExpert(id);
			if (expert != null) {
				request.setAttribute("expert", expert);
			}
			String pc = request.getParameter("pc");
			if ("y".equals(pc)) {
				return ("expertPc");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "对不起，出错了！");
			return ("error");
		}
		return ("expert");
	}

	/**
	 * 获得当月话题列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getTopics() throws IOException {
		String year = request.getParameter("dateym");
		if (year == null) {
			year = DateUtil.getShortCurrentDate().substring(0, 4);
		}
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		List<Topics> questionsByDate = service.topics(Integer.valueOf(year));
		PrintWriter out = response.getWriter();
		if (!questionsByDate.isEmpty()) {
			out.write(JSONArray.fromObject(questionsByDate).toString());
		} else {
			out.write("{\"result\":\"empty\"}");
		}
		return null;
	}

	/**
	 * 获得礼品列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getGifts() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		List<Gift> gifts = service.getGifts();
		PrintWriter out = response.getWriter();
		if (!gifts.isEmpty()) {
			out.write(JSONArray.fromObject(gifts).toString());
		} else {
			out.write("{\"result\":\"empty\"}");
		}
		return null;
	}
	
	/**
	 *  加载积分商品详情
	 * @return
	 * @throws IOException
	 */
	public String loadGiftDetail() throws IOException {
		response.setCharacterEncoding("UTF-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Long giftId = Long.valueOf(request.getParameter("giftId"));
		Gift gift = service.getGiftById(giftId);
		PrintWriter out = response.getWriter();
		if (gift != null) {
			JSONObject jo = new JSONObject();
			jo.put("msg", "ok");
			jo.put("gift", gift);
			out.write(JSONObject.fromObject(jo).toString());
		} else {
			out.write("{\"msg\":\"empty\"}");
		}
		return null;
	}
	
	/**
	 * 获取本人预订的记录
	 * @return
	 * @throws IOException
	 */
	public String getMyBookedRecord() throws IOException {
		response.setCharacterEncoding("UTF-8");
		IHotelService service = (IHotelService) ServerBeanFactory.getBean("hotelService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		// 查询参数
		// Map<String, String> paramMap = getParamMap(request);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prdId", request.getParameter("prdId"));
		try {
			Paging pp = service.getMyBookedRecords(Integer.valueOf(page), Integer.valueOf(limit).intValue(), paramMap);
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
	 * 邮政咨询：子栏目的条目加载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getYzinfoList() throws IOException {
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
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 培训信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getTrainingList() throws IOException {
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
			Map<String, Object> map = service.getTrainingList(start, Integer.valueOf(limit).intValue());
			List<Training> resumeList = (List<Training>) map.get("list");
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
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 获得当前用户剩余积分
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getScore() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		int s = service.getScoreById(this.getCurrentUser().getId());
		PrintWriter out = response.getWriter();
		out.write(s + "");
		return null;
	}

	// 在线考试试题总数
	public String getOnlineTestCount() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String id = request.getParameter("id");// 考卷的ID
		logger.info("考卷ID---" + id);
		if (id != null) {
			int s = service.getTestCount(Long.valueOf(id));
			OnlineTests onlineTest = service.getOnlineTest(Long.valueOf(id));
			PrintWriter out = response.getWriter();
			JSONObject jo = new JSONObject();
			jo.put("count", s);
			jo.put("testTitle", onlineTest.getTestTitle());
			jo.put("testDesc", onlineTest.getTestDesc());
			out.write(jo.toString());
		}
		return null;
	}

	// 在线辅导题目总数
	public String getOnlineStudyCount() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		int s = service.getOnlineStudyCount();
		PrintWriter out = response.getWriter();
		out.write(s + "");
		return null;
	}

	// 在线调查总数
	public String getOnlineVoteCount() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		int s = service.getVoteCount();
		Vote vote = service.getActiveVote();
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		jo.put("vtitle", vote.getVtitle());
		jo.put("count", s);
		jo.put("vdesc", vote.getVdesc());
		out.write(jo.toString());
		return null;
	}

	/**
	 * 检测该openId是否已经绑定过
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String checkBind() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String openId = request.getParameter("openId");
		User u = service.getUserByOpenId(openId);
		PrintWriter out = response.getWriter();
		if (u != null) {
			request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, u);
			out.write("ok");
		} else {
			out.write("fail");
		}
		return null;
	}

	/**
	 * 兑换
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String duihuan() throws IOException {
		PrintWriter out = response.getWriter();
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String gifId = request.getParameter("giftId");
		if (gifId == null) {
			out.write("ok");
			return null;
		}
		boolean b = service.duihuan(this.getCurrentUser().getId(), Long.valueOf(gifId));
		if (b) {
			out.write("ok");
		} else {
			out.write("fail");
		}
		return null;
	}

	/**
	 * 兑换 产品，把商品作为奖品，和之前做好的功能不同，之前是独立的兑换奖品
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String duihuanPrd() throws IOException {
		PrintWriter out = response.getWriter();
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String prdId = request.getParameter("prdId");
		if (prdId == null) {
			out.write("fail");
			return null;
		}
		boolean b = service.duihuanPrd(this.getCurrentUser().getId(), Long.valueOf(prdId));
		if (b) {
			out.write("ok");
		} else {
			out.write("fail");
		}
		return null;
	}

	/**
	 * 获得当月话题的问题列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getQuestions() throws IOException {
		String date = request.getParameter("date");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		List<Question> questionsByDate = service.getQuestionsByDate(date);
		request.setAttribute("list", questionsByDate);
		return ("questionList");
	}

	/**
	 * 提交答案
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String submitAnswer() throws IOException {
		try {
			String[] parameterValues = request.getParameterValues("answer");
			// 序列号
			String snum = request.getParameter("snum");
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			int score = service.saveAnswer(this.getCurrentUser(), parameterValues, snum);
			// 分辨PC还是手机
			// String userAgent = request.getHeader("User-Agent");
			// logger.info("客户端类型：" + userAgent);
			// if (!WeiXinUtil.isFromMobile(userAgent)) {//非移动端
			// return new String("/pc/msg.jsp?m=5&msg=1");
			// }else{
			request.setAttribute("score", score);
			// 考卷ID
			request.setAttribute("id", request.getParameter("testId"));
			request.setAttribute("msg", WeiXinUtil.getTestMsg(score));
			// 跳转到考试结果
			return ("testResult");
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return ("error");
		}
	}

	/**
	 * 在线辅导--提交答案
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String submitDemoTest() throws IOException {
		try {
			String[] parameterValues = request.getParameterValues("answer");
			// 序列号
			String snum = request.getParameter("snum");
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			int score = service.saveDemoTestResult(this.getCurrentUser(), parameterValues, snum);
			// 分辨PC还是手机
			// String userAgent = request.getHeader("User-Agent");
			// logger.info("客户端类型：" + userAgent);
			// if (!WeiXinUtil.isFromMobile(userAgent)) {//非移动端
			// return new String("/pc/msg.jsp?m=5&msg=1");
			// }else{
			request.setAttribute("score", score);
			request.setAttribute("msg", WeiXinUtil.getTestMsg(score));
			// 另外，需要把题目所有正确答案输出
			// 查出所有的在线辅导题
			List<PublishedStudy> list = service.getPublishedStudy();
			request.setAttribute("list", list);
			// 跳转到考试结果
			return ("studyResult");
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return ("error");
		}
	}

	/**
	 * 提交调查
	 * 
	 * 提交的关键域 <input type="hidden" value="17|A" id="v_17" name="answer">
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String submitVote() throws IOException {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			// 每个数组表示： VoteItem.id|用户选择的答案
			String[] parameterValues = request.getParameterValues("answer");
			String empType = request.getParameter("empType");
			String jobType = request.getParameter("jobType");
			String voteId = request.getParameter("voteId");
			// 检查是否已经答过了
			if (service.checkVoter(voteId)) {
				request.setAttribute("info", "对不起，该调查您已经参与过了，不能重复提交。");
				return ("voteResult");
			}
			int score = service.saveVote(this.getCurrentUser(), parameterValues, empType, jobType, voteId);
			// 跳转到考试结果
			return ("voteResult");
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return ("error");
		}
	}

	// 投票者session缓存，防止重复投票
	private List<String> tpUser = new ArrayList<String>();

	/**
	 * 提交投票
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String submitTP() throws IOException {
		try {
			if (tpUser.contains(request.getSession().getId())) {
				request.setAttribute("info", "对不起，您已经投过票了！");
				return ("tpresult");
			}
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String slimit = request.getParameter("slimit");
			// 用户选择的选项ID
			String optId = request.getParameter("opts");
			String[] opts = request.getParameterValues("opts");
			if ("1".equals(slimit)) {
				service.updateTPCount(optId);
			} else {
				// 多选的情况
				service.updateTPCount(opts);
			}
			tpUser.add(request.getSession().getId());
			return ("tpresult");
		} catch (Exception e) {
			e.printStackTrace();
			return ("error");
		}
	}

	/**
	 * 在线考试
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getPublishedTesting() throws IOException {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		PrintWriter out = response.getWriter();
		try {
			List<PublishedTesting> list = service.getPublishedTesting();
			out.print(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 获得报告
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getReport() throws Exception {
		logger.info("统计数据查询...");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 话题ID
		String topicId = request.getParameter("topicId");
		List<GameReport> list = service.getReportList(Long.valueOf(topicId));
		JSONArray fromObject = JSONArray.fromObject(list);
		logger.info("fromObject.toString()=" + fromObject.toString());
		response.getWriter().write(fromObject.toString());
		return null;
	}

	/**
	 * 获得第一个案例
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getFirstCase() throws Exception {
		logger.info("统计数据查询...");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		// 0-病例分享 1-获奖病例
		String type = request.getParameter("t");
		ShowCase sc = service.getLastCase(type);
		JSONObject fromObject = JSONObject.fromObject(sc);
		logger.info("fromObject.toString()=" + fromObject.toString());
		response.getWriter().write(fromObject.toString());
		return null;
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
				JsonConfig jc = new JsonConfig();
				jc.setExcludes(new String[]{"pjer"});
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

	/**
	 * 获得第一个专家
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getFirstExpert() throws Exception {
		logger.info("统计数据查询...");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Experts ex = service.getLastExpert();
		JSONObject fromObject = JSONObject.fromObject(ex);
		logger.info("fromObject.toString()=" + fromObject.toString());
		response.getWriter().write(fromObject.toString());
		return null;
	}

	/**
	 * 培训信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getTraining() throws Exception {
		logger.info("统计数据查询...");
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String id = request.getParameter("id");
			Training t = service.getTraining(Long.valueOf(id));
			JSONObject fromObject = JSONObject.fromObject(t);
			logger.info("fromObject.toString()=" + fromObject.toString());
			response.getWriter().write(fromObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}

	public String getAboutUs() throws Exception {
		logger.info("统计数据查询...");
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			AboutUs t = service.getAboutus();
			JSONObject fromObject = JSONObject.fromObject(t);
			logger.info("fromObject.toString()=" + fromObject.toString());
			response.getWriter().write(fromObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 获得当前发布的投票
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getTP() throws Exception {
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			TP t = service.getTP();
			if (t == null) {// 还没有任何投票
				response.getWriter().write("{\"result\":\"nodata\"}");
			} else {
				JsonConfig jc = new JsonConfig();
				jc.setExcludes(new String[] { "items" });
				JSONObject fromObject = JSONObject.fromObject(t, jc);
				response.getWriter().write(fromObject.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 用户提交反馈
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String submitFeedBack() throws Exception {
		logger.info("提交反馈...");
		try {
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			String sesid = request.getSession().getId();
			logger.info("sesid==" + sesid);
			// 反馈
			String name = request.getParameter("name");
			String content = request.getParameter("content");
			service.submitFeedback(name, content);
			request.setAttribute("info", Version.getInstance().getNewProperty("msg1"));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "提交失败，请稍后重试！");
		}
		return ("feedback");
	}

	/**
	 * 查询参数
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> getParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		// **0-邮政资讯 1-业务学习 2-生活常识 */
		String t = request.getParameter("t");
		if (StringUtils.isNotBlank(t) && t != null && !"null".equals(t)) {
			paramMap.put("t", t);
		}
		// 所属子栏目
		String cid = request.getParameter("cid");
		if (StringUtils.isNotBlank(cid) && cid != null && !"null".equals(cid)) {
			paramMap.put("cid", cid);
		}
		// 文章类别： 0-邮政资讯 1-业务学习 2-生活常识
		String docType = request.getParameter("docType");
		if (StringUtils.isNotBlank(docType) && docType != null && !"null".equals(docType)) {
			paramMap.put("docType", docType);
		}
		// 业务学习模块，关键字
		String keyword = request.getParameter("keyword");
		if (StringUtils.isNotBlank(keyword) && keyword != null && !"null".equals(keyword)) {
			paramMap.put("keyword", keyword);
		}
		return paramMap;
	}

	/**
	 * 进入商城首页
	 * 这里需要处理两种情况：
	 *  1）如果是关注用户进入，只有一个参数： c， c的值是经过编码算法的分销商代码
	 *  2）如果是分销客转发的，这个URL除了c之外， 还要带上一个agent参数，这个agent就是转发者（分销客）的openId
	 *    体现在state参数的不同，这种情况，state={分销商id},{分销客id}
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		String referrerUserid="";
		try {
			// 分销商编码, 从参数上传递过来的分销商编码是经过特定算法的： base64编码+随机10位字母; 有=号的，替换成英文逗号(,),然后再倒序
			String reverseCode = request.getParameter("c");
			logger.info("进入分销商城菜单...|" + reverseCode);
			String agentId = request.getParameter("agent");
			if(agentId != null){
				logger.info("通过分销客进入-------" + agentId);
				//把分销客的ID存到session里,后续支付订单时， 需要通过这个计算佣金到分销客
				request.getSession().setAttribute("agentId", agentId);
			}
			
			if(Version.getInstance().getNewProperty("wxtest").equals("y")){
				PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
				User userByOpenId = service.getUserByOpenId("ohXRbxJRAvMg3avhgMgfatRrQRdU");
				request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, userByOpenId);
				request.getSession().setAttribute(CupidStrutsConstants.WXOPENID, "ohXRbxJRAvMg3avhgMgfatRrQRdU");
				
			}
			
			
			String fxCode = super.decodeFxCode(reverseCode);
			//得出分销商名称
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			IAdService adService = (IAdService) ServerBeanFactory.getBean("adService");
			Org orgBycode = service.getOrgBycode(fxCode);
			request.getSession().setAttribute(CupidStrutsConstants.FXCODE, fxCode);
			request.getSession().setAttribute(CupidStrutsConstants.FX_ID, orgBycode.getId().toString());
			//分销商名称
			request.getSession().setAttribute(CupidStrutsConstants.FXNAME, orgBycode.getOrgname());
			request.getSession().setAttribute(CupidStrutsConstants.ORG, orgBycode);
			logger.info("企业组织编号ORGCODE==..." + fxCode);
			
			String userIdStr = request.getParameter("userIdStr");
	
			//推荐人userid(通过扫描二维码进入)
			if(userIdStr!=null&&!userIdStr.equals("")){
				referrerUserid = userIdStr.split("\\|")[0];
				request.getSession().setAttribute(CupidStrutsConstants.REFERRER_USER_ID, referrerUserid);
				request.getSession().setAttribute("diseaseId", userIdStr.split("\\|")[1]);
			}else{
				request.getSession().removeAttribute(CupidStrutsConstants.REFERRER_USER_ID);
			}
			
			User u = this.getCurrentUser();
			if(u == null){
				logger.info("in publicAction.index, User in session is null..");
				Object openId = request.getSession().getAttribute(CupidStrutsConstants.WXOPENID);
				logger.info("openId = " + openId);
				if(openId != null){
					u = service.getUserByOpenId(openId.toString());
					if(u != null){
						logger.info("refresh user in session....");
						this.updateSessionUser(u);
					}
				}
			}
			
			
			//首页幻灯片的信息
			List<AdvConfig> ppts = adService.getPptsByOrg(orgBycode.getId());
			request.getSession().setAttribute("ppts", ppts);
			
			//加载首页logo
			request.getSession().setAttribute("WXMALL_BANNER", orgBycode.getWxMallIndexBanner());
			request.getSession().setAttribute("WXMALL_LOGO", orgBycode.getWxMallLogo());
			
			//根据当前总销商的商城设置，决定首页显示方式： 1） 列表， 2）缩略图方式， 3）单一商品爆款
//			if(orgBycode.getIndexShow() == 0){//list列表
//				return ("index");
//			}else if(orgBycode.getIndexShow() == 1){ //2）缩略图方式
//				return ("index-thumb");
//			}else if(orgBycode.getIndexShow() == 2){ //单一商品爆款
//				return ("index-one");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "进入首页失败，请稍后重试！");
		}
//		return ("cp-index");
		//找药神器的首页
		if(referrerUserid==null||referrerUserid.equals("")){
			return ("drug-index");
		}else{
			//通过推荐扫描进入项目详情页
			return("drug-patientinfo");
		}
		
//		return this.couponList();
	}

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
	 * 预定, 传递参数如下：
	 * 	"count": count, 
		"prdId": prdId, 
		"tel":tel,
		"count":count,
		"bookdate":bookdate,
		"arriveTime":arriveTime
	 * @return
	 * @throws Exception
	 */
	public String bookroom() throws Exception{
		String count = request.getParameter("count");
		String prdId = request.getParameter("prdId");
		String tel = request.getParameter("tel");
		String username = request.getParameter("username");
		//预定日期
		String bookdate = request.getParameter("bookdate");
		String orgId = this.getCurrentFenXiao().getId().toString();
		//最晚到店时间
		String arriveTime = request.getParameter("arriveTime");
		//当前用户的openId
		String openId= "";
		if("y".equals(Version.getInstance().getNewProperty("wxtest"))){
			openId = "testOpenId1000001111111";
		}else{
			openId = request.getSession().getAttribute(CupidStrutsConstants.WXOPENID).toString();
		}
		logger.info("预定房间:" + count + "|" + prdId + "|" + tel + "|" + bookdate  + "|" + arriveTime + "| orgId=" + orgId + "| openId=" + openId);
		Map<String, String> param = new HashMap<String, String>();
		param.put("count", count);
		param.put("prdId", prdId);
		param.put("orgId", orgId);
		param.put("openId", openId);
		param.put("username", username);
		param.put("tel", tel);
		param.put("bookdate", bookdate);//预定日期
		param.put("arriveTime", arriveTime);//最晚到店时间, 24小时制 7表示7点， 11表示11点。
		PrintWriter out = response.getWriter();
		try{
			IHotelService service = (IHotelService) ServerBeanFactory.getBean("hotelService");
			int flag = service.bookRoom(param);
			if(flag == 1){
				out.write("{\"msg\":\"ok\"}");
			}else{
				out.write("{\"msg\":\"fail\"}");
			}
		}catch(Exception e){
			e.printStackTrace();
			out.write("{\"msg\":\"fail\"}");
		}
		return  null;
	}

	/**
	 * 推广链接入口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String spread() throws IOException {
		String vname = request.getParameter("vname");
		String productCat = request.getParameter("productCat");
		logger.info("推广链接入口；vname=" + vname);
		if (vname == null) {
			request.setAttribute("info", "对不起，参数错误！请重新打开推荐链接。");
			return ("info");
		}
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Spreader sp = service.getSpreadVip(vname);
		if (sp == null) {
			request.setAttribute("info", "对不起，推荐链接错误。");
			return ("info");
		}
		// 显示商品信息
		Products p = service.getProductByGuige(productCat);
		request.setAttribute("product", p);
		request.setAttribute("vname", SysUtil.encodeBase64(vname));// 大V微信号
		// 产品详情页
		return new String("/jms/product.jsp");
	}

	/**
	 * 从推广链接直接购买 <br>
	 * 先进入配送地址填写页面，然后 直接生成订单，并进入订单确认页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String directBuyOrder() throws IOException {
		String prdId = request.getParameter("prdId");
		String count = request.getParameter("count");
		try {
			// 转发者
			String vname = request.getParameter("vname");
			// 非关注用户的openId和nickname
			String openId = request.getParameter("openId");
			String nickname = request.getParameter("nickname");
			if (StringUtils.isEmpty(vname) || vname == null) {
				logger.info("非注册用户购买-------" + openId);
			} else {
				logger.info("推广链接,点击购买-------");
			}
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			// "count": count,
			// "prdId": prdId,
			// "userAddress": userAddress,
			// "postcode": postcode,
			// "accepter": accepter,
			// "telephone": telephone
			// 搜集地址数据
			String accepter = request.getParameter("accepter");
			String address = request.getParameter("address");
			String telephone = request.getParameter("telephone");
			String postcode = request.getParameter("postcode");
			String invoiceTitle = request.getParameter("invoiceTitle");
			String buyerMsg = request.getParameter("buyerMsg");
			Map<String, String> param = new HashMap<String, String>();
			// param.put("accepter", accepter);
			// param.put("address", address);
			// param.put("telephone", telephone);
			// param.put("postcode", postcode);
			param.put("invoiceTitle", invoiceTitle);
			param.put("buyerMsg", buyerMsg);
			param.put("destlocation", accepter + "|" + telephone + "|" + address + "|" + postcode);
			param.put("userId", null);
			param.put("prdId", prdId);// 产品ID
			param.put("count", count);// 数量
			param.put("vname", SysUtil.decodeBase64(vname));// 关键参数。。。
			param.put("openId", openId);
			param.put("nickname", nickname);
			// 直接生成免注册订单
			Orders saveOrder = service.saveOrder(param);
			// 跳转到订单确认页
			request.setAttribute("order", saveOrder);
			// 非关注用户的openId
			request.setAttribute("openId", openId);
			JSONObject jo = new JSONObject();
			jo.put("result", "ok");
			jo.put("orderId", saveOrder.getId().toString());
			response.getWriter().write(JSONObject.fromObject(jo).toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
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
			String openId = request.getParameter("openId");
			if (orderId == null) {
				request.setAttribute("info", "对不起，参数错误！");
				return ("info");
			}
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			Orders order = service.getOrder(Long.valueOf(orderId));
			request.setAttribute("order", order);
			request.setAttribute("openId", openId);
			logger.info("开放订单---" + openId);
			// 到订单确认页面
			return ("confirmOrder");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 首页获取所有产品--佳美氏
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getAllProductList() throws IOException {
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
		// Map<String, String> paramMap = getParamMap(request);
		Map<String, String> paramMap = new HashMap<String, String>();
		if (request.getParameter("keyword") != null) {
			paramMap.put("keyword", request.getParameter("keyword"));
		}
		// ----积分兑换页面传递的参数，目的有2个，为了在列表上显示兑换按钮和兑换积分。---
		if (request.getParameter("charge") != null) {
			paramMap.put("charge", request.getParameter("charge"));
		}
		// 当前的分类ID
		if (request.getParameter("pid") != null) {
			paramMap.put("pid", request.getParameter("pid"));
		}
		//当前分销商代码
		paramMap.put("orgCode", this.getCurrentFxCode());
		
		try {
			Map<String, Object> map = service.getAllProductList(start, Integer.valueOf(limit).intValue(), paramMap);
			List<Products> dataList = (List<Products>) map.get("list");
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
			out.print(JSONObject.fromObject(pp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	

	/**
	 * 加载分类
	 * 
	 * @return
	 * @throws IOException
	 */
	public String loadCat() throws IOException {
		response.setCharacterEncoding("UTF-8");
		Object fxCode = request.getSession().getAttribute(CupidStrutsConstants.FXCODE);
		String pid = request.getParameter("pid");
		if (fxCode == null || pid == null) {
			// 参数不正确
			response.getWriter().write("{\"result\":\"param\"}");
			return null;
		}
		PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
		// 查找子分类
		List<ProductCat> subList = service.loadSubCategory(fxCode.toString(), pid);
		if (subList.isEmpty()) {// 没子分类，加载产品列表
			// 加载产品
			this.loadOrgProducts(pid);
		} else {
			JSONObject jo = new JSONObject();
			jo.put("result", "1");
			jo.put("list", subList);
			response.getWriter().write(JSONObject.fromObject(jo).toString());
		}
		return null;
	}
	/**
	 * 加载爆款商品图片列表，图片上附加URL
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getOnePrdList() throws IOException {
		response.setCharacterEncoding("UTF-8");
		Object obj = request.getSession().getAttribute(CupidStrutsConstants.ORG);
		if (obj == null) {
			// 参数不正确
			response.getWriter().write("{\"result\":\"param\"}");
			return null;
		}
		try{
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			OneProduct one = service.getOneProductInfo(((Org)obj).getId());
			IProductsService prdService = (IProductsService) ServerBeanFactory.getBean("productsService");
			JSONObject jo = new JSONObject();
			jo.put("result", "ok");
//			Products prd = prdService.getPrductByCode(one.getPrdCode());
//			if(prd != null){
//				jo.put("prdId", prd.getId().toString());
//			}
			jo.put("data", one);
			response.getWriter().write(JSONObject.fromObject(jo).toString());
		} catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	/**
	 * 加载第一级产品分类
	 * @return
	 * @throws IOException
	 */
	public String loadTopPrdCat() throws IOException {
		response.setCharacterEncoding("utf-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		
		String orgCode = this.getCurrentFenXiao().getCode();
		List<ProductCat> list = service.getTopCat(orgCode);
		JSONObject jo = new JSONObject();
		jo.put("result", "ok");
		jo.put("datas", list);
		logger.info(jo.toString());
		response.getWriter().write(jo.toString());
		return null;
	}
	/**
	 * 加载二级分类 
	 * @return
	 * @throws IOException
	 */
	public String loadSubPrdCat() throws IOException {
		response.setCharacterEncoding("utf-8");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		String pid = request.getParameter("pid");
		String orgCode = this.getCurrentFenXiao().getCode();
		List<ProductCat> list = service.getSubCat(orgCode, pid);
		JSONObject jo = new JSONObject();
		jo.put("result", "ok");
		jo.put("datas", list);
		logger.info(jo.toString());
		response.getWriter().write(jo.toString());
		return null;
	}

	/**
	 * 加载当前分销商信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String loadParentOrgInfo() throws IOException {
		response.setCharacterEncoding("UTF-8");
		// 把分销商code传递到申请页面
		String orgCode = this.getCurrentFxCode();
		if(orgCode == null){
			JSONObject jo = new JSONObject();
			jo.put("result", "fail");
			response.getWriter().write(JSONObject.fromObject(jo).toString());
			return null;
		}
		AdminService adminService = (AdminService) ServerBeanFactory.getBean("adminService");
		Org org = adminService.getOrgBycode(orgCode);
		String levelName = "";
		if(org.getCode().length() == CupidStrutsConstants.LV_CODE_LENGTH){
			levelName = "一级分销商";
		}else if(org.getCode().length() == CupidStrutsConstants.LV_CODE_LENGTH * 2){
			levelName = "二级分销商";
		}else if(org.getCode().length() == CupidStrutsConstants.LV_CODE_LENGTH * 3){
			levelName = "三级分销商";
		}
		
		JSONObject jo = new JSONObject();
		jo.put("result", "ok");
		jo.put("orgName", org.getOrgname());
		jo.put("orgCode", org.getCode());
		jo.put("levelName", levelName);
		
		response.getWriter().write(JSONObject.fromObject(jo).toString());
		return null;
	}
	
	
	/**
	 * 检查验证码是否正确 
	 * @return
	 */
	public String checkVerifyCode(){
		String code = request.getParameter("code");
		JSONObject jo = new JSONObject();
		try{
			Object codeInsession = request.getSession().getAttribute(CupidStrutsConstants.CODE_IMAGE_REGIST);
			if(codeInsession != null){
				if(!codeInsession.equals(code)){//验证码不正确
					jo.put("result", "fail");
				}else{
					jo.put("result", "ok");
				}
			}
			response.getWriter().write(JSONObject.fromObject(jo).toString());
		}catch(Exception e){
			jo.put("result", "fail");
		}
		return null;
	}
	
	
	/**
	 * 分销商申请
	 * @return
	 * @throws IOException
	 */
	public String fxApply() throws IOException {
		try {
			//验证码判断
			String codeImage = request.getParameter("codeImage");
			if(codeImage == null || !codeImage.equals(request.getSession().getAttribute(CupidStrutsConstants.CODE_IMAGE_REGIST).toString())){
				request.setAttribute("info", "验证码不正确，请重新输入！");
				return "fxapply-biz";
			}
			
			// 搜集用户个人信息
			String username = request.getParameter("username");
			String telephone = request.getParameter("telephone");
			String email = request.getParameter("email");
			//经销商编码, 这个编码应该从session里获取，因为入口链接访问过以后， 分销商编码就已经存在session里了
			String orgCode = request.getSession().getAttribute(CupidStrutsConstants.FXCODE).toString();
			String openId = request.getParameter("openId");
			
			//---------test--------------
//			orgCode = "000002";
			openId="asdfadsfFDfgsadf";
			
			Map<String,String>  param = new HashMap<String, String>();
			param.put("username", username);
			param.put("telephone", telephone);
			param.put("email", email);
			param.put("orgCode", orgCode);
			param.put("openId", openId);
			logger.info("分销商申请-----"+param);
 			
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			service.addSubOrg(orgCode, param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("info", "申请成功！");
		return "info";
	}
	
	/**
	 * 个人分销客申请 
	 * Ajax方法
	 * @return
	 * @throws IOException 
	 */
	public String fxPersonApply() throws IOException{
		try{
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String openId = this.getCurrentUser().getOpenId();
			//申请成为分销客
			User user = service.applyFxPerson(openId, this.getCurrentFxCode());
			if(user == null){
				response.getWriter().write("{\"result\":\"fail\"}");
			}else{
				response.getWriter().write("{\"result\":\"ok\"}");
			}
			
			//更新session
			request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, user);
			
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}
	/**
	 * 个人分销客状态查询，是否已经申请过了 
	 * Ajax方法
	 * @return
	 * @throws IOException 
	 */
	public String checkFxPersonStatus() throws IOException{
		try{
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String openId = this.getCurrentUser().getOpenId();
			//申请成为分销客
			User user = service.getUserByOpenId(openId);
			if(user.getFlag() == 1){
				response.getWriter().write("{\"result\":\"ok\",\"regDate\":\""+ user.getRegDate().toString().substring(0, 10) +"\"}");
			}else{
				response.getWriter().write("{\"result\":\"fail\"}");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	
	
	
	

	private String loadOrgProducts(String pid) throws IOException {
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
		// Map<String, String> paramMap = getParamMap(request);
		Map<String, String> paramMap = new HashMap<String, String>();
		if (request.getParameter("keyword") != null) {
			paramMap.put("keyword", request.getParameter("keyword"));
		}
		// ----积分兑换页面传递的参数，目的有2个，为了在列表上显示兑换按钮和兑换积分。---
		if (request.getParameter("charge") != null) {
			paramMap.put("charge", request.getParameter("charge"));
		}
		try {
			Map<String, Object> map = service.getAllProductList(start, Integer.valueOf(limit).intValue(), paramMap);
			List<Products> dataList = (List<Products>) map.get("list");
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
			out.print(JSONObject.fromObject(pp).toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}

	/**
	 * 用户点击菜单，进入积分兑换页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String scoreCharge() throws IOException {
		try {
			String openId = request.getParameter("openId");
			if (openId == null) {
				request.setAttribute("info", "对不起，参数错误！");
				return ("info");
			}
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			User userByOpenId = service.getUserByOpenId(openId);
			request.setAttribute("username", userByOpenId.getUsername());
			request.setAttribute("openId", openId);
			// 加载积分不为0的商品
			// 到积分兑换页面
			return new String("/jms/scoreCharge.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 进入提示用户关注的url 
	 * @return
	 * @throws IOException
	 */
	public String attHintUrl() throws IOException {
		try {
			String url = this.getCurrentFenXiao().getAttHintUrl();
			logger.info("提示关注的url..."+ url);
			if(url == null){
				response.sendRedirect(request.getContextPath() + "/weixin/noAtthintUrl.jsp");
			}else{
				response.sendRedirect(url);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/weixin/noAtthintUrl.jsp");
		}
		return null;
	}
	
	
	/**
	 * 会员注册
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String userReg() throws IOException {
		try {
			String codeImage = request.getParameter("codeImage");
			if(!codeImage.equalsIgnoreCase(request.getSession().getAttribute(CupidStrutsConstants.CODE_IMAGE_REGIST).toString())){
				request.setAttribute("info", "对不起，验证码不正确！");
				return "wxreg";
			}
			
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			// 搜集用户个人信息
			String username = request.getParameter("username");
			String newpwd = request.getParameter("newpwd");
			String confirmpwd = request.getParameter("confirmpwd");
			String telephone = request.getParameter("telephone");
			// String birthday = request.getParameter("birthday");
			String email = request.getParameter("email");
			String openId = request.getSession().getAttribute(CupidStrutsConstants.WXOPENID).toString();
			logger.info("分销商注册------openId---" + openId);
			
			//根据该分销商的公众号，获取粉丝的信息
			Org org = this.getCurrentFenXiao();
			WxUser wxUserByOrg = WeiXinUtil.getWxUserByOrg(openId, org.getAppid(), org.getAppsecret());
			logger.info("注册时：：：微信用户信息：" + wxUserByOrg.toString());
			
			String fxCode = request.getSession().getAttribute(CupidStrutsConstants.FXCODE).toString();
			if (!newpwd.equals(confirmpwd)) {
				request.setAttribute("info", "对不起，两次密码输入不一致。");
				return "wxlogin";
			}
			User u = new User();
			u.setUsername(username);
			u.setTelephone(telephone);
			u.setOpenId(openId);
			u.setPassword(newpwd);
			
			//------微信用户属性--------------------------
			u.setNickName(wxUserByOrg.getNickName());
			u.setHeadimgurl(wxUserByOrg.getHeadimgurl());
			u.setCity(wxUserByOrg.getCity());
			u.setProvince(wxUserByOrg.getProvince());
			u.setCountry(wxUserByOrg.getCountry());
			u.setSex(wxUserByOrg.getSex());
			u.setSubscribTime(wxUserByOrg.getSubscribeTime());
			
			
			u.setEmail(email);
			u.setRegDate(new Date());
			// 根据fenxiaocode查询分销商
			u.setOrg(service.getOrgBycode(fxCode));
			u.setStatus(1);//默认可用？？？？？？？？？？？？？？？？？？？？？
			// u.setBirthday(birthday);
			// 注册送积分
			String regScore = Version.getInstance().getNewProperty("regScore");
			if (regScore == null) {
				u.setScore(0);
			} else {
				u.setScore(Integer.valueOf(regScore));
			}
			service.saveUser(u);
			request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, u);
			// session.put(CupidStrutsConstants.SESSION_USER, u);
		} catch (Exception e) {
			e.printStackTrace();
			// 到注册成功页面
			request.setAttribute("info", "注册失败！");
			return "info";
		}
		// 到注册成功页面
		request.setAttribute("info", "注册成功！");
		return "info";
	}
	
	
	/**
	 * 拿红包 
	 * @return
	 * @throws IOException
	 */
	public String takeHongbao() throws IOException {
		try{
			response.setCharacterEncoding("UTF-8");
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String openId = request.getParameter("openId");
			String uuid = request.getParameter("uuid");
			if (openId == null) {
				response.getWriter().write("{\"result\":\"fail\"}");
				return null;
			}
			//根据uuid和openId， 将红包数据和用户绑定， 并更新红包库存
			int b = service.takeHongbao(this.getCurrentFenXiao().getId(), openId, uuid);
			JSONObject jo = new JSONObject();
			
			if(b == 1){
				jo.put("msg", "ok");
			}else if(b == 9){//红包没有了
				jo.put("msg", "null");
			}else if(b == 2){
				jo.put("msg", "multi");
			}else{
				jo.put("msg", "fail");
			}
			
			response.getWriter().write(jo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 前台：生成红包的推广链接， 主要用来转发 
	 * "uuid": uuid,
			"openId": openId,
			"orgId": orgId
	 * @return
	 * @throws IOException 
	 */
	public String createHongBaoLink() throws IOException{
		response.setCharacterEncoding("UTF-8");
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfa261c6dad08e42c&redirect_uri=http%3A%2F%2Fwww.91lot.com%2Ffenxiao%2Fwx%2Fwx_wxMall.Q&response_type=code&scope=snsapi_userinfo&state=84#wechat_redirect
		String orgId = request.getParameter("orgId");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Org orgById = service.getOrgById(Long.valueOf(orgId));
		String appId = orgById.getAppid();
		String serverUrl = Version.getInstance().getNewProperty("wxServer");
		logger.info("前台---createhongbaoLink---serverUrl==" + serverUrl);
		if(appId == null || StringUtils.isEmpty(appId)){
			response.getWriter().write("{\"msg\":\"appId\"}");
			return null;
		}
//		"uuid": uuid, //红包的批次代码
//		"hbid": hbid	红包的数据库ID
		String param = request.getParameter("uuid")+","+orgId;//通过state传递的参数
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId +"&redirect_uri=http%3A%2F%2F"+ serverUrl +"%2Fhotel%2Fwx%2Fwx_wxHongbao.Q&response_type=code&scope=snsapi_base&state="+ param +"#wechat_redirect";
		response.getWriter().write("{\"msg\":\"ok\",\"result\":\""+ url +"\"}");
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
		try{
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
			
			jo.put("shareLogo", org.getShareLogo());
			jo.put("shareTitle", org.getShareTitle());
			jo.put("shareDesc", org.getShareDesc());
			
			response.getWriter().write(jo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载红包首页的个性化图片 
	 * 路径： upload\org-78\hongbao\hongbao.xxx
	 * @return
	 */
	public String loadHongBaoPic(){
		String orgId = request.getParameter("orgId");
//		orgId = "78";
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Org org = service.getOrgById(Long.valueOf(orgId));
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write("{\"msg\":\"ok\",\"result\":\""+ org.getHongbaoPic() +"\"}");
		} catch (IOException e) {
			out.write("{\"msg\":\"fail\"}");
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static void main(String[] args) {
		// DecimalFormat df = new DecimalFormat("00000");
		// Number parse = df.parse("03010");
		// System.out.println("parse=" + parse.intValue());
		// UUID uuid = UUID.randomUUID();
		// String snum = uuid.toString();
		// System.out.println(snum);
		String text = "003";
		String encode = Base64Utility.encode(text.getBytes());
		String string = encode.replaceAll("=", ",") + SysUtil.getRandomCode(10);
		System.out.println("ENCODE=="+encode);
		String reverse = StringUtils.reverse(string);
		System.out.println("reverse str=="+reverse);
		byte[] decode;
		try {
			decode = Base64Utility.decode(StringUtils.reverse(reverse).substring(0, string.length() - 10).replaceAll(",", "="));
			// byte[] decode = Base64Utility.decode(StringUtils.reverse(reverse));
			System.out.println(new String(decode));
		} catch (Base64Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String loadContact() throws IOException{
		response.setCharacterEncoding("UTF-8");
		String locId = request.getParameter("locId");
		Long orgId = Long.valueOf(request.getParameter("orgId"));
		PrintWriter out = null;
		try{
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			Contacts contacts = service.getContacts(orgId, Integer.valueOf(locId));
			out = response.getWriter();
			if(contacts != null){
				JSONObject jo = new JSONObject();
				jo.put("msg", "ok");
				jo.put("result", contacts);
				out.print(jo.toString());
			}
		}catch(Exception e){
			out.write("{\"msg\":\"fail\",\"result\":\"\"}");
		}
		return null;
	}
	
	

	/**
	 * 车险平台：：：设置用户所在城市
	 * @return
	 */
	public String setCity(){
	//	CupidStrutsConstants.SES_CITY
		PrintWriter out = null;
		try{
			out = response.getWriter();
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			User currentUser = this.getCurrentUser();
			request.getSession().setAttribute(CupidStrutsConstants.SES_CITY, city);
			//更新数据库
			AdminService ss = (AdminService) ServerBeanFactory.getBean("adminService");
			currentUser.setCity(city);
			currentUser.setCountry("中国");
			currentUser.setProvince(province);
			ss.updateUser(currentUser);
			JSONObject jo = new JSONObject();
			jo.put("msg", "ok");
			String encodeParam = super.encodeFxCode(this.getCurrentFenXiao().getCode());
			String dest = request.getContextPath() + "/public/pub_index.Q?c=" + encodeParam;
			logger.info("城市设置成功（"+ province + "|" + city +"）， 接下去url-" + dest);
			
			//改变当前session中的FXcode,因为后面的优惠券列表是根据fenxiaoCode去查找各自代理商发布的
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			Org org = service.getOrgByCity(city);
			if(org != null){
				request.getSession().setAttribute(CupidStrutsConstants.FXCODE, org.getCode());
				request.getSession().setAttribute(CupidStrutsConstants.WXFENXIAO, org);
			}
			
			jo.put("result", dest);//目标页面
			out.print(jo.toString());
		}catch(Exception e){
			e.printStackTrace();
			JSONObject jo = new JSONObject();
			jo.put("msg", "fail");
			out.print(jo.toString());
		}
		return null;
	}
	
	/**
	 * 车险平台：：：获取发现列表 
	 * @return
	 * @throws IOException
	 */
	public String getFaxianLists() throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
			// 查询参数
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("openId", this.getCurrentUser().getOpenId());
			Paging pp = service.getFaxianList(Integer.valueOf(page), Integer.valueOf(limit), paramMap);
			if(!pp.getDatas().isEmpty()){
				JSONObject jo = new JSONObject();
				jo.put("msg", "ok");
				jo.put("result", pp);
				out.print(jo.toString());
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
	 * 车险服务入口 
	 * @return
	 */
	public String carins() throws Exception{
		try{
			String openId = this.getCurrentUser().getOpenId();
			request.setAttribute("openId", openId);
			return "car-ins";
//			return "car-index";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 优惠券购买列表 
	 * @return
	 */
	public String couponList(){
		return "car-couponList";
	}
	
	
	public String getRescueList() throws IOException{
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
			paramMap.put("area", this.getCurrentUser().getCity());
			paramMap.put("province", this.getCurrentUser().getProvince());
			Paging pp = service.getRescueList(Integer.valueOf(page), Integer.valueOf(limit), paramMap);
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
	 * 初始化优惠券分类
	 * @return
	 * @throws Exception
	 */
	public String loadCouponCats() throws Exception{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			IProductCatService service = (IProductCatService) ServerBeanFactory.getBean("productCatService");
			List<ProductCat> pcats = service.getPrdCatsByParentId(0L);
			List<ProductCat> cats = new ArrayList<ProductCat>();
			ProductCat p = null;
			if(pcats.size() > 1){//没有父级的情况
				cats = pcats;
			}else{//
				p = pcats.get(0);//根节点，===优惠券
				cats = service.getPrdCatsByParentId(p.getId());
			}
			if(!cats.isEmpty()){
				JSONObject jo = new JSONObject();
				jo.put("msg", "ok");
				jo.put("result", cats);
				out.print(jo.toString());
			} else {
				out.print("{\"msg\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"msg\":\"fail\"}");
		}
		return null;
	}
	
	
	public String loadFaxianDetail() throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try{
			String dbId = request.getParameter("dbId");
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			ShowCase sc = service.getShowCase(dbId);
			if(sc != null){
				JSONObject jo = new JSONObject();
				jo.put("msg", "ok");
				jo.put("result", sc);
				out.print(jo.toString());
			} else {
				out.print("{\"msg\":\"null\"}");
			}
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("info", "获取信息失败");
			return "info";
		}
		return null;
	}
	
	/**
	 * 加载推荐优惠券
	 * @return
	 * @throws IOException
	 */
	public String loadRecommended() throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try{
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			List<AdvConfig> list = service.getRecommendedList(this.getCurrentFenXiao().getId());
			if(list != null && !list.isEmpty()){
				JSONObject jo = new JSONObject();
				jo.put("msg", "ok");
				jo.put("result", list);
				out.print(jo.toString());
			} else {
				out.print("{\"msg\":\"null\"}");
			}
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("info", "获取信息失败");
			return "info";
		}
		return null;
	}
	
	
}


