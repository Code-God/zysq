package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import model.bo.auth.Org;
import model.vo.WxPayParamn;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import service.intf.PublicService;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.wfsc.util.DateUtil;

/**
 * 微信支付UTIL
 *
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class WxPayUtil {
	//日志
	private static Logger logger = Logger.getLogger(LogUtil.PAY);

	
	public static void main(String[] args) throws Exception{
//		String s="{'a':'123','b':'222'}";
//		try {
//			JSONObject fromObject = JSONObject.fromObject(s);
////			Object bean = JSONObject.toBean(fromObject, AA.class);
////			System.out.println(bean);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String[] keys = {"appId", "timeStamp", "nonceStr", "package", "signType"};
//		Arrays.sort(keys); // 排
//		for (String string : keys) {
//			System.out.println(string);
//		}
		
//		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" +
//					 "<return_msg><![CDATA[OK]]></return_msg>" +
//					"<appid><![CDATA[wx8275aa5fa86fb7c6]]></appid>" +
//					"<mch_id><![CDATA[10017362]]></mch_id>" +
//					"<nonce_str><![CDATA[Gi4H3YmFZHcneXmo]]></nonce_str>" +
//					"<sign><![CDATA[392FA2972F691B870926300311E9933F]]></sign>" +
//					"<result_code><![CDATA[SUCCESS]]></result_code>" +
//					"<prepay_id><![CDATA[wx20141230100858d1db2f491e0268749203]]></prepay_id>" +
//					"<trade_type><![CDATA[JSAPI]]></trade_type>" +
//					"</xml>";
//		
//		System.out.println(getValueFromXml(xml, "prepay_id"));
//		String s = "{\"access_token\":\"OezXcEiiBSKSxW0eoylIeNJILJCJZjCtB9XIgZyyrUipOhnwpp5nEuOXQKyZ91ZsMHJ09DRX9o_MY3e0YDXmS8F2XP2be9RBhf5wjww8tkj08laBt4OBx72MIUn_POS3eYd3vldfko0Ozh22uAIVVA\"}";
//		JSONObject fromObject = JSONObject.fromObject(s);
//		System.out.println(fromObject.get("access_token"));
		String s = "vv|2L";
		System.out.println(s.split("\\|")[0]);
		System.out.println(s.split("\\|")[1]);
	}

	public final static String nonceStr = "yixiaoke2015";

	/**
	 * 获取预支付订单号
	 * @param appId - APPID
	 * @param mchId - 商户号
	 * @param openId - 用户的OPENID
	 * @param orderNum - 业务订单号
	 * @param payKey - 支付密钥
	 * @return
	 * @throws DocumentException 
	 */
	public static String getPrePayId(Org org,  String openId, String orderNum) throws DocumentException {
		String appId = org.getAppid();
		String appSecret = org.getAppsecret();
		String mchId = org.getMchId();//商户号
		String payKey = org.getPayKey();
		logger.info("in getPrePayId----支付参数----payKey==" + payKey);
		long t = System.currentTimeMillis();
		String url = WxPayParamn.UNI_PAY_URL;
		PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
		int totalFee = 0;
		if("y".equals(Version.getInstance().getNewProperty("wxtest"))){
			totalFee = 1;//测试， 支付1分钱
//			openId = "o2lYEj0-U5A2QKQGvCi_eEHtwK1Y";
		}else{
			//根据订单号获得总金额
			totalFee = service.getOrderFeeByOrderNum(orderNum);
		}
		//商品名称
		String encode = org.getOrgname();
		logger.info("商品："+ encode +", 实际需支付：" + totalFee + "分");
//		try {
//			encode = URLEncoder.encode(org.getOrgname(), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		
		//--------- 组装签名参数 -----
		String[] keys = {"appid", "mch_id", "nonce_str","body","out_trade_no","total_fee","spbill_create_ip","notify_url","time_start", "time_expire", "trade_type","openid"};
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", appId);//用户的APPID
		map.put("mch_id", mchId);//用户微信支付的商户号
		map.put("nonce_str", nonceStr);
		
		map.put("body", encode);
		map.put("out_trade_no", orderNum);
		map.put("total_fee", totalFee+"");
		map.put("spbill_create_ip", Version.getInstance().getNewProperty("spIP"));
		map.put("notify_url", Version.getInstance().getNewProperty("notify_url"));
		map.put("trade_type", Version.getInstance().getNewProperty("trade_type"));
		map.put("openid", openId);
		String timeStart = DateUtil.getLongDateMark();
		//订单失效时间：10分钟
		String timeEnd = DateUtil.getLongDateMark(new Date().getTime() + 10 * 60 * 1000);
		map.put("time_start", timeStart);
		map.put("time_expire", timeEnd);
		//签名
		String sign = wxpaySign(keys, map, payKey);
		
//		<xml>
//		<appid>wx2421b1c4370ec43b</appid>
//		<attach><![CDATA[att1]]></attach>
//		<body><![CDATA[JSAPI 支付测试]]></body>
//		<device_info>1000</device_info>
//		<mch_id>10000100</mch_id>
//		<nonce_str>b927722419c52622651a871d1d9ed8b2</nonce_str>
//		<notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.php</notify_url>
//			<out_trade_no>1405713376</out_trade_no>
//			<spbill_create_ip>127.0.0.1</spbill_create_ip>
//			<total_fee>1</total_fee>
//			<trade_type>JSAPI</trade_type>
//			<sign><![CDATA[3CA89B5870F944736C657979192E1CF4]]></sign>
//			</xml>
		StringBuffer xml = new StringBuffer("<xml>");
		xml.append("<appid><![CDATA["+ appId +"]]></appid>");
		xml.append("<mch_id>"+ mchId +"</mch_id>");
		xml.append("<nonce_str><![CDATA["+ nonceStr +"]]></nonce_str>");
		xml.append("<body><![CDATA["+ encode +"]]></body>");
		xml.append("<out_trade_no><![CDATA["+ orderNum +"]]></out_trade_no>");
		xml.append("<total_fee>"+ totalFee +"</total_fee>");
		xml.append("<spbill_create_ip><![CDATA["+ Version.getInstance().getNewProperty("spIP") +"]]></spbill_create_ip>");
		xml.append("<notify_url><![CDATA["+ Version.getInstance().getNewProperty("notify_url") +"]]></notify_url>");
		xml.append("<trade_type>"+ Version.getInstance().getNewProperty("trade_type") +"</trade_type>");
		xml.append("<openid><![CDATA["+ openId +"]]></openid>");
		xml.append("<time_start><![CDATA["+ timeStart +"]]></time_start>");
		xml.append("<time_expire><![CDATA["+ timeEnd +"]]></time_expire>");
		xml.append("<sign><![CDATA["+ sign +"]]></sign>");
		xml.append("</xml>");
		logger.info("xml=" + xml.toString());
		String doPost = HttpUtils.doPostXml(url, xml.toString());
		//解析xml，拿到prePayId
		
		logger.info("getPrePayId=== "+doPost + "("+ (System.currentTimeMillis() - t) +"ms)");
		try{
			if("fail".equalsIgnoreCase(getValueFromXml(doPost, "result_code"))){
				//获取预支付ID失败
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return getValueFromXml(doPost, "prepay_id");
	}

	/**
	 * 解析xml,返回该属性的值
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static String getValueFromXml(String xml, String prop) throws DocumentException{
		Document msgdoc = DocumentHelper.parseText(xml);
		Element root = msgdoc.getRootElement();
		Element node = root.element(prop);
		return node.getTextTrim();
	}
	

	/**
	 * 微信支付签名 
	 * @param keys
	 * @param paramMap
	 * @return
	 */
	public static String wxpaySign(String[] keys, Map<String, String> paramMap, String payKey) {
		Arrays.sort(keys); // 排序
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < keys.length; i++) {
			sb.append(keys[i] + "=" + paramMap.get(keys[i])).append("&");
		}
		sb.append("key=" + payKey);
		logger.info("weixin pay---签名加密前：" + sb.toString());
		String sign = WeixinMessageDigestUtil.stringMD5(sb.toString());
		logger.info("weixin pay---签名加密后：" + sign);
		return sign;
	}


	/**
	 * 商户接收到支付通知后反馈 
	 * @param success - 0-不成功， 1-成功
	 * @param msg		错误消息
	 */
	public static void wxpayFeedback(HttpServletResponse response, int success, String msg) {
//		<xml>
//		<return_code><![CDATA[FAIL ]]></return_code>
//		<return_msg><![CDATA[SYSERR]]></return_msg>
//		</xml>
		String xml = "";
		if(success == 1){
			xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg></return_msg></xml>";
		}else{
			xml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA["+ msg +"]]></return_msg></xml>";
		}
		try {
			response.getWriter().write(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("商户反馈给微信支付接口完成:");
	}
	
	/**
	 * 从json数据里，根据属性返回对应的值
	 * @param json
	 * @param prop
	 * @return
	 */
	public static String getValueFromJson(String json, String prop){
		JSONObject fromObject = JSONObject.fromObject(json);
		if(fromObject.getString(prop) == null){
			return "";
		}else{
			return fromObject.getString(prop);
		}
	}
	

	/**
	 * 微信签名算法 
	 * @param keys
	 * @param paramMap
	 * @return
	 */
	public static String wxJsSign(String[] keys, Map<String, String> paramMap) {
		Arrays.sort(keys); // 排序
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < keys.length; i++) {
			if(i < keys.length -1){
				sb.append(keys[i] + "=" + paramMap.get(keys[i])).append("&");
			}else{
				sb.append(keys[i] + "=" + paramMap.get(keys[i]));
			}
		}
		logger.info("签名加密前：" + sb.toString());
		return WeixinMessageDigestUtil.getInstance().encipher(sb.toString());
	}

}
