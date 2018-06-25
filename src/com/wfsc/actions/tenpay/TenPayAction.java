package com.wfsc.actions.tenpay;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.log.LogUtil;
import com.wfsc.actions.common.CupidBaseAction;
import com.wfsc.services.pay.IFinanceService;
import com.wfsc.tenpay.RequestHandler;
import com.wfsc.tenpay.ResponseHandler;
import com.wfsc.tenpay.TenPayConstants;
import com.wfsc.tenpay.client.ClientResponseHandler;
import com.wfsc.tenpay.client.TenpayHttpClient;
import com.wfsc.tenpay.util.TenpayUtil;

/**
 * 
 * @author Jacky
 * @version 1.1
 * 
 */
@Controller("TenpayAction")
@Scope("prototype")
public class TenPayAction extends CupidBaseAction {

	private static final long serialVersionUID = 6894975364581693382L;

	Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Resource(name = "finService")
	private IFinanceService finService;
	
	// private AdminService adminService;
	/**
	 * 支付完成后跳转的页面，商家提供
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String payReturn() throws Exception {
		// ---------------------------------------------------------
		// 财付通支付应答（处理回调）示例，商户按照此文档进行开发即可
		// ---------------------------------------------------------
		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(TenPayConstants.key);
		logger.info("前台回调返回参数:" + resHandler.getAllParameters());
		// 判断签名
		if (resHandler.isTenpaySign()) {
			// 通知id
			String notify_id = resHandler.getParameter("notify_id");
			// 商户订单号
			String out_trade_no = resHandler.getParameter("out_trade_no");
			// 财付通订单号
			String transaction_id = resHandler.getParameter("transaction_id");
			// 金额,以分为单位
			String total_fee = resHandler.getParameter("total_fee");
			// 如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
			String discount = resHandler.getParameter("discount");
			// 支付结果
			String trade_state = resHandler.getParameter("trade_state");
			// 交易模式，1即时到账，2中介担保
			String trade_mode = resHandler.getParameter("trade_mode");
			// 获取debug信息,建议把debug信息写入日志，方便定位问题
			String debuginfo = resHandler.getDebugInfo();
			logger.info("In Pay return url :" + debuginfo);
			if ("1".equals(trade_mode)) { // 即时到账
				if ("0".equals(trade_state)) {
					// ------------------------------
					// 即时到账处理业务开始
					// ------------------------------
					// 注意交易单不要重复处理
					// 注意判断返回金额
					// ------------------------------
					// 即时到账处理业务完毕
					// ------------------------------
					//充值成功的金额：单位元
					request.setAttribute("fee", Double.valueOf(total_fee)/100);
					logger.info("即时到帐付款成功");
					return SUCCESS;
				} else {
					logger.info("即时到帐付款失败");
					return ERROR;
				}
			}
			// else if ("2".equals(trade_mode)) { // 中介担保
			// if ("0".equals(trade_state)) {
			// // ------------------------------
			// // 中介担保处理业务开始
			// // ------------------------------
			// // 注意交易单不要重复处理
			// // 注意判断返回金额
			// // ------------------------------
			// // 中介担保处理业务完毕
			// // ------------------------------
			// logger.info("中介担保付款成功");
			// } else {
			// logger.info("trade_state=" + trade_state);
			// }
			// }
		} else {
			logger.info("认证签名失败");
			return ERROR;
		}
		// return this.info(mapping, request, "您的支付操作已经成功！");
		return SUCCESS;
	}

	/**
	 * 支付网关通知
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String payNotify() throws Exception {
		// ---------------------------------------------------------
		// 财付通支付通知（后台通知）示例，商户按照此文档进行开发即可
		// ---------------------------------------------------------
		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(TenPayConstants.key);
		logger.info("后台回调返回参数:" + resHandler.getAllParameters());
		// 判断签名
		if (resHandler.isTenpaySign()) {
			// 通知id
			String notify_id = resHandler.getParameter("notify_id");
			// 创建请求对象
			RequestHandler queryReq = new RequestHandler(null, null);
			// 通信对象
			TenpayHttpClient httpClient = new TenpayHttpClient();
			// 应答对象
			ClientResponseHandler queryRes = new ClientResponseHandler();
			// 通过通知ID查询，确保通知来至财付通
			queryReq.init();
			queryReq.setKey(TenPayConstants.key);
			queryReq.setGateUrl("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
			queryReq.setParameter("partner", TenPayConstants.partner);
			queryReq.setParameter("notify_id", notify_id);
			// 通信对象
			httpClient.setTimeOut(5);
			// 设置请求内容
			httpClient.setReqContent(queryReq.getRequestURL());
			logger.info("验证ID请求字符串:" + queryReq.getRequestURL());
			// 后台调用
			if (httpClient.call()) {
				// 设置结果参数
				queryRes.setContent(httpClient.getResContent());
				logger.info("验证ID返回字符串:" + httpClient.getResContent());
				queryRes.setKey(TenPayConstants.key);
				// 获取id验证返回状态码，0表示此通知id是财付通发起
				String retcode = queryRes.getParameter("retcode");
				// 支付结果
				String trade_state = resHandler.getParameter("trade_state");
				// 交易模式，1即时到账，2中介担保
				String trade_mode = resHandler.getParameter("trade_mode");
				// 判断签名及结果
				if (queryRes.isTenpaySign() && "0".equals(retcode)) {
					logger.info("id验证成功");
					if ("1".equals(trade_mode)) { // 即时到账
						if ("0".equals(trade_state)) {
							// ------------------------------
							logger.info("// 即时到账处理业务开始");
							// ------------------------------
							finService.notifySuccess(resHandler);
							// ------------------------------
							logger.info("// 即时到账处理业务完毕");
							// ------------------------------
							logger.info("即时到账支付成功");
							// 给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
							resHandler.sendToCFT("success");
						} else {
							logger.info("即时到账支付失败");
							resHandler.sendToCFT("fail");
						}
					}
					// else if ("2".equals(trade_mode)) { // 中介担保
					// // ------------------------------
					// // 中介担保处理业务开始
					// // ------------------------------
					// // 处理数据库逻辑
					// // 注意交易单不要重复处理
					// // 注意判断返回金额
					// int iStatus = TenpayUtil.toInt(trade_state);
					// switch (iStatus) {
					// case 0: // 付款成功
					// break;
					// case 1: // 交易创建
					// break;
					// case 2: // 收获地址填写完毕
					// break;
					// case 4: // 卖家发货成功
					// break;
					// case 5: // 买家收货确认，交易成功
					// break;
					// case 6: // 交易关闭，未完成超时关闭
					// break;
					// case 7: // 修改交易价格成功
					// break;
					// case 8: // 买家发起退款
					// break;
					// case 9: // 退款成功
					// break;
					// case 10: // 退款关闭
					// break;
					// default:
					// }
					// // ------------------------------
					// // 中介担保处理业务完毕
					// // ------------------------------
					// logger.info("trade_state = " + trade_state);
					// // 给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
					// resHandler.sendToCFT("success");
					// }
				} else {
					// 错误时，返回结果未签名，记录retcode、retmsg看失败详情。
					logger.info("查询验证签名失败或id验证失败" + ",retcode:" + queryRes.getParameter("retcode"));
				}
			} else {
				logger.info("后台调用通信失败");
				logger.info(httpClient.getResponseCode());
				logger.info(httpClient.getErrInfo());
				// 有可能因为网络原因，请求已经处理，但未收到应答。
			}
		} else {
			logger.info("通知签名验证失败");
		}
		return null;
	}

	/**
	 * 提交到支付网关
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String tenpay() throws Exception {
		// ---------------------------------------------------------
		// 财付通网关支付请求示例，商户按照此文档进行开发即可
		// ---------------------------------------------------------
		request.setCharacterEncoding("UTF-8");
		// 获取提交的商品价格
		String order_price = request.getParameter("order_price"); // 这里输入的是元
		// String order_price = "0.01";
		/* 商品价格（包含运费），以分为单位 */
		double total_fee = (Double.valueOf(order_price) * 100);
		int fee = (int) total_fee;
		// 获取提交的商品名称
		String product_name = "爱觅网金币";
		// 获取提交的备注信息
		String remarkexplain = "金币充值";
		String desc = "商品：" + product_name + ",备注：" + remarkexplain;
		// 获取提交的订单号
		String out_trade_no = request.getParameter("order_no");
		// 支付方式
		String trade_mode = request.getParameter("trade_mode");
		// ---------------生成订单号 开始------------------------
		// 当前时间 yyyyMMddHHmmss
		// String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		// String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		// String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		// String strReq = strTime + strRandom;
		// 订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
		// String out_trade_no = strReq;
		// ---------------生成订单号 结束------------------------
		String currTime = TenpayUtil.getCurrTime();
		// 创建支付请求对象
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init();
		// 设置密钥
		reqHandler.setKey(TenPayConstants.key);
		// 设置支付网关
		reqHandler.setGateUrl("https://gw.tenpay.com/gateway/pay.htm");
		// -----------------------------
		// 设置支付参数
		// -----------------------------
		reqHandler.setParameter("partner", TenPayConstants.partner); // 商户号
		reqHandler.setParameter("out_trade_no", out_trade_no); // 商家订单号
		reqHandler.setParameter("total_fee", String.valueOf(fee)); // 商品金额,以分为单位
		reqHandler.setParameter("return_url", TenPayConstants.return_url); // 交易完成后跳转的URL
		reqHandler.setParameter("notify_url", TenPayConstants.notify_url); // 接收财付通通知的URL
		reqHandler.setParameter("body", desc); // 商品描述
		reqHandler.setParameter("bank_type", "DEFAULT"); // 银行类型(中介担保时此参数无效)
		reqHandler.setParameter("spbill_create_ip", request.getRemoteAddr()); // 用户的公网ip，不是商户服务器IP
		reqHandler.setParameter("fee_type", "1"); // 币种，1人民币
		reqHandler.setParameter("subject", desc); // 商品名称(中介交易时必填)
		// 系统可选参数
		reqHandler.setParameter("sign_type", "MD5"); // 签名类型,默认：MD5
		reqHandler.setParameter("service_version", "1.0"); // 版本号，默认为1.0
		reqHandler.setParameter("input_charset", "UTF-8"); // 字符编码
		reqHandler.setParameter("sign_key_index", "1"); // 密钥序号
		// 业务可选参数
		reqHandler.setParameter("attach", ""); // 附加数据，原样返回
		reqHandler.setParameter("product_fee", String.valueOf(fee)); // 商品费用，必须保证transport_fee + product_fee=total_fee
		reqHandler.setParameter("transport_fee", "0"); // 物流费用，必须保证transport_fee + product_fee=total_fee
		reqHandler.setParameter("time_start", currTime); // 订单生成时间，格式为yyyymmddhhmmss
		reqHandler.setParameter("time_expire", ""); // 订单失效时间，格式为yyyymmddhhmmss
		reqHandler.setParameter("buyer_id", ""); // 买方财付通账号
		reqHandler.setParameter("goods_tag", ""); // 商品标记
		reqHandler.setParameter("trade_mode", trade_mode); // 交易模式，1即时到账(默认)，2中介担保，3后台选择（买家进支付中心列表选择）
		reqHandler.setParameter("transport_desc", ""); // 物流说明
		reqHandler.setParameter("trans_type", "1"); // 交易类型，1实物交易，2虚拟交易
		reqHandler.setParameter("agentid", ""); // 平台ID
		reqHandler.setParameter("agent_type", ""); // 代理模式，0无代理(默认)，1表示卡易售模式，2表示网店模式
		reqHandler.setParameter("seller_id", ""); // 卖家商户号，为空则等同于partner
		// 请求的url
		String requestUrl = reqHandler.getRequestURL();
		// 获取debug信息,建议把请求和debug信息写入日志，方便定位问题
		String debuginfo = reqHandler.getDebugInfo();
		// System.out.println("requestUrl: " + requestUrl);
		// System.out.println("sign_String: " + debuginfo);
		logger.info("requestUrl:  " + requestUrl);
		logger.info("sign_String:  " + debuginfo);
		// 在系统生成一条订单
		finService.createOrder(reqHandler, this.getCurrentUser().getId());
		response.sendRedirect(requestUrl);
		return null;
	}

	
	public void setFinService(IFinanceService finService) {
		this.finService = finService;
	}

	// public void setAdminService(AdminService adminService) {
	// this.adminService = adminService;
	// }
	
	public static void main(String[] args){
		System.out.println(Double.valueOf("1")/100);
	}
}
