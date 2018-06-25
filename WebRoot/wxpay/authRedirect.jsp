<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="util.WxPayUtil"%>
<%@page import="util.HttpUtils"%>
<%@page import="util.SysUtil"%>
<%@page import="service.intf.AdminService"%>
<%@page import="com.base.log.LogUtil"%>
<%@page import="com.wfsc.common.bo.product.Products"%>
<%@page import="com.base.ServerBeanFactory"%>
<%@page import="com.base.tools.Version"%>
<%@page import="model.bo.auth.Org"%>
<%
Logger logger = LogUtil.getLogger(LogUtil.PAY);
Object obj = request.getSession().getAttribute("WXFENXIAO");
Org org = null;
if(obj != null){
	org = (Org)obj;
	
	String appId = org.getAppid();
	String secret = org.getAppsecret();
	//用户点击允许授权后,跳转到此页面: 并带上code参数,(不允许的话,也到此页面,但是不带code参数)redirect_uri/?code=CODE&state=STATE
	String code = request.getParameter("code");
	//vname， 推广人的微信号或openId以及产品信息
	String state = request.getParameter("state");
	logger.info("code=" + code);
	logger.info("state=" + state);//用来传递大V微信号和商品规格的参数，很重要！
	if(code != null){
		// 第二步：通过code换取网页授权access_token
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret="+ secret +"&code="+ code +"&grant_type=authorization_code";
		logger.info("tokenUrl=="+tokenUrl);
		//拿到openId
		String doPost = HttpUtils.doPost(tokenUrl, null, null);
		logger.info("doPost=" + doPost);
		String openId = WxPayUtil.getValueFromJson(doPost, "openid");
		String token = WxPayUtil.getValueFromJson(doPost, "access_token");
		logger.info("openId=" + openId);
		logger.info("token=" + token);
		
		String vname = state.split(";")[0];
		String prdCat = state.split(";")[1];
		//抓取用户信息
		//https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+ openId +"&lang=zh_CN";
		String infoXml = HttpUtils.doPost(infoUrl, null, null);
		logger.info("infoXml=" + infoXml);
		//用户昵称
		String nickname = WxPayUtil.getValueFromJson(infoXml, "nickname");
		String province = WxPayUtil.getValueFromJson(infoXml, "province");
		//然后转到商品购买页面
		//显示商品信息
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Products p = service.getProductByGuige(prdCat);
		request.setAttribute("product", p);
		request.setAttribute("nickname", nickname);
		request.setAttribute("openId", openId);
		request.setAttribute("vname", SysUtil.encodeBase64(vname));
		request.setAttribute("prdCat", prdCat);
		logger.info("p=" + p + " | prdCat=" + prdCat);
		logger.info("prdCat=" +prdCat);
		logger.info("vname=" +vname);
		logger.info("nickname=" +nickname);
		request.getRequestDispatcher("/weixin/product.jsp").forward(request, response);
	}else{
		//不允许授权
		response.sendRedirect("/authCancel.jsp");
	}
	
	
}else{
	//超时了
	out.println("为了您的个人信息安全， 会话已经超时，请重新从菜单或转发链接进入。");
	return;
}


%>

