<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="util.WxPayUtil"%>
<%@page import="service.intf.AdminService"%>
<%@page import="com.base.ServerBeanFactory"%>
<%@page import="com.base.log.LogUtil"%>
<%@page import="com.constants.CupidStrutsConstants"%>
<%@page import="com.wfsc.common.bo.user.User"%>
<%
Logger logger = LogUtil.getLogger(LogUtil.PAY);

logger.info("支付成功通知：：：：");
BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
String line = null;
StringBuilder sb = new StringBuilder();
while ((line = br.readLine()) != null) {
	sb.append(line);
}
/*
返回消息格式样例-2015。5
<xml>
	<appid><![CDATA[wx788f0bb83a4a427e]]></appid>
	<bank_type><![CDATA[CMB_CREDIT]]></bank_type>
	<cash_fee><![CDATA[1]]></cash_fee>
	<fee_type><![CDATA[CNY]]></fee_type>
	<is_subscribe><![CDATA[Y]]></is_subscribe>
	<mch_id><![CDATA[1239466102]]></mch_id>
	<nonce_str><![CDATA[yixiaoke2015]]></nonce_str>
	<openid><![CDATA[o7zxXuDRLzb4KwMQa_6hSz4077Ag]]></openid>
	<out_trade_no><![CDATA[20150506-D0000-20150506-000014]]></out_trade_no>
	<result_code><![CDATA[SUCCESS]]></result_code>
	<return_code><![CDATA[SUCCESS]]></return_code>
	<sign><![CDATA[6883A38721EDBB01F22DA549293FA18F]]></sign>
	<time_end><![CDATA[20150516103235]]></time_end>
	<total_fee>1</total_fee>
	<trade_type><![CDATA[JSAPI]]></trade_type>
	<transaction_id><![CDATA[1007600075201505160133706431]]></transaction_id>
</xml>
*/
logger.info("payNotify:" + sb.toString());
String rt = WxPayUtil.getValueFromXml(sb.toString(), "return_code");
String rc = WxPayUtil.getValueFromXml(sb.toString(), "result_code");
//商家系统订单号
String orderNum = WxPayUtil.getValueFromXml(sb.toString(), "out_trade_no");
//交易ID
String transId = WxPayUtil.getValueFromXml(sb.toString(), "transaction_id");
//银行
String bank = WxPayUtil.getValueFromXml(sb.toString(), "bank_type");
//订单金额
String totalFee = WxPayUtil.getValueFromXml(sb.toString(), "total_fee");

AdminService service = (AdminService)ServerBeanFactory.getBean("adminService");
if("SUCCESS".equals(rt) && "SUCCESS".equals(rc)){
	//组装参数
	Map<String, String> params = new HashMap<String, String>();
	params.put("orderNum", orderNum);
	params.put("bank", bank);
	params.put("transId", transId);
	params.put("status", "1");
	params.put("totalFee", totalFee);
	
	//确定支付已经成功，修改订单状态 (订单状态 0 – 未支付， 1-已支付，2-已发货，3-已完成 9-废弃)
	logger.info("订单状态修改开始...." + params.toString());
	service.updateOrderPayStatus(params);
	logger.info("订单状态修改完毕....");
	//返回正确通知给微信支付服务器
	WxPayUtil.wxpayFeedback(response, 1, null);
}else{
	response.sendRedirect(request.getContextPath() + "/weixin/error.html");
}

%>

