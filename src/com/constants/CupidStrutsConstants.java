package com.constants;

import java.math.BigDecimal;

/**
 * 全局常量定义类
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public class CupidStrutsConstants {

	/** 在线人数 */
	public static int SES_TOTAL = 0;

	/** 所有用户在session里的key值 */
	public static final String SESSION_USER = "SESSION_USER";
	/** 购物车session key */
	public static final String SESSION_SHOP = "SESSION_SHOP";
	/** 最近浏览记录session key */
	public static final String SESSION_JUSTLOOK = "SESSION_JUSTLOOK";

	/** 所有访问者总数在session里的key值 */
	public static final String SESSION_VISITOR = "com.cupidonline.visitor";

	/** 在线会员总数在session里的key值 */
	public static final String SESSION_USER_COUNT = "com.cupidonline.user.count";

	public static final String SES_REFERER = "referer";
	
	/** 城市切换 */
	public static final String SES_CITY = "SES_CITY";

	/** 会员对象,在request里 */
	public static final String REQ_MEMBER = "member";

	public static final String SESSION_ADMIN = "SESSION_ADMIN";
	
	public static final String GOTO_URL_KEY = "GOING_TO";

	/** 用户图片目录 */
	public static final String USER_PIC_DIR = "/private/UploadImages";

	/** 证件目录,是用户图片目录的子目录 */
	public static final String USER_CERTIFY_DIR = "/Certify";

	public static final String SES_TOKEN = "SES_TOKEN";
	
	public static final String SESSION_ADMIN_ROLE = "SESSION_ADMIN_ROLE";
	
	public static final String SESSION_SUPER_ADMIN = "SESSION_SUPER_ADMIN";
	
	public static final String CURR_CITY = "CURR_CITY";
	
	//手机短信验证码
	public static final String MOBILE_VERIFICATION_CODE = "mobile_verification_code";
	
	//前台登录验证码
	public static final String CODE_IMAGE_LOGIN = "CODE_IMAGE_LOGIN";
	
	//用户注册验证码
	public static final String CODE_IMAGE_REGIST = "CODE_IMAGE_REGIST";
	
	//后台登录验证码
	public static final String CODE_IMAGE_ADMIN = "CODE_IMAGE_ADMIN";
	
	
	public static final String MD5KEY = "FenXiao@2015";

	//分销商code
	public static final String FXCODE = "FXCODE";
	//分销商ID
	public static final String FX_ID = "FX_ID";

	public static final String FXNAME = "FXNAME";
	
	public static final String ORG = "ORG";
	
	public static final String WXOPENID = "WXOPENID";
	
	public static final String WXFENXIAO = "WXFENXIAO";
	
	public static final String REFERRER_USER_ID="referrer_user_id";//推荐人userid
	//层次码的长度
	public static final int LV_CODE_LENGTH = 6;
	
	public static void main(String[] args){
		float f = 0.1f;
		BigDecimal b = new BigDecimal(f);
		b = b.setScale(4, BigDecimal.ROUND_FLOOR);
		System.out.println(b.toString());
	}
}
