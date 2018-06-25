package wx.data;

import org.apache.log4j.Logger;

import com.base.log.LogUtil;

import util.HttpUtils;
import wx.cache.WxParamCache;
import actions.integ.weixin.WeiXinUtil;

public class JsApiTickedHandler implements WxDataHandler {
	private static Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	@Override
	public String getValueAndUpdate2Cache(String orgCode) {
		String token = WxParamCache.getInstance().getWxJsParam(orgCode, WxParamCache.TOKEN);
		logger.info("in JsApiTickedHandler--" + token);
		String jsapiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ token +"&type=jsapi";
		String doGet = HttpUtils.doGet(jsapiUrl, null);
		String ticket = WeiXinUtil.getValueFromJson(doGet, "ticket");
		//更新到缓存
		WxParamCache.getInstance().updateParam(orgCode, WxParamCache.JS_TICKET, ticket, System.currentTimeMillis());
		
		return ticket;
	}
}
