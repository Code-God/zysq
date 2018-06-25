package constants;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author jacky
 * @version 1.0
 */
public class MarkConstants {
	/** 文字水印类型 */
	public static final String MARK_TYPE_TEXT = "1";

	/** 图片水印类型 */
	public static final String MARK_TYPE_ICON = "2";
	
	/** 存放预览图片的原图片 */
	public static final String PREVIEW_SRC_PATH = "/swfupload/previewsrc/";
	
	private static Map<String, String> MENU_MAP = null;
	
	public static Map<String, String> getCAT_MAP() {
		MENU_MAP = new HashMap<String, String>();
		MENU_MAP.put("apple", "苹果风格#apple");
		MENU_MAP.put("circle", "圆形风格#circle");
//		CAT_MAP.put("qingren", "情人节#qingren");
//		CAT_MAP.put("wuyi", "五一#wuyi");
//		CAT_MAP.put("guoqing", "国庆#guoqing");
//		CAT_MAP.put("double11", "双11(光棍节)#double11");
//		CAT_MAP.put("shengdan", "圣诞节#shengdan");
		
//		CAT_MAP.put("otherfest", "其他节日#otherfest");
//		CAT_MAP.put("duanwu", "端午#duanwu");
//		CAT_MAP.put("zhongqiu", "中秋#zhongqiu");
//		CAT_MAP.put("parents", "父亲母亲节#parents");
		MENU_MAP.put("dazhecuxiao", "打折促销#dazhecuxiao");
		MENU_MAP.put("new", "新品上市#new");
		MENU_MAP.put("text", "超酷文本#text");
		MENU_MAP.put("others", "其他#others");
		MENU_MAP.put("custom", "自定义#custom");
		return MENU_MAP;
	}
	
	//-----------------------------------  API相关 -----------------------------------------------------
	
	/** 授权URL */
	public static final String authServerUrl = "https://api.weibo.com/oauth2/authorize";
	/** api的调用url */
	public static final String serverUrl = "https://gw.api.tbsandbox.com/router/rest";
	public static final String callbackUrl = "http://golf.fangmingyi.com/sinapage/golf.do?method=redirectCall";
//	public static final String callbackUrl = "http://127.0.0.1:88/sinapage/golf.do?method=redirectCall";
	
	public static final String clientId = "1160277498";
	public static final String appSecret = "ea24e202000fdd14a2d1468cea3c675c";
	/** 官方微博UID    App Key:1160277498*/
	public static final String HOST_UID = "2465351365";
	
	public static final String tokenUrl = "https://api.weibo.com/oauth2/access_token";
//	https://api.weibo.com/oauth2/authorize?client_id=YOUR_CLIENT_ID&response_type=code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI 
	/** 引导用户授权码的地址 */
	public static final String authUrl = authServerUrl + "?client_id="+ clientId +"&response_type=code&redirect_uri=" + callbackUrl;
	
	/** 企业授权回调地址 */
	public static final String companyAuthCallbackUrl = "http://e.weibo.com/"+ HOST_UID +"/app_1160277498" ;
	
	//-------------------------- SES常量 --------------------
	/** 管理员登录后的session key */
	public static final String SES_ADMIN = "SES_ADMIN";
	
	public static final String CART_COUNT = "cartCount";

	public static final String SES_OPENID = "OPENID";
	
	
	
}
