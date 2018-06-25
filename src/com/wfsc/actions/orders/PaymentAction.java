package com.wfsc.actions.orders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.order.OrdersDetail;
import com.wfsc.payment.AlipayNotify;
import com.wfsc.payment.AlipaySubmit;
import com.wfsc.services.orders.IOrdersService;

/**
 * 
 * @author hl
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("PaymentAction")
@Scope("prototype")
public class PaymentAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840813332299260353L;

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);
	
	@Resource(name = "ordersService")
	private IOrdersService ordersService;
	
	private Orders orders;
	
	private OrdersDetail od;

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public OrdersDetail getOd() {
		return od;
	}

	public void setOd(OrdersDetail od) {
		this.od = od;
	}
	
	public String payment(){
		String orderNo = request.getParameter("orderNo");
		Orders o = ordersService.getOrderByCode(orderNo);
	//	List<OrdersDetail> ods = new ArrayList<OrdersDetail>();
		if(o!=null&&o.getOrdersDetail()!=null){
	//		ods = o.getOrdersDetail();
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			String service = Version.getInstance().getNewProperty("alipay.service");
			String partner = Version.getInstance().getNewProperty("alipay.partner");
			String seller_id = Version.getInstance().getNewProperty("alipay.seller_id");
			String _input_charset = Version.getInstance().getNewProperty("alipay._input_charset");
			String payment_type = Version.getInstance().getNewProperty("alipay.payment_type");
			String notify_url = Version.getInstance().getNewProperty("alipay.notify_url");
			String return_url = Version.getInstance().getNewProperty("alipay.return_url");
			
			sParaTemp.put("service", service);
	        sParaTemp.put("partner", partner);
	        sParaTemp.put("seller_id", seller_id);
	        sParaTemp.put("_input_charset", _input_charset);
			sParaTemp.put("payment_type", payment_type);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("return_url", return_url);
			sParaTemp.put("out_trade_no", o.getOrderNo());
			sParaTemp.put("total_fee", o.getFee().toString());
			sParaTemp.put("subject", "订单"+o.getOrderNo());
			//建立请求
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
			try {
				response.getWriter().write(sHtmlText);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			return "error";
		}
		
	}
	public String notifyHandle(){
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		try {
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			if(AlipayNotify.verify(params)){//验证成功
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//该种交易状态只在两种情况下出现
					//1、开通了普通即时到账，买家付款成功后。
					//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				}

				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					
				response.getWriter().write("success");	//请不要修改或删除

			}else{//验证失败
				response.getWriter().write("fail");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String returnHandle(){
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		//交易状态
		String trade_status = "";
		try {
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		
		if(verify_result){//验证成功
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
			}
			
			//该页面可做页面美工编辑
			//out.println("验证成功<br />");
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			return "ok";

		}else{
			//该页面可做页面美工编辑
			//out.println("验证失败");
			return "error";
		}
	}
	
	public String errorNotifyHandle(){
		return "error";
	}


}
