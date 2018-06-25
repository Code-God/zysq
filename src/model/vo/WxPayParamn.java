package model.vo;

import java.io.Serializable;

/**
 * 
 * "appId" : appId, // 公众号名称， "timeStamp": ""+time,// 时间戳，自 1970 1970 年以来的秒数 年以来的秒数 年以来的秒数 年以来的秒数 "nonceStr" : nonceStr, //
 * 随机串 \ "package" : package, "signType" : "MD5", // 微信签名方式 "paySign" : paySign // 微信签名
 * 
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class WxPayParamn implements Serializable {

	private static final long serialVersionUID = -776918359242383907L;

	/** 统一支付接口URL */
	public static final String UNI_PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/** 订单关闭接口 */
	public static final String UNI_PAY_CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";

	private String appId;

	private String timeStamp;

	private String nonceStr;

	private String pckage;

	private String signType;

	private String paySign;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPckage() {
		return pckage;
	}

	public void setPckage(String pckage) {
		this.pckage = pckage;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	
	public String getTimeStamp() {
		return timeStamp;
	}

	
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	public String getSignType() {
		return signType;
	}

	
	public void setSignType(String signType) {
		this.signType = signType;
	}
}
