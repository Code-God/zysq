package actions.integ;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bo.WxRulesImage;
import model.bo.auth.Org;
import model.bo.drug.DrugScoreLog;
import model.bo.food.ConfigParam;
import model.vo.WxUser;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugScoreLogService;
import service.intf.AdminService;
import service.intf.IFenxiaoService;
import service.intf.IWeiXinService;
import service.intf.PublicService;
import util.HttpUtils;
import util.TwoDimensionCode;
import util.UploadUtil;
import util.WeixinMessageDigestUtil;
import util.WxPayUtil;
import wx.data.WxDataCommands;
import wx.data.WxDataHandler;
import actions.integ.weixin.WeiXinUtil;
import actions.integ.weixin.WeixinConstants;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.constants.CupidStrutsConstants;
import com.wfsc.common.bo.ad.AdvConfig;
import com.wfsc.common.bo.user.User;
import com.wfsc.common.bo.user.WeiChat;
import com.wfsc.services.account.IUserService;
import com.wfsc.services.ad.IAdService;
import com.wfsc.util.DateUtil;
@Controller("WeixinAction")
@Scope("prototype")
public class WeiXinAction extends DispatchPagerAction {

	private static final long serialVersionUID = 4622027781423053486L;

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	private final String TOKEN = "Lvze2015";
	@Autowired
	private IWeiXinService weiXinService;
	
	@Autowired
	private IDrugScoreLogService drugScoreLogService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private AdminService adminService;
	
	
	/**
	 * 客服标记map，用来记录某个用户当前是进行何种咨询
	 * key-openId
	 * value- 根据KF_K_PRODUCT获取的客服号
	 */
	public static Map<String, String> kf_flag_map = new ConcurrentHashMap<String, String>();
	
	private String url;

	/**
	 * 接入和转发 -------------------------------- 被添加：
	 * 初次进入需要登陆（即绑定），需要提供身份证号码进行绑定
	 * 一档绑定，下次再进入，只要检查数据库该openId已经绑定了身份证号码，就不用再登陆了。
	 * @returnorg.apache.coyote.ajp.AjpMessage
	 * @throws Exception
	 */
	public String verify() throws Exception {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		logger.info("进入微信消息处理方法...");
		response.setCharacterEncoding("utf-8");
		if (StringUtils.isNotEmpty(echostr)) {
			logger.info("  开始校验...");
			if (this.checkSignature(signature, timestamp, nonce, echostr)) {
				response.getWriter().write(echostr);
			}
			logger.info("结束校验");
		} else {
			logger.info("接收到微信消息...");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				// line = new String(line.getBytes("utf-8"));
				sb.append(line);
			}
			logger.info("data:" + sb.toString());
			if (sb.toString().equals("")) {
				response.getWriter().write("");
				return null;
			}
			Document msgdoc = DocumentHelper.parseText(sb.toString());
			Element root = msgdoc.getRootElement();
			Element node = root.element("MsgType");
			String MsgType = node.getTextTrim();
			node = root.element("ToUserName");// 开发者账号/公众号----------- 
			String toUserName = node.getTextTrim();
			node = root.element("FromUserName");// 发送者openId
			// 即：openId
			String fromUserName = node.getTextTrim();
			
//			Element root = null;
//			String MsgType = "image";
//			Element node = null;
//			String fromUserName = "123";
//			String toUserName = "123";
			
			String responseXml = "";
			if (MsgType != null && MsgType.equals("event")) {
				node = root.element("Event");
				// 事件名称
				String event = node.getTextTrim();
				String eventkey = root.element("EventKey").getTextTrim();
				logger.info("MsgType==" + MsgType + "  event=" + event);
				// ----------->>>>>>>>>>>>>>>>>>>> 订阅消息自动回复 <<<<<<<<<<<<<<<<<<<<<<-------------
				if (WeixinConstants.MSG_SUBSCRIBE.equals(event)) {
					logger.info("新关注用户..." + fromUserName);
					//用户关注后反馈的msg
					String msg = "";
					if(eventkey.startsWith("qrscene_")){
						//TODO 处理带参数二维码关注事件。。。。
						//获取二维码参数						
						String inviterid=eventkey.replace("qrscene_", "");
						logger.info("扫描二维码进行关注，并生成新用户，保存推荐人id"+inviterid);
//						String code = request.getParameter("code");			
//						
//						//根据orgid获取appid和appsecret
						AdminService adminService = (AdminService) ServerBeanFactory.getBean("adminService");
						HttpSession session2 = request.getSession();
//					
//					
						session2.setAttribute(CupidStrutsConstants.REFERRER_USER_ID, inviterid);//报名时所用推荐人id
//						
						String appId = adminService.getConfigParam(ConfigParam.APPID);
						String secret = adminService.getConfigParam(ConfigParam.APP_SECRET);
//						System.out.println("--------------------------code:"+code);
//						String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
//						String doPost = HttpUtils.doPost(tokenUrl, null, null);
//						String acToken = WxPayUtil.getValueFromJson(doPost, "access_token");
						//此处要用网页授权的方式i获取用户信息
//						WxUser wxUserByOrg = WeiXinUtil.getWxAuthUserByOrg(fromUserName, acToken);	
						WxUser wxUserByOrg = WeiXinUtil.getWxUserByOrg(fromUserName, appId, secret);
						wxUserByOrg.setOpenid(fromUserName);
						adminService.saveWxCustomer(wxUserByOrg, null,  null,inviterid);
						
						logger.info("扫描二维码进行关注完成");
					}else{
						IFenxiaoService fenxiaoService = (IFenxiaoService) ServerBeanFactory.getBean("fenxiaoService");
						//根据原始ID，获取该公众号账号信息
						
						//String access_token=WeiXinUtil.getAccessToken("000001");
						//String ticketurl="https://api.weixin.qq.com/cgi-bin/shorturl?access_token="+access_token;
						//JSONObject param=new JSONObject();
						//param.put("action", "long2short");
						//param.put("long_url", "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIwMTM2ODMzNg==&scene=124#wechat_redirect");					
						//String ticketpost=HttpUtils.doPost(ticketurl, null, param.toString());
						//System.out.println(ticketpost+"**********************************");
						
						weiXinService.createUser(fromUserName, toUserName);
						Org org = fenxiaoService.getOrgByWxId(toUserName);
						msg = (org.getWelcomeMsg()==null?"您好，欢迎关注："+org.getOrgname():org.getWelcomeMsg());
						//msg = msg+"  "+ticketpost;
					}
					responseXml = WeiXinUtil.getResponseXml(fromUserName, toUserName,  msg);
					response.getWriter().write(responseXml);
					return null;
				}
				//多客服关闭会话事件
//				<xml>
//			     <ToUserName><![CDATA[touser]]></ToUserName>
//			     <FromUserName><![CDATA[fromuser]]></FromUserName>
//			     <CreateTime>1399197672</CreateTime>
//			     <MsgType><![CDATA[event]]></MsgType>
//			     <Event><![CDATA[kf_close_session]]></Event>
//			     <KfAccount><![CDATA[test1@test]]></KfAccount>
//			 </xml>
				if (WeixinConstants.KF_CLOSE_SESSION.equals(event)) {
					String kfAccount = root.element("KfAccount").getTextTrim();
					logger.info(kfAccount + " has closed the session...with " + fromUserName);
					
					//清理缓存
					kf_flag_map.remove(fromUserName);
					logger.info("kf flag removed...");
					//发送通知给用户
					responseXml = WeiXinUtil.getResponseXml(fromUserName, toUserName, "客服已经关闭了您的会话， 如果还需要与客服联系，请再次点击客服菜单。谢谢您的配合！");
					response.getWriter().write(responseXml);
					logger.info(responseXml);
					return null;
				}
				
				if(WeixinConstants.MSG_UNSUBSCRIBE.equals(event)){//取消订阅
					logger.info(fromUserName + "取消订阅xxxxxxxxxxxxxxxxxxxxxx");
					AdminService s = (AdminService) ServerBeanFactory.getBean("adminService");
					User userByOpenId = s.getUserByOpenId(fromUserName);
					if(userByOpenId != null){
						//更新用户表中的订阅标记
						userByOpenId.setSubstate(0);
						s.updateUser(userByOpenId);
						logger.info("更新订阅标记完成....");
					}
				}
				
				
				// 菜单事件
				// <xml>
				// <ToUserName><![CDATA[toUser]]></ToUserName>
				// <FromUserName><![CDATA[FromUser]]></FromUserName>
				// <CreateTime>123456789</CreateTime>
				// <MsgType><![CDATA[event]]></MsgType>
				// <Event><![CDATA[CLICK]]></Event>
				// <EventKey><![CDATA[EVENTKEY]]></EventKey>
				// </xml>
				//这里都是菜单点击事件
				if (event.equalsIgnoreCase("CLICK")) {
					node = root.element("EventKey");
					String key = node.getTextTrim();
					
					logger.info("key==" + key);
					if(key.equalsIgnoreCase("DKF_M")){
						//*** ******************************* 多客服处理 *******************************
						//对于不同公众号，多客服提示信息可以自定义，存在org表里 TODO 这里需要修改，从数据库读取
						String msg = Version.getInstance().getNewProperty("DKF_TIP");
						logger.info("点击了菜单："+ key +"  $$$$$$$$$$$$" + msg);
						responseXml = WeiXinUtil.getResponseXml(fromUserName, toUserName, msg);
						
						//客服工号，不同的公众号， 默认客服的账号需要设置，这里需要从数据库读取（comorg表）
						Org org = weiXinService.getOrgBySourceId(toUserName);
						logger.info("多客服菜单点击，此公众号为：" + org.getOrgname() + "|" + org.getWxID());
						String kfAccount = org.getKfAccount();
						logger.info(kf_flag_map.size() + " | kfAcc=" + kfAccount + " 放入缓存fromUserName--[" + fromUserName + "]");
						kf_flag_map.put(fromUserName, kfAccount);
						
						response.getWriter().write(responseXml);
						return null;
					}else{
						// 有些是图文消息的，要先推送
						List<Map<String, String>> picTexts = new ArrayList<Map<String, String>>();
						//菜单个数
						String s = Version.getInstance().getNewProperty(key + "_count");
						if(s == null){
							responseXml = WeiXinUtil.getResponseXml(fromUserName, toUserName, "玩命更新中，敬请期待！");
							response.getWriter().write(responseXml);
							return null;
						}
						int n = Integer.valueOf(s);
						logger.info(key + " - 此菜单个数：" + n);
						for(int i=1; i<=n; i++){
							Map<String, String> m1 = new HashMap<String, String>();
							m1.put("title", Version.getInstance().getNewProperty(key + "_title_" + i));
							m1.put("desc", Version.getInstance().getNewProperty(key + "_desc_" + i));
							m1.put("picUrl", Version.getInstance().getNewProperty(key + "_picUrl_" + i));
							if(Version.getInstance().getNewProperty(key + "_url_" + i).indexOf("jms.mycloudexpo.com") != -1){
								//只有对接站点才会传递openId
								m1.put("url", Version.getInstance().getNewProperty(key + "_url_" + i) + "&openId=" + fromUserName);
							}else{
								m1.put("url", Version.getInstance().getNewProperty(key + "_url_" + i));
							}
							picTexts.add(m1);
							logger.info("#####"+m1);
						}
						responseXml = WeiXinUtil.getResponsePicTextXml(fromUserName, toUserName, picTexts);
						response.getWriter().write(responseXml);
						return null;
					}
				}else if(event.equalsIgnoreCase("VIEW")){//对于View类型的菜单事件的处理
//					<xml>
//					<ToUserName><![CDATA[toUser]]></ToUserName>
//					<FromUserName><![CDATA[FromUser]]></FromUserName>
//					<CreateTime>123456789</CreateTime>
//					<MsgType><![CDATA[event]]></MsgType>
//					<Event><![CDATA[VIEW]]></Event>
//					<EventKey><![CDATA[www.qq.com]]></EventKey>
//					</xml>
					String url = root.element("EventKey").getText();
					logger.info("跳转链接菜单被点击...." + url + "|" + DateUtil.getLongCurrentDate());
				}
			}else if (!StringUtils.isEmpty(MsgType) && "image".equals(MsgType)){//消息类型为图片
				logger.info("当前的消息类型是：" + MsgType);
				//消息创建时间 （整型）
				node = root.element("CreateTime");
				String createTime = node.getTextTrim();
				//图片链接（由系统生成）
				node = root.element("PicUrl");
				String picUrl = node.getTextTrim();
				//图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
				node = root.element("MediaId");
				String mediaId = node.getTextTrim();
				//消息id，64位整型
				node = root.element("MsgId");
				String msgId = node.getTextTrim();
				//保存会话
				WeiChat weiChat = new WeiChat();
				weiChat.setToUserName(toUserName);
				weiChat.setFromUserName(fromUserName);
				weiChat.setCreateTime(new Date());
				weiChat.setMsgType(MsgType);
				weiChat.setMediaId(mediaId);
				weiChat.setMsgId(msgId);
				weiChat.setReviewState(new Integer(0));
				String url = this.saveToFile(picUrl,fromUserName);
				if("".equals(url)){
					url = picUrl;
				}
				weiChat.setPicUrl(url);
				userService.saveWeiChat(weiChat);
				
//				WeiChat weiChat = new WeiChat();
//				weiChat.setToUserName("11");
//				weiChat.setFromUserName("12");
//				weiChat.setCreateTime(new Date());
//				weiChat.setMsgType("image");
//				weiChat.setMediaId("1212");
//				weiChat.setMsgId("121");
//				String picUrl = "http://mmbiz.qpic.cn/mmbiz_jpg/GbI8bpJ3mxYxvkBUCf4bNxjswduuncvQBCLZ0hGzCiaSgWWLyfCQMaaIPA7KMiagHvgFjL3tPniaFic5nMR89jdhPg/0";
//				String url = this.saveToFile(picUrl,fromUserName);
//				if("".equals(url)){
//					url = picUrl;
//				}
//				weiChat.setPicUrl(url);
//				userService.saveWeiChat(weiChat);
				
				logger.info("自动回复文本...");
				String reply = "";
				//调数据库回复模板
				List<WxRulesImage> rules = adminService.getAllWxRulesImage();
				if(null != rules && rules.size()>0){
					for(WxRulesImage rule : rules){
						if("text".equals(rule.getRespType())){
							reply = rule.getRespContent();
						}
					}
				}
				System.out.println(reply);
				responseXml = WeiXinUtil.getResponseXml(fromUserName, toUserName, reply);
				response.getWriter().write(responseXml);
				return null;
				
			} else if (StringUtils.isEmpty(MsgType) || !"text".equals(MsgType)) {

				logger.info("不支持的消息类型是：" + MsgType);
				responseXml = WeiXinUtil.getResponseXml(fromUserName, toUserName, "本系统目前暂不受理文本以外的消息类型！");
				response.getWriter().write(responseXml);
				return null;
			} else {
				node = root.element("CreateTime");
				String CreateTime = node.getTextTrim();
				node = root.element("Content");
				String Content = node.getTextTrim();
				node = root.element("MsgId");
				String MsgId = node.getTextTrim();
				logger.info("content=" + Content);
				// // 以#开头的是命令行
				if (Content.startsWith("#")) {
					logger.info("接收到指令消息..");
					// String cmd = Content.substring(1, Content.length());
					// logger.info("cmd==" + cmd);
					// if(cmd.equalsIgnoreCase("help")){
					// logger.info("发送图文消息...");
					// //图文消息反馈
					// List<Map<String,String>> picTexts = new ArrayList<Map<String,String>>();
					// createTW(picTexts);
					// responseXml = WeiXinUtil.getResponsePicTextXml(fromUserName, toUserName, picTexts);
					// logger.info(responseXml);
					// }else{
					// // 解析命令,并返回相应的消息
					// WxCmdInterceptor wi = new BaseInterceptor();
					// responseXml = WeiXinUtil.getResponseXml(fromUserName, toUserName, wi.intercept(fromUserName, cmd));
					// }
					//				
					// // 反馈消息
					// response.getWriter().write(responseXml);
				} else {
					// ----------->>>>>>>>>>>>>>>>>>>> 关键字消息自动回复 <<<<<<<<<<<<<<<<<<<<<<-------------
					logger.info("fromUserName==["+ fromUserName +"] | 普通消息..." + Content + "  kf_flag_map.get(fromUserName)==" + kf_flag_map.get(fromUserName));
					logger.info("kf_flag_map.size()=" + kf_flag_map.size());
					//如果请求过多客服， 这里就由多客服接管
					//默认客服
					//如果没请求过特定客服
					Org org = weiXinService.getOrgBySourceId(toUserName);
					logger.info(fromUserName + "####" + toUserName+ "####" +Content);
					String kfaccount = org.getKfAccount();
					if(kf_flag_map.get(fromUserName) != null){
						kfaccount = kf_flag_map.get(fromUserName);
						logger.info("接受到文本消息，目标客服：：" + kfaccount);
						// 转移到多客服
						responseXml = WeiXinUtil.getServiceDeskMsg(Content, fromUserName, toUserName, kfaccount);
						response.getWriter().write(responseXml);
					}else{
						
						// 这里需要处理31个或者将来更多的自定义规则,规则已经保存到数据库，从数据库里取出来依次判断即可
						responseXml = this.weiXinService.intercept(fromUserName, toUserName, Content);
						logger.info("responseXml==" + responseXml);
						response.getWriter().write(responseXml);
					}
					return null;
				}
			}
		}
		return null;
	}
	
	public String contactDKF() throws Exception{
		try{
			String openId = this.getCurrentUser().getOpenId();
			logger.info("in contactDKF----- openId==" + openId);
			//*** ******************************* 多客服处理 *******************************
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			//客服工号，不同的公众号， 默认客服的账号需要设置，这里需要从数据库读取（comorg表）
			Org org = service.getOrgById(Long.valueOf(Version.getInstance().getNewProperty("testOrgId")));
			logger.info("点击我要咨询，此公众号为：" + org.getOrgname() + "|" + org.getWxID());
			String kfAccount = org.getKfAccount();
			logger.info(kf_flag_map.size() + " | kfAcc=" + kfAccount + " 放入缓存fromUserName--[" + openId + "]");
			kf_flag_map.put(openId, kfAccount);
			response.getWriter().write("{\"msg\":\"ok\"}");
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("{\"msg\":\"fail\"}");
		}
		return null;
	}
	
	
	/**
	 * 
	 * 微信支付告警反馈接口
	 * @param request
	 * @param response
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 * @throws IOException
	 */
	public String alarm()
			throws IOException {
		logger.info("接收到微信支付告警消息...");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		logger.info("data:" + sb.toString());
		 
		return null;
	}
//	/**
//	 * 查询loginId
//	 * @param response
//	 * @param toUserName
//	 * @param fromUserName
//	 * @return
//	 * @throws IOException
//	 */
//	private String queryLoginId(HttpServletResponse response, String toUserName, String fromUserName){
//		//返回中奖信息
//		String msg = "";
//		PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
//		User userByOpenId = service.getUserByOpenId(fromUserName);
//		if(userByOpenId != null){
//			msg = userByOpenId.getLoginId();
//		}
//		
//		return WeiXinUtil.getResponseXml(fromUserName, toUserName, msg);
//	}
	
	
	
//	/**
//	 * 发送关注图文:即首页
//	 * @param picTexts
//	 * @param request 
//	 */
//	private void createAttnTW(List<Map<String, String>> picTexts, String openId, HttpServletRequest request) {
//		//关注后推送的图文URL
//		Map<String, String> m2 = new HashMap<String, String>();
//		m2.put("title", Version.getInstance().getNewProperty("indexTitle"));
//		m2.put("desc", Version.getInstance().getNewProperty("homeDesc"));
//		m2.put("picUrl", Version.getInstance().getNewProperty("indexPicUrl"));
//		//跳转到目标页，显示登录ID以及登录页面的链接
//		String loginId = this.weiXinService.createUser(openId);
//		logger.info("loginId="+loginId + " | openId=" + openId);
//		
//		PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
//		
//		//把登录ID和openId传递过去
//		String param="?openId="+openId;
////		查看是否已经登录过了，如果已经登录过了，就不需要再次登录了
//		User user = service.getUserByOpenId(openId);
////		homeUrl=http://digest.dawnboat.com/cascade/main/home.jsp
////		indexUrl=http://digest.dawnboat.com/cascade/main/index.jsp
//		if(StringUtils.isNotEmpty(user.getIdCard())){//已经登录过
//			logger.info("用户11："+ user.getUsername() + "|"+user.getTelephone() + "已经登录过.." + param);
//			request.getSession().setAttribute(MarkConstants.SES_USER, user);
//			//直接到登录后页面：首页
//			m2.put("url", Version.getInstance().getNewProperty("indexUrl") + param  );
//		}else{
//			logger.info("未登录用户，参数：" + param);
//			m2.put("url", Version.getInstance().getNewProperty("loginUrl") + param );
//		}
//		picTexts.add(m2);
//	}
	/**
	 * 发送活动图文:
	 * 一段话，
	 * @param picTexts
	 */
	private void createGameTW(List<Map<String, String>> picTexts, String openId) {
		String key = Version.getInstance().getNewProperty("game");
		if("1".equals(key)){//活动开启
			//活动游戏页面
			Map<String, String> m2 = new HashMap<String, String>();
			// Version.getInstance().getNewProperty("wx_cmdList")
			m2.put("title", "【转发有奖活动】");
			m2.put("desc", "定制祝福语，送好友，转发有奖！");
			m2.put("picUrl", "http://mmbiz.qpic.cn/mmbiz/ibwseQicrqEQHyM0XgYn7Y2D3ibJia4I9KdC8U9hEOOEzmGlttfFWEy3zMBqiacJomjQWgkJF8UxD7Mys5324kEU4tQ/0");
			m2.put("url", "http://tea.mycloudexpo.com/zhuyeqing/wx/start.html?openId="+openId);
			picTexts.add(m2);
		}
	}
	
	
	/**
	 * ajax方式，获得转发总数,其实就是点击数， 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getTotal() throws Exception {
		int total = weiXinService.getTotalTransfer();
		PrintWriter out = response.getWriter();
		out.print(total);
		return null;
	}
		
	/**
	 * 校验合法性来源
	 * 
	 * @param openId
	 * @param userAgent
	 * @return
	 */
	private int validateClient(String openId,  HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		logger.info("客户端类型：" + userAgent);
		if (openId == null || "null".equals(openId) || "".equals(openId)) {
			// 不带openId的链接也是非法的
//			request.setAttribute("info", "对不起，这个是非法的链接！请确定此链接的来源是否合法！");
			return 1;
		}
		if (!WeiXinUtil.isFromMobile(userAgent)) {
			// 不是从手机端来的
//			request.setAttribute("info", "请从手机进行操作！");
			return 2;
		}
		return 0;
	}
	
	/**
	 * 微信支付通知
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String wxpayNotify() throws IOException {
		logger.info("支付成功通知：：：：");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			// line = new String(line.getBytes("utf-8"));
			sb.append(line);
		}
		logger.info("payNotify:" + sb.toString());
		return null;
	}

	/**
	 * 校验方法
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	private boolean checkSignature(String signature, String timestamp, String nonce, String echostr) {
		logger.info("signature:" + signature);
		logger.info("timestamp" + timestamp);
		logger.info("nonce" + nonce);
		logger.info("echostr" + echostr);
		//由于这里是所有公众号账号公众号的统一校验入口，但是校验服务器是同一个， 所以token是一样的、
		String arrtemp[] = { TOKEN, timestamp, nonce };
		Arrays.sort(arrtemp); // 排序
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arrtemp.length; i++) {
			sb.append(arrtemp[i]);
		}
		String pwd = WeixinMessageDigestUtil.getInstance().encipher(sb.toString());
		if (pwd != null && pwd.equals(signature)) {
			logger.info("校验成功!返回:" + echostr);
			return true;
		} else {
			logger.equals("校验失败！");
		}
		return false;
	}
	
	
	
	
	
	/**
	 * 菜单统一入口 授权模式， 可以获取openId,
	 * @param request
	 * @param response
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 * @throws IOException
	 */
	public String goPage() throws IOException {
		//snsapi_base方式，微信服务器传过来的参数
		String code = request.getParameter("code");
		logger.info("in goPage , code=" + code);
		String appId = Version.getInstance().getNewProperty("APPID");
		String secret = Version.getInstance().getNewProperty("APPSECRET");
		// 第二步：通过code换取网页授权access_token以及openId
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		//附带参数，用来表示不同的菜单项
		String state = request.getParameter("state");
		logger.info("state=" + state);
		if(code != null){
			logger.info("tokenUrl=="+tokenUrl);
			//拿到openId
			String doPost = HttpUtils.doPost(tokenUrl, null, null);
			logger.info("doPost=" + doPost);
			String openId = WxPayUtil.getValueFromJson(doPost, "openid");
			logger.info("openId=" + openId);
			
			//根据openId去判断是否绑定，如果没绑定， 进入绑定页面
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			User userByOpenId = service.getUserByOpenId(openId);
			if(userByOpenId != null){
				request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, userByOpenId);
			}
//			//这里统一判断，哪些页面必须登录才能进入
//			if(userByOpenId == null){//账号未绑定，提示
//				boolean needAuth = checkAuthMenu(request, state);
//				if(needAuth){
//					request.setAttribute("info", Version.getInstance().getNewProperty("no.bind.tip"));
//					return "info";
//				}
//			}
			
			String url = Version.getInstance().getNewProperty(state);
			if(url == null || StringUtils.isEmpty(url)){
				request.setAttribute("info", Version.getInstance().getNewProperty("under.construction"));
				return "info";
			}
			logger.info("url=" + url);
			if(url.indexOf("?") != -1){
				//url
				this.setUrl(url + "&openId=" + openId);
				return "page";
			}
			this.setUrl(url);
			return "page";
		}
		
		return null;
	}
	
	/**
	 * 经过授权之后，跳转到http://localhost:88/fenxiao/public/pub_index.Q?c=yRbLyYSGTXyADMxADMwADM
	 * c是经过编码的公众号账号编码。
	 * 
	 * 
	 * @return
	 * @throws IOException
	 */
	public String wxMall() throws IOException {
		//snsapi_base方式，微信服务器传过来的参数
		String code = request.getParameter("code");
		//附带参数，用来表示不同的公众号账号ID
		String state = request.getParameter("state");
		String referrerUserid = request.getParameter(CupidStrutsConstants.REFERRER_USER_ID);
		String orgId = "";
		
		String prdCode = null;
		
		//分销客ID
		String agentId = null;
		String destFlag = null;
		/****************
		 * 这里state有三种情况 
		 * 1) state = {公众号账号id}
		 * 2) state = {公众号账号id},{分销客id}
		 * 3) state = {公众号账号id},{0},{destUrl}
		 */
		if(state.indexOf(",") != -1 && state.split(",").length == 2){//第二种情况, 是分销客转发的链接，agentId不为空
			orgId = state.split(",")[0];
			agentId = state.split(",")[1];
			logger.info("公众号账号转发...");
		}else if(state.indexOf(",") != -1 && state.split(",").length == 3){//第三种情况
			//指定目标页面
			orgId = state.split(",")[0];
//			agentId = state.split(",")[1];
			agentId = null;
			destFlag = state.split(",")[2];
			logger.info("跳转到指定页面：" + destFlag);
		}else if(state.indexOf(",") != -1 && state.split(",").length == 4){//第四个参数是产品编码
			orgId = state.split(",")[0];
			prdCode =  state.split(",")[3];
			logger.info("分享的页面跳转到优惠券详情：" + prdCode);
		}else{
			orgId = state;
			logger.info("一个参数：跳转到首页");
		}
		
		logger.info("商城入口------------state=" + orgId);
		//根据orgid获取appid和appsecret
		AdminService adminService = (AdminService) ServerBeanFactory.getBean("adminService");
		Org org = adminService.getOrgById(Long.valueOf(orgId));
		HttpSession session2 = request.getSession();
		if(org != null){
			session2.setAttribute(CupidStrutsConstants.ORG, org);
			session2.setAttribute(CupidStrutsConstants.FXCODE, org.getCode());
		}
		
		
		String appId = adminService.getConfigParam(ConfigParam.APPID);
		String secret = adminService.getConfigParam(ConfigParam.APP_SECRET);
		String wxID = adminService.getConfigParam(ConfigParam.WX_ID);
		logger.info("in wxMall---- , code=" + code);
		logger.info("in wxMall---- , appId=" + appId + " secret=" + secret);
		
		if(StringUtils.isEmpty(appId) || StringUtils.isEmpty(secret) || StringUtils.isEmpty(wxID)){
			request.setAttribute("info", "对不起，该公众号的APPID, APPSECRET或原始ID未设置，无法进入商城。");
			return "info";
		}
		//判断是否到期---不需要
//		if(System.currentTimeMillis() - DateUtil.getDate(org.getWxmallexpire()).getTime() > 0){
//			request.setAttribute("info", "对不起，该公众号的微商城已经到期（"+ org.getWxmallexpire() +"），请联系上级公众号账号管理员。");
//			return "info";
//		}
		
		
		logger.info("--------统一入口，该公众号账号为："+ org.getOrgname() +" | appid=" + appId + "  secret=" + secret);
		// 第二步：通过code换取网页授权access_token以及openId
//		Scope为snsapi_base
//		https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri=http%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wechat_redirect
//		Scope为snsapi_userinfo
//		https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf0e81c3bee622d60&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
		
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		if(code != null){
			logger.info("tokenUrl=="+tokenUrl);
			//拿到openId
			String doPost = HttpUtils.doPost(tokenUrl, null, null);
			logger.info("doPost=" + doPost);
			String openId = "";
			try{
				openId = WxPayUtil.getValueFromJson(doPost, "openid");
				logger.info("openId=" + openId);
			}catch(Exception e){
				//取不到openId
				//===========解决第一次进入首页，再进入二级页面后点返回报错的问题========
				if(WxPayUtil.getValueFromJson(doPost, "errcode") != null){
					//直接跳转到优惠券首页
					try {
						request.getRequestDispatcher("/public/pub_couponList.Q").forward(request, response);
						return null;
					} catch (ServletException ex) {
						ex.printStackTrace();
					}
				}
			}
			logger.info("openId=" + openId);
			
			//获取access_token
			String acToken = WxPayUtil.getValueFromJson(doPost, "access_token");
			logger.info("acToken====" + acToken);
			
			//将openId放到session,避免在url中明文传递
			session2.setAttribute(CupidStrutsConstants.WXOPENID, openId);
			//用户（普通用户，分销客）当前访问的公众号账号的对象
			session2.setAttribute(CupidStrutsConstants.WXFENXIAO, org);
			
			//推荐人userid(通过扫描二维码进入)
			if(referrerUserid!=null&&!referrerUserid.equals("")){
				session2.setAttribute(CupidStrutsConstants.REFERRER_USER_ID, referrerUserid);	
				System.out.println("aaaaaaaaaaaaaaaaaaaa"+session2.getAttribute(CupidStrutsConstants.REFERRER_USER_ID));
			}else{
				request.getSession().removeAttribute(CupidStrutsConstants.REFERRER_USER_ID);
			}
			
			//不管前面是否根据openId取到用户，这里都保存更新一下
			try{
				//此处要用网页授权的方式i获取用户信息
				WxUser wxUserByOrg = WeiXinUtil.getWxAuthUserByOrg(openId, acToken);
				logger.info("用户的微信账号信息：" + wxUserByOrg.toString());
				adminService.saveWxCustomer(wxUserByOrg, org,  agentId,referrerUserid);			
				
			}catch(Exception e){
				e.printStackTrace();
				logger.info("授权获取用户微信详细信息出错....");
			}
			
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			User userByOpenId = service.getUserByOpenId(openId);
			logger.info("In weixinAction-############### openId=" + openId +"|" + userByOpenId);
			if(userByOpenId != null){
//				updateSessionUser(userByOpenId);
				session2.setAttribute(CupidStrutsConstants.SESSION_USER, userByOpenId);
				
				if(userByOpenId.getFlag() == 1){//说明是分销客
					session2.setAttribute("isAgent", "y");
				}
				//设置城市属性，如果用户的城市属性不为空的话
				if(userByOpenId.getCity() != null && StringUtils.isNotEmpty(userByOpenId.getCity())){
					logger.info("当前进入公众号用户所属城市：" + userByOpenId.getCity());
					session2.setAttribute(CupidStrutsConstants.SES_CITY, userByOpenId.getCity());
				}
			}
			
			//加密的信息: 公众号账号编码, 从参数上传递过来的公众号账号编码是经过特定算法的： base64编码+随机10位字母; 有=号的，替换成英文逗号(,),然后再倒序
			String encodeParam = super.encodeFxCode(org.getCode());
			/*
			//以下是跳转到商城的过程，有两种情况， 可能是分销客转发给第三方， 也可能是关注用户进入
			 * 1）如果是关注用户进入，只有一个参数： c， c的值是经过编码算法的公众号账号代码
			 * 2）如果是分销客转发的，这个URL除了c之外， 还要带上一个agent参数，这个agent就是转发者（分销客）的id
			 *    体现在state参数的不同，这种情况，state={公众号账号id},{分销客id}
			 *    前面已经做过判断处理。
			 */
			String wxMallUrl = "";
			
			//如果是分享的页面过来的，直接跳转到
			if(prdCode != null){
				///coupon/public/pub_productGateway.Q?prdCode=" + $("#shareCode").val()+"&orgId=" + orgId
//				wxMallUrl = "/public/pub_productGateway.Q?prdCode=" + prdCode + "&orgId=" + orgId;
				wxMallUrl = "/public/pub_productGateway.Q?prdCode=" + prdCode;
				logger.info("分享后的页面跳转：wxMallUrl======" + wxMallUrl);
				
				
				//首页幻灯片的信息
				IAdService adService = (IAdService) ServerBeanFactory.getBean("adService");
				List<AdvConfig> ppts = adService.getPptsByOrg(Long.valueOf(orgId));
				request.getSession().setAttribute("ppts", ppts);
				
			}else if(agentId != null){
				logger.info("分销客转发的链接： " + agentId);
				wxMallUrl =  "/public/pub_index.Q?c=" + encodeParam + "&agent="+agentId;
			}else{
				if(destFlag != null){
					//进入某个特定页面
					String url = Version.getInstance().getNewProperty(destFlag);
					logger.info("普通用户进入其他页面." + destFlag + ":" + url);
					wxMallUrl =  "/weixin/" + url+"?openId="+userByOpenId.getOpenId();
				}else{
					logger.info("普通用户进入商城.");
					wxMallUrl =  "/public/pub_index.Q?c=" + encodeParam;
				}
			}
			//
			try {
				request.getRequestDispatcher(wxMallUrl).forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
//			String url = Version.getInstance().getNewProperty(state);
//			if(url == null || StringUtils.isEmpty(url)){
//				request.setAttribute("info", Version.getInstance().getNewProperty("under.construction"));
//				return "info";
//			}
//			logger.info("url=" + url);
//			if(url.indexOf("?") != -1){
//				//url
//				this.setUrl(url + "&openId=" + openId);
//				return "page";
//			}
//			this.setUrl(url);
//			return "page";
		}else{
			request.setAttribute("info", "参数不正确，无法进入商城。");
			return "info";
		}
		
		return null;
	}
	
	/**
	 * 获取带参数的临时二维码
	 */
	public String qrCode() throws Exception{
		//snsapi_base方式，微信服务器传过来的参数
		String code = request.getParameter("code");
		//附带参数，用来表示不同的公众号账号ID
		String state = request.getParameter("state");
		
		String qr_code_path="";
		
		//根据orgid获取appid和appsecret
		AdminService adminService = (AdminService) ServerBeanFactory.getBean("adminService");
		HttpSession session2 = request.getSession();
		String appId = adminService.getConfigParam(ConfigParam.APPID);
		String secret = adminService.getConfigParam(ConfigParam.APP_SECRET);
		// 第二步：通过code换取网页授权openId
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		//附带参数，用来表示不同的菜单项
		logger.info("state=" + state);
		if(code != null){
			logger.info("tokenUrl=="+tokenUrl);
			//拿到openId
			String doPost = HttpUtils.doPost(tokenUrl, null, null);
			logger.info("doPost=" + doPost);
			String openId = WxPayUtil.getValueFromJson(doPost, "openid");
			logger.info("openId=" + openId);
			
			//根据openId去判断是否绑定，如果没绑定， 进入绑定页面
			PublicService service = (PublicService) ServerBeanFactory.getBean("publicService");
			User userByOpenId = service.getUserByOpenId(openId);
			if(userByOpenId != null){
				//获取openId对应的用户id
				Long userid=userByOpenId.getId();
				
				qr_code_path=userByOpenId.getQr_code_url();
				Date date=userByOpenId.getQr_code_time();
				Date nowdate=new Date();
				boolean effective=false;
				//计算时间差，有效时间为30天
				Long second=0l;
				if(date!=null){
					//时间差，秒
					second=(nowdate.getTime()-date.getTime())/1000;
					//30天减去10分钟
					if(second>=(30*24*60*60-10*60)){
						effective=false;
					}else{
						effective=true;
					}
				}else{
					effective=false;
				}
				
				//判断用户表中是否有二维码，并且是在有效时间内的,
				//如果没有重新获取一个有效时间二维码
				if(qr_code_path==null||qr_code_path.equals("")||!effective){
					//获取有效的token
//					String tokenUrl1="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
//					String tokenpost = HttpUtils.doPost(tokenUrl1, null, null);
//					String access_token = WxPayUtil.getValueFromJson(tokenpost, "access_token");
					String access_token=WeiXinUtil.getAccessToken("000001");
					System.out.println("access_tokenaaaaaaaaaaa:"+access_token);
					//通过token获取临时带参数二维码url,ticket
					String ticketurl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token;
					JSONObject param=new JSONObject();
					param.put("expire_seconds", 2592000);//最大三十天
					param.put("action_name", "QR_SCENE");//临时二维码
					JSONObject jsonscene=new JSONObject();
					jsonscene.put("scene_id", userid);
					JSONObject action_info=new JSONObject();
					action_info.put("scene", jsonscene);
					param.put("action_info", action_info);					
					String ticketpost=HttpUtils.doPost(ticketurl, null, param.toString());
					System.out.println(ticketpost+"**********************************");
					if(ticketpost.contains("errcode")||!ticketpost.contains("url")){
						//超过更新期限了，需要重新获取一遍，并且更新到缓存
						System.out.println("####################come in##############");
						WxDataHandler handler = WxDataCommands.getInstance().getDataHandler("access_token");
						handler.getValueAndUpdate2Cache("000001");
						access_token=WeiXinUtil.getAccessToken("000001");
						ticketurl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token;
						ticketpost=HttpUtils.doPost(ticketurl, null, param.toString());
					}
					System.out.println("ticketpostaaaaaaaaaaa:"+ticketpost);

					//二维码图片解析后的地址
					String url=WxPayUtil.getValueFromJson(ticketpost, "url");
					//生成二维码保存到本地
					String saveDir=UploadUtil.getSaveUrl()+UploadUtil.UPLOADSIMAGES+"\\QR_code";
					String picname=UUID.randomUUID().toString()+".png";
					String picSavePath = saveDir + File.separator + picname; //保存路径
					System.out.println(picSavePath);
					File fileDirect = new File(saveDir);
					if (!fileDirect.exists()) {
						// 该目录不存在，则创建目录
						fileDirect.mkdirs();
					}
					//二维码不存在，需要生成二维码
					TwoDimensionCode qr_code=new TwoDimensionCode();
					String content=url;
					System.out.println(content);
					//qr_code.encoderQRCode(content,picSavePath,"png" ,20);
					qr_code.createQRCode(content, picSavePath, "c:\\zhaoyao.jpg");
					//保存到用户表中
					userByOpenId.setQr_code_url(picSavePath.replace(UploadUtil.getSaveUrl(), ""));
					userByOpenId.setQr_code_time(nowdate);
					userService.saveOrUpdateEntity(userByOpenId);
					qr_code_path=userByOpenId.getQr_code_url();
				}
				//二维码存在，进入二维码页面
				
				request.setAttribute("qr_code", UploadUtil.getImgUrl()+qr_code_path);
				
			}
		}
		return "to_qrCode";
	}
	
	/**
	 * 微信红包跳转的入口，这个方法做以下事情：
	 * 1）通过snsapi_base方式获取用户openId
	 * 2）根据获取到的openid去数据库查询，是否已经关注， 如果没有关注，提示关注公众号； 否则直接发放红包。
	 * 
	 *  发放红包的逻辑：
	 *  1）建立红包（优惠券）和用户关联记录
	 *  2）更新红包的库存
	 * @return
	 */
	public String wxHongbao(){
		//snsapi_base方式，微信服务器传过来的参数,这个参数通过tokenUrl来换取accesstoken
		String code = request.getParameter("code");
		//附带参数，用来表示不同的公众号账号ID
		String state = request.getParameter("state");
		String orgId = state.split(",")[1];
		String uuid = state.split(",")[0];
		logger.info("红包领取入口-----------传入参数-state=" + state);
		//根据orgid获取appid和appsecret
		AdminService adminService = (AdminService) ServerBeanFactory.getBean("adminService");
		Org org = adminService.getOrgById(Long.valueOf(orgId));
		
		
		logger.info("in 红包 , code=" + code);
		String appId = org.getAppid();
		String secret = org.getAppsecret();
		String wxID = org.getWxID();
		
		if(StringUtils.isEmpty(appId) || StringUtils.isEmpty(secret) || StringUtils.isEmpty(wxID)){
			request.setAttribute("info", "对不起，该公众号的APPID, APPSECRET或原始ID未设置，无法进入商城。");
			return "info";
		}
		//判断是否到期
		if(System.currentTimeMillis() - DateUtil.getDate(org.getWxmallexpire()).getTime() > 0){
			request.setAttribute("info", "对不起，该公众号的微商城已经到期（"+ org.getWxmallexpire() +"），请联系上级公众号账号管理员。");
			return "info";
		}
		
		logger.info("--------领取红包统一入口，该公众号账号为："+ org.getOrgname() +" | appid=" + appId + "  secret=" + secret);
		// 第二步：通过code换取网页授权access_token以及openId
//		Scope为snsapi_base
//		https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri=http%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wechat_redirect
//		Scope为snsapi_userinfo
//		https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf0e81c3bee622d60&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
		
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		if(code != null){
			logger.info("tokenUrl=="+tokenUrl);
			//拿到openId
			String doPost = HttpUtils.doPost(tokenUrl, null, null);
			logger.info("doPost=" + doPost);
			String openId = WxPayUtil.getValueFromJson(doPost, "openid");
			logger.info("openId=" + openId);
			//去查找是否已经关注
			User userByOpenId = adminService.getUserByOpenId(openId);
			boolean issubscribe = true;
			if(userByOpenId == null){
				issubscribe = false;
			}else{
				logger.info("userByOpenId.substate---" + userByOpenId.getSubstate());
				if(userByOpenId.getSubstate() == 0){
					issubscribe = false;
				}
			}
			
			if(!issubscribe){
				//进入提示页面
				logger.info(openId + "未关注");
				request.setAttribute("info", "对不起，请先关注公众号【"+ org.getOrgname() +"】后通过菜单->领取红包即可领取。");
				try {
					response.sendRedirect(org.getAttHintUrl());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}else{
				saveSession(org, openId, userByOpenId);
				
				logger.info(openId + "已关注");
				//进入红包领取页面
				//跳转到红包页面， 不过需要传递3个参数：当前用户的openId, 红包的批次编号、 公司ID
				request.setAttribute("openId", openId);
				request.setAttribute("uuid", uuid);
				request.setAttribute("orgId", orgId);
				return "hongBaoIndex";
			}
		}
		
		return null;
	}
	
	/**
	 * 微信公众号相关链接的通用入口，在这个入口里，主要的目的就是获取当前用户访问的企业信息。Org； 并设置到session，以便后续程序调用
	 * 第二个目的就是负责跳转。 通用入口的url规则如下：
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx24d130897de2b974&redirect_uri=http%3A%2F%2Fhotel.91lot.com%2Fhotel%2Fwx%2Fwx_commonEntry.Q&response_type=code&scope=snsapi_base&state=contact,78#wechat_redirect
	 * state：后面两个参数：第一个：映射到目标页面的字符串。 第二个：orgId
	 * 
	 * @return
	 */
	public String commonEntry(){
		//TODO 
		return null;
	}
	
	
	
	//更新session
	private void saveSession(Org org, String openId, User userByOpenId) {
		//将openId放到session,避免在url中明文传递
		request.getSession().setAttribute(CupidStrutsConstants.WXOPENID, openId);
		request.getSession().setAttribute(CupidStrutsConstants.SESSION_USER, userByOpenId);
		request.getSession().setAttribute(CupidStrutsConstants.FXCODE, org.getCode());
		request.getSession().setAttribute(CupidStrutsConstants.FX_ID, org.getId().toString());
		//公众号账号名称
		request.getSession().setAttribute(CupidStrutsConstants.FXNAME, org.getOrgname());
		request.getSession().setAttribute(CupidStrutsConstants.ORG, org);
		//用户（普通用户，分销客）当前访问的公众号账号的对象
		request.getSession().setAttribute(CupidStrutsConstants.WXFENXIAO, org);
	}
	
	

	public static void main(String[] args) {
		String xml = "<xml><ToUserName><![CDATA[gh_649e8c686a80]]></ToUserName><FromUserName><![CDATA[o1PuujpRyno87Ja2YaPWCBpRbE0c]]></FromUserName><CreateTime>1376371547</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[哈哈哈哈哈]]></Content><MsgId>5911470781509926936</MsgId></xml>";
		 Document msgdoc;
		try {
			msgdoc = DocumentHelper.parseText(xml);
			Element root = msgdoc.getRootElement();
			Element node = root.element("MsgType");
			String MsgType = node.getTextTrim();
			node = root.element("ToUserName");// 开发者账号/公众号----------- 
			String toUserName = node.getTextTrim();
			node = root.element("FromUserName");// 发送者openId
			// 即：openId
			String fromUserName = node.getTextTrim();
			System.out.print(fromUserName);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Document msgdoc = DocumentHelper.parseText(sb.toString());
	}
	
	public String saveToFile(String destUrl,String openid) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        String name = "";
        try {
            url = new URL(destUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            name = "weichat_images/"+openid+"_"+System.currentTimeMillis()+".png";
            String imagesUrl = this.getAbsoluteRootPath()+"/"+name+"/";
            System.out.println(name);
            bis = new BufferedInputStream(httpUrl.getInputStream());
            fos = new FileOutputStream(imagesUrl);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.flush();
        } catch (IOException e) {
        } catch (ClassCastException e) {
        } finally {
            try {
                fos.close();
                bis.close();
                httpUrl.disconnect();
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }
        }
        return name;
    }
	
	public String getAbsoluteRootPath(){
        String systemPath = request.getSession().getServletContext().getRealPath("/");
        String realPathParent = (new File(systemPath)).getParent();
        return realPathParent;
    }

	public void setWeiXinService(IWeiXinService weiXinService) {
		this.weiXinService = weiXinService;
	}
	
	
	public void setDrugScoreLogService(IDrugScoreLogService drugScoreLogService) {
		this.drugScoreLogService = drugScoreLogService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
