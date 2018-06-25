package com.wfsc.tenpay;

public class TenPayConstants {

	//收款方
	public static final String spname = "财付通双接口测试";  

	//商户号 - 1216409101
	public static final String partner = "1217696401";

	//密钥 - a542740cd043a26b520d3c47d2ef624c-
	public static final String key = "67cc1a08b1341379bda66db196faacd0";

	//测试结果：
	//http://localhost:81/tenpay/tenpay_api_b2c/payReturnUrl.jsp?
	/**
		bank_billno=2363411810&	//银行订单号
		bank_type=CMB&			//付款银行
		discount=0&
		fee_type=1&				//人民币
		input_charset=UTF-8&
		notify_id=iu4pIynHtA8WwxMzRFxq4jTAV0gnim9mgO3to2UJN9-sE0ojHeIeDL_5SrbI-Jep61NbeS0odGEMWcEsvvuJwKa9mGwTft1G&
		out_trade_no=1803053504&//商户订单号
		partner=1216409101& 	//商户号
		product_fee=1&			//物品费用
		sign_type=MD5&
		time_end=20130620180635& //支付完成时间
		total_fee=1&			//总费用
		trade_mode=1&			//即时到帐
		trade_state=0&
		transaction_id=1216409101201306200289672095&//财付通订单号
		transport_fee=0&
		sign=EF3C5E7C9E3C92EB70EE61CF89F19861
	*/
	//交易完成后跳转的URL
//	public static final String return_url = "/tenpay/payReturnUrl.jsp";
//	public static final String return_url = "http://www.amylove.cn/tenpay/payReturnUrl.Q";
	public static final String return_url = "http://www.amylove.cn/love/private/tenpay_payReturn.Q";

	//接收财付通通知的URL
//	public static final String notify_url = "http://www.amylove.cn/tenpay/payNotifyUrl.Q";
	public static final String notify_url = "http://www.amylove.cn/love/private/tenpay_payNotify.Q";
}
