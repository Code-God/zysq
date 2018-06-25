package actions.integ.weixin;

import java.util.List;
import java.util.Map;

import model.vo.WxUser;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import util.HttpUtils;
import wx.cache.WxParamCache;

import com.base.log.LogUtil;
import com.base.tools.Version;

/**
 * 
 * weixin工具类
 * @author jacky
 * @version 1.0
 * @since cupid 1.0
 */
public class WeiXinUtil {
	private static Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	/**
	 * 回复文本消息
	 * @param toUserName - 消息接收方
	 * @param fromUserName - 消息发送方
	 * @param content
	 * @return
	 */
	public static String getResponseXml(String toUserName, String fromUserName, String content) {
		StringBuffer responsXml = new StringBuffer("<xml>");
		responsXml.append("<ToUserName><![CDATA[" + toUserName + "]]></ToUserName>");
		responsXml.append("<FromUserName><![CDATA[" + fromUserName + "]]></FromUserName>");
		responsXml.append("<CreateTime>" + System.currentTimeMillis() + "</CreateTime>");
		responsXml.append("<MsgType><![CDATA[text]]></MsgType>");
		responsXml.append("<Content><![CDATA[" + content + "]]></Content>");
		responsXml.append("</xml>");
		return responsXml.toString();
	}
	
	
	
	/**
	 * 回复（多）图文消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String getResponsePicTextXml(String toUserName, String fromUserName, List<Map<String, String>> picTexts) {
		StringBuffer responsXml = new StringBuffer("<xml>");
		responsXml.append("<ToUserName><![CDATA[" + toUserName + "]]></ToUserName>");
		responsXml.append("<FromUserName><![CDATA[" + fromUserName + "]]></FromUserName>");
		responsXml.append("<CreateTime>" + System.currentTimeMillis() + "</CreateTime>");
		responsXml.append("<MsgType><![CDATA[news]]></MsgType>");
		responsXml.append("<ArticleCount>"+ picTexts.size() +"</ArticleCount>");
		responsXml.append("<Articles>");
		for (Map<String, String> map : picTexts) {
			responsXml.append("<item>");
			responsXml.append("<Title><![CDATA["+ map.get("title") +"]]></Title>");
			responsXml.append("<Description><![CDATA["+ map.get("desc") +"]]></Description>");
			responsXml.append("<PicUrl><![CDATA["+ map.get("picUrl") +"]]></PicUrl>");
			responsXml.append("<Url><![CDATA["+ map.get("url") +"]]></Url>");
			responsXml.append("</item>");
		}
		responsXml.append("</Articles>");
		responsXml.append("</xml>");
		return responsXml.toString();
	}



	/**
	 * //第一步，获取token, APPID=wxc572b8215150b75a  APPSec = 5d7a1109d4ef780c91076d632f5bc572
		//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	 * @return
	 */
	public static String getToken() {
		String APPID = Version.getInstance().getNewProperty("APPID");
		String APPSECRET = Version.getInstance().getNewProperty("APPSECRET");
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID + "&secret="+ APPSECRET;
		
		String doGet = HttpUtils.doGet(url, null);
		if("".equals(doGet)){
			return null;
		}
		JSONObject fromObject = JSONObject.fromObject(doGet);
		if(fromObject.get("access_token") != null){
			String token = (String) fromObject.get("access_token");
			
			return token;
		}else{
			return null;
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		String APPID = "wxae7b26149dedd9ba";
		String APPSECRET = "0fbe4d06a48bba01dc458fa119737683";
//		String APPID = "wxc572b8215150b75a";
//		String APPSECRET = "5d7a1109d4ef780c91076d632f5bc572";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID + "&secret="+ APPSECRET;
		
		String doGet = HttpUtils.doGet(url, null);
		
		JSONObject fromObject = JSONObject.fromObject(doGet);
		String token = (String) fromObject.get("access_token");
//		String openId = "o8Eu5juaC7R1kU_2kHrGPW_4xBQg";
		String openId = "o8nVpt0hOSDk4WWOJ9MXnxo8rPto";
		String queryUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openId+"&lang=zh_CN";
		//网页授权方式
//		String queryUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+openId+"&lang=zh_CN";
		
		
		
//		String menuJson = "{\"button\":[{\"type\":\"click\",\"name\":\"11\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"33\",\"key\":\"V1001_TODAY_SINGER\"}]}";
//		String menuJson = "{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"歌手简介\",\"key\":\"V1001_TODAY_SINGER\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}";
//		String menuJson1 = "%7B%0D%0A++++%22button%22%3A+%5B%0D%0A++++++++%7B%0D%0A++++++++++++%22name%22%3A+%2211%22%2C+%0D%0A++++++++++++%22type%22%3A+%22click%22%2C+%0D%0A++++++++++++%22key%22%3A+%22V01_S01%22%0D%0A++++++++%7D%2C+%0D%0A++++++++%7B%0D%0A++++++++++++%22name%22%3A+%2222%22%2C+%0D%0A++++++++++++%22type%22%3A+%22click%22%2C+%0D%0A++++++++++++%22key%22%3A+%22V02_S01%22%0D%0A++++++++%7D%2C+%0D%0A++++++++%7B%0D%0A++++++++++++%22name%22%3A+%2231%22%2C+%0D%0A++++++++++++%22type%22%3A+%22click%22%2C+%0D%0A++++++++++++%22key%22%3A+%22V03_S01%22%0D%0A++++++++%7D%0D%0A++++%5D%0D%0A%7D";
//		String menuJson = "{"+
//		        "\"button\":["+
//		            "{\"name\":\"1111\","+
//		            "\"type\":\"click\"," +
//		            "\"key\":\"V01_S01\"" +
//		            "}"+
//		        "]"+
//		    "}";
//		//{"button":[{"type":"click","name":"1111","key":"V01_S01","sub_button":[]}]}
//////		//先删除
////		String delUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + token;
		doGet = HttpUtils.doGet(queryUrl, null);
////		System.out.println("after delete--- "+doGet);
//////		
//////		//查询
//		String queryUrl = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + token;
//		doGet = HttpUtils.doGet(queryUrl, null);
//		System.out.println("after query--- "+doGet);
//		JSONObject jo = JSONObject.fromObject(doGet);
//		System.out.println(jo.get("menu").toString());
//		String posturl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token;
		System.out.println(doGet);
//		System.out.println(menuJson);
//		String doPost = HttpUtils.doPost(posturl, null, menuJson);
//		System.out.println(doPost);
	}


	/**
	 * 提交菜单 https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
	 * @param menuJson
	 * @param token
	 */
	public static boolean postMenu(String menuJson, String token) {
		String posturl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token;
		System.out.println(token);
		System.out.println(menuJson);
		String doPost = HttpUtils.doPost(posturl, null, menuJson);
		JSONObject fromObject = JSONObject.fromObject(doPost);
		if(fromObject.get("errcode") != null){
			if(fromObject.get("errcode").toString().equals("0")){
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * 发送模板消息
	 * @param toUserOpenId
	 * @param templateId
	 * @param msgs - 按顺序的消息文本
	 *  {
           "touser":"OPENID",
           "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
           "url":"http://weixin.qq.com/download",            
           "data":{
                   "first": {
                       "value":"恭喜你购买成功！",
                       "color":"#173177"
                   },
                   "keynote1":{
                       "value":"巧克力",
                       "color":"#173177"
                   },
                   "keynote2": {
                       "value":"39.8元",
                       "color":"#173177"
                   },
                   "keynote3": {
                       "value":"2014年9月22日",
                       "color":"#173177"
                   },
                   "remark":{
                       "value":"欢迎再次购买！",
                       "color":"#173177"
                   }
           }
       }
	 * @return
	 * 
	 * 正常返回
	 * {
           "errcode":0,
           "errmsg":"ok",
           "msgid":200228332
       }
	 * 
	 */
	public static boolean sendTemplateMsg(String toUserOpenId, String appId, String appsecret, String templateId, List<String> msgs) {
		String token = getToken(appId, appsecret);
		String posturl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token;
		
		JSONObject jo = new JSONObject();
		JSONObject datas = new JSONObject();
		JSONObject subJo1 = new JSONObject();
		subJo1.put("value", msgs.get(0));
		subJo1.put("color", "#173177");
		JSONObject subJo2 = new JSONObject();
		subJo2.put("value", msgs.get(1));
		subJo2.put("color", "#173177");
		JSONObject subJo3 = new JSONObject();
		subJo3.put("value", msgs.get(2));
		subJo3.put("color", "#173177");
		JSONObject subJo4 = new JSONObject();
		subJo4.put("value", msgs.get(3));
		subJo4.put("color", "#173177");
		JSONObject subJo5 = new JSONObject();
		subJo5.put("value", msgs.get(4));
		subJo5.put("color", "#173177");
		
		datas.put("first", subJo1);
		datas.put("keynote1", subJo2);
		datas.put("keynote2", subJo3);
		datas.put("keynote3", subJo4);
		datas.put("remark", subJo5);
		
		jo.put("touser", toUserOpenId);
		jo.put("template_id", templateId);
		jo.put("url", "");//推送消息的链接
		jo.put("data", datas);
		
		String doPost = HttpUtils.doPost(posturl, null, jo.toString());
		JSONObject fromObject = JSONObject.fromObject(doPost);
		if(fromObject.get("errcode") != null){
			if(fromObject.get("errcode").toString().equals("0")){
				return true;
			}
		}
		return false;
		
	}


	/**
	 * 查询菜单
	 * @param token
	 * @return
	 */
	public static String getLastMenu(String token) {
		String queryUrl = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + token;
		String doGet = HttpUtils.doGet(queryUrl, null);
		doGet = HttpUtils.doGet(queryUrl, null);
		JSONObject jo = JSONObject.fromObject(doGet);
		if(jo.get("menu") != null){
			return jo.get("menu").toString();
		}else{
			return null;
		}
	}



	/**
	 * 判断是否来自手机客户端
	 * 
	 * @return
	 */
	public static boolean isFromMobile(String userAgent) {
		
		//测试模式下，根据配置文件返回
		if("y".equals(Version.getInstance().getNewProperty("wxtest"))){
			if("mob".equals(Version.getInstance().getNewProperty("testmode"))){
				return true;
			}else{
				return false;
			}
		}
		
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod", "nokia",
				"samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma", "docomo",
				"up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos", "techfaith",
				"palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem", "wellcom", "bunjalloo",
				"maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos", "pantech", "gionee", "portalmmm",
				"jig browser", "hiptop", "benq", "haier", "^lct", "320x320", "240x320", "176x220", "w3c ", "acs-", "alav",
				"alca", "amoi", "audi", "avan", "benq", "bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",
				"doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g",
				"lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-", "newt", "noki",
				"oper", "palm", "pana", "pant", "phil", "play", "port", "prox", "qwap", "sage", "sams", "sany", "sch-",
				"sec-", "send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar", "sony", "sph-", "symb", "t-mo",
				"teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp", "wapr",
				"webc", "winw", "winw", "xda", "xda-", "Googlebot-Mobile" };
		boolean isMoblie = false;
		for (int i = 0; i < mobileAgents.length; i++) {
			if (userAgent.toLowerCase().indexOf(mobileAgents[i]) != -1) {
				isMoblie = true;
				break;
			}
		}
		return isMoblie;
	}



	public static String getNickName(String openId) {
		String token = getToken();
		String queryUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openId+"&lang=zh_CN";
		String doGet = HttpUtils.doGet(queryUrl, null);
		JSONObject fromObject = JSONObject.fromObject(doGet);
		//subscribe
		if("0".equals(fromObject.get("subscribe")+"")){
			return "已取消关注";//已经取消关注。
		}else if(fromObject.get("errmsg") != null){//errmsg
			return "";
		}
		String nickName = fromObject.get("nickname").toString();
		return nickName;
	}

	/**
	 * 生成转移到多客服的消息，这个消息服务器会自动识别并转到多客服客户端 
	 *  
	 * <xml>
		    <ToUserName><![CDATA[touser]]></ToUserName>
		    <FromUserName><![CDATA[fromuser]]></FromUserName>
		    <CreateTime>1399197672</CreateTime>
		    <MsgType><![CDATA[transfer_customer_service]]></MsgType>
		    <TransInfo>
		        <KfAccount>test1@test</KfAccount>
		    </TransInfo>
		</xml>
		 
	 * @param content
	 * @param toUserName - 接收客服消息的微信号
	 * @param fromUserName - 开发者微信号
	 * @param kfaccount - 客服账号
	 * @return
	 */
	public static String getServiceDeskMsg(String content, String toUserName, String fromUserName, String kfaccount) {
		StringBuffer responsXml = new StringBuffer("<xml>");
		//接收人
		responsXml.append("<ToUserName><![CDATA[" + toUserName + "]]></ToUserName>");
		//开发者微信号
		responsXml.append("<FromUserName><![CDATA[" + fromUserName + "]]></FromUserName>");
		responsXml.append("<CreateTime>" + System.currentTimeMillis() + "</CreateTime>");
		responsXml.append("<MsgType><![CDATA[transfer_customer_service]]></MsgType>");
		if(kfaccount.indexOf("|") != -1){//如果是多个客服
			String[] split = kfaccount.split("|");
			for (String kf : split) {
				responsXml.append("<TransInfo><KfAccount>"+ kf + "@SandvikCoromantGCR</KfAccount></TransInfo>");
			}
		}else{
			responsXml.append("<TransInfo><KfAccount>"+ kfaccount +"</KfAccount></TransInfo>");
		}
		responsXml.append("</xml>");
		return responsXml.toString();
	}

	
	
	/**
	 * 根据分销商来获取微信用户的信息：
	 * 
	 * @param openId
	 * @param org
	 */
	public static WxUser getWxUserByOrg(String openId, String appId, String appSecret) {
		WxUser wxuser = new WxUser();
		try{
			String token = getToken(appId, appSecret);
			//https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
			String queryUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openId+"&lang=zh_CN";
			String doGet = HttpUtils.doGet(queryUrl, null);
			//{"subscribe":1,"openid":"o8Eu5juaC7R1kU_2kHrGPW_4xBQg","nickname":"Amy.Q???Q","sex":2,"language":"zh_CN","city":"长宁","province":"上海","country":"中国","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/1kFy1JdGd8xhmnpEdyFUjZdP8fxaAic9Iyu7NXAgcacqfsHasTcTSn7ADbOH53c0NQZLWAJ1sYOFRib2YLfs8Bia0MIfyoeBoIe\/0","subscribe_time":1409756649,"remark":""}
			
			JSONObject fromObject = JSONObject.fromObject(doGet);
			
			if(fromObject.get("nickname") == null && fromObject.get("access_token") != null){
				//通过全局accesstoken
				
			}
			
			//subscribe
			wxuser.setOpenid(openId);
			wxuser.setNickName(fromObject.getString("nickname"));
			wxuser.setSex(fromObject.getString("sex"));
			wxuser.setCity(fromObject.getString("city"));
			wxuser.setProvince(fromObject.getString("province"));//province
			wxuser.setCountry(fromObject.getString("country"));
			wxuser.setHeadimgurl(fromObject.getString("headimgurl"));
			wxuser.setSubscribeTime(fromObject.getString("subscribe_time"));//
			logger.info("get wx user info by OpenID:::"+wxuser.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return wxuser;
	}
	/**
	 * 微信授权方式获取用户信息, 和公众号获取用户信息接口不同。比如subscribeTime不同
	 * 
	 * @param openId
	 * @param org
	 */
	public static WxUser getWxAuthUserByOrg(String openId, String acToken) {
		WxUser wxuser = new WxUser();
		try{
			//https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
			String queryUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+acToken+"&openid="+openId+"&lang=zh_CN";
			String doGet = HttpUtils.doGet(queryUrl, null);
			//
//			{
//				   "openid":" OPENID",
//				   " nickname": NICKNAME,
//				   "sex":"1",
//				   "province":"PROVINCE"
//				   "city":"CITY",
//				   "country":"COUNTRY",
//				    "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46", 
//					"privilege":[
//					"PRIVILEGE1"
//					"PRIVILEGE2"
//				    ],
//				    "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//				}
			JSONObject fromObject = JSONObject.fromObject(doGet);
			//subscribe
			wxuser.setOpenid(openId);
			wxuser.setNickName(fromObject.getString("nickname"));
			wxuser.setSex(fromObject.getString("sex"));
			wxuser.setCity(fromObject.getString("city"));
			wxuser.setProvince(fromObject.getString("province"));//province
			wxuser.setCountry(fromObject.getString("country"));
			wxuser.setHeadimgurl(fromObject.getString("headimgurl"));
			try{
				wxuser.setUnionid(fromObject.getString("unionid"));
			}catch(Exception e){
				
			}
			logger.info("get wx user info by OpenID:::"+wxuser.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return wxuser;
	}
	/**
	 * 根据appid和appsecret获取token 
	 * @param appId
	 * @param appsecret
	 * @return
	 */
	public static String getToken(String appId, String appsecret) {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId + "&secret="+ appsecret;
		
		String doGet = HttpUtils.doGet(url, null);
		if("".equals(doGet)){
			return null;
		}
		JSONObject fromObject = JSONObject.fromObject(doGet);
		if(fromObject.get("access_token") != null){
			String token = (String) fromObject.get("access_token");
			return token;
		}else{
			return null;
		}
	}
	

	/**
	 * 根据考试分数返回文字信息 
	 * @param score
	 * @return
	 */
	public static String getTestMsg(int score) {
		String msg = "";
		if(score == 100){
			msg = "不是吧，这么厉害，竟然得了满分！";
		}else if(score >= 90 && score < 100){
			msg = "厉害，离满分不远了，请再接再厉！";
		}else if(score < 90 && score > 60){
			msg = "请再接再厉！";
		}else if(score >= 40 && score < 60){
			msg = "不及格哦，再加把劲吧！";
		}else if(score < 40){
			msg = "不是吧， 这分数简直惨不忍睹啊，您好自为之吧！";
		}
		return msg;
	}
	
	/**
	 * 
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
	 * 从缓存获取jsapiTicket 
	 * @return
	 */
	public static String getJSApiTicket(String orgCode) {
		//这里的缓存，应该还要传递一个参数： 分销商CODE
		return WxParamCache.getInstance().getWxJsParam(orgCode, WxParamCache.JS_TICKET);
	}

	/**
	 * 从缓存获取access_token
	 */
	public static String getAccessToken(String orgCode){
		return WxParamCache.getInstance().getWxJsParam(orgCode, WxParamCache.TOKEN);
	}
	
	/**
	 * 从缓存获取datacenter_token
	 */
	public static String getDataCenterToken(String orgCode){
		return WxParamCache.getInstance().getWxJsParam(orgCode, WxParamCache.DataCenter_TOKEN);
	}
}
